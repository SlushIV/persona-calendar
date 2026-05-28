package com.example.calendar.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.calendar.data.StatsController

@Composable
fun StatScreen(controller: StatsController) {
    Column {

        // academics ui
        Column {
            Text(text = "Academics: ${controller.academicPoints}")
            Text(text = "Rank: ${controller.getAcademicsTitle()}")

            Button(onClick = { controller.increaseAcademics(2) }) {
                Text("Study (+2)")
            }
        }

        // charm ui
        Column {
            Text(text = "Charm: ${controller.charmPoints}")
            Text(text = "Rank: ${controller.getCharmTitle()}")

            Button(onClick = { controller.increaseCharm(2) }) {
                Text("Train (+2)")
            }
        }

        // courage ui
        Column {
            Text(text = "Courage: ${controller.couragePoints}")
            Text(text = "Rank: ${controller.getCourageTitle()}")

            Button(onClick = { controller.increaseCourage(2) }) {
                Text("Socialize (+2)")
            }
        }
    }
}