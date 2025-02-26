package com.ajeeb.bumblecar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ajeeb.bumblecar.common.core.BumbleCarTheme
import com.ajeeb.bumblecar.common.core.NavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BumbleCarTheme {
                NavGraph()
            }
        }
    }
}