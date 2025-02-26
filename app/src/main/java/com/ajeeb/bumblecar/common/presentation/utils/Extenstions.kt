package com.ajeeb.bumblecar.common.presentation.utils

import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental

/**This extension streamlines access to read-only data from the ViewModel's state.*/
val <STATE : Any, SIDE_EFFECT : Any> ContainerHost<STATE, SIDE_EFFECT>.currentState: STATE
    get() = container.stateFlow.value

/**Extension function to simplify state updates in an Orbit MVI `ContainerHost`.
 * It reduces boilerplate by directly applying the state update logic.*/
@OptIn(OrbitExperimental::class)
suspend inline fun <STATE : Any, SIDE_EFFECT : Any> ContainerHost<STATE, SIDE_EFFECT>.reduceState(
    crossinline update: STATE.() -> STATE
) = subIntent {
    reduce { state.update() }
}