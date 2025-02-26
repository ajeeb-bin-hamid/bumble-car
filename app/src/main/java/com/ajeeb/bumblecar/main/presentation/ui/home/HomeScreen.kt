package com.ajeeb.bumblecar.main.presentation.ui.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ajeeb.bumblecar.R
import com.ajeeb.bumblecar.common.core.poppinsFontFamily
import com.ajeeb.bumblecar.common.presentation.ui.text_field.basic.BumbleCarBasicTextField
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: State<HomeState>,
    sideEffect: Flow<HomeSideEffect>,
    onEvent: (HomeIntent) -> Unit,
) {

    val context = LocalContext.current

    /**Collect SideEffects using Orbit*/
    LaunchedEffect(Unit) {
        sideEffect.collect { action ->
            when (action) {
                is HomeSideEffect.ShowToast -> {
                    Toast.makeText(context, context.getString(action.message), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding()
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            HomeScreenTopBar()
            Spacer(Modifier.height(8.dp))
            LazyColumn {
                item {

                    val suggestions =
                        listOf("Apple", "Banana", "Cherry", "Date", "Grape", "Orange", "Mango")
                    val pickUpSuggestions = suggestions.filter {
                        it.contains(
                            state.value.pickUpPoint, ignoreCase = true
                        ) && state.value.pickUpPoint.isNotEmpty() && state.value.pickUpPoint != it
                    }

                    val dropOffSuggestions = suggestions.filter {
                        it.contains(
                            state.value.dropOffPoint, ignoreCase = true
                        ) && state.value.dropOffPoint.isNotEmpty() && state.value.dropOffPoint != it
                    }
                    Column {


                        BumbleCarBasicTextField(modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                            value = state.value.pickUpPoint,
                            label = stringResource(R.string.pickup_location),
                            placeholder = stringResource(R.string.enter_your_pick_up_location),
                            filteredSuggestions = pickUpSuggestions,
                            onValueChange = { newText ->
                                onEvent(HomeIntent.SetPickUpPoint(newText))
                            })

                        Spacer(Modifier.height(12.dp))

                        BumbleCarBasicTextField(modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                            value = state.value.dropOffPoint,
                            label = stringResource(R.string.drop_off_point),
                            placeholder = stringResource(R.string.enter_your_drop_off_location),
                            filteredSuggestions = dropOffSuggestions,
                            onValueChange = { newText ->
                                onEvent(HomeIntent.SetDropOffPoint(newText))
                            })
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenTopBar(modifier: Modifier = Modifier) {
    TopAppBar(modifier = modifier, colors = TopAppBarDefaults.topAppBarColors().copy(
        containerColor = MaterialTheme.colorScheme.background
    ), title = {
        Row {
            Text(
                text = stringResource(R.string.bumble), style = TextStyle(
                    fontFamily = poppinsFontFamily(),
                    fontWeight = FontWeight.Normal,
                    fontSize = 24.sp,
                ), color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = stringResource(R.string.car), style = TextStyle(
                    fontFamily = poppinsFontFamily(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                ), color = MaterialTheme.colorScheme.onBackground
            )
        }
    })
}