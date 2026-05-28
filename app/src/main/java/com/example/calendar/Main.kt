package com.example.calendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.calendar.data.StatsController
import com.example.calendar.ui.StatScreen
import com.example.calendar.ui.theme.CalendarTheme

class MainActivity : ComponentActivity() {

    // initialize StatsController
    private val statsController: StatsController by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // app visual theme template
            CalendarTheme {

                // surface container using background color from theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    // launch screen layout with controller backend
                    StatScreen(controller = statsController)

                }
            }
        }
    }
}