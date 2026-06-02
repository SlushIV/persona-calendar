package com.example.calendar.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class StatsController : ViewModel() {

    var academicPoints by mutableIntStateOf(0)
        private set

    var charmPoints by mutableIntStateOf(0)
        private set

    var couragePoints by mutableIntStateOf(0)
        private set

    val thresholds = listOf(0, 50, 100, 200, 400, 800)

    fun getAcademicsTitle(): String {
        return when {
            academicPoints < thresholds[1] -> "Slacker"
            academicPoints < thresholds[2] -> "Average"
            academicPoints < thresholds[3] -> "Above Average"
            academicPoints < thresholds[4] -> "Smart"
            academicPoints < thresholds[5] -> "Intelligent"
            else -> "Genius"
        }
    }

    fun getCharmTitle(): String {
        return when {
            charmPoints < thresholds[1] -> "Plain"
            charmPoints < thresholds[2] -> "Unpolished"
            charmPoints < thresholds[3] -> "Confident"
            charmPoints < thresholds[4] -> "Smooth"
            charmPoints < thresholds[5] -> "Popular"
            else -> "Charismatic"
        }
    }

    fun getCourageTitle(): String {
        return when {
            couragePoints < thresholds[1] -> "Timid"
            couragePoints < thresholds[2] -> "Ordinary"
            couragePoints < thresholds[3] -> "Determined"
            couragePoints < thresholds[4] -> "Tough"
            couragePoints < thresholds[5] -> "Fearless"
            else -> "Badass"
        }
    }

    fun recalculateStats(allEntries: List<CalendarEntry>) {
        academicPoints = 0
        charmPoints = 0
        couragePoints = 0
        
        val today = LocalDate.now()

        allEntries.forEach { entry ->
            val entryDate = LocalDate.parse(entry.date, DateTimeFormatter.ISO_LOCAL_DATE)
            if (!entryDate.isAfter(today)) {
                val lowerText = entry.description.lowercase()
                
                // Academics
                if (listOf("study", "read", "library", "homework", "exam").any { lowerText.contains(it) }) {
                    academicPoints += 3
                }
                // Charm
                if (listOf("bath", "date", "movie", "coffee", "maid", "social").any { lowerText.contains(it) }) {
                    charmPoints += 3
                }
                // Courage
                if (listOf("train", "gym", "burger", "scary", "ghost", "fight", "workout").any { lowerText.contains(it) }) {
                    couragePoints += 3
                }
            }
        }
    }

    // Direct manual increases (kept for compatibility or manual study buttons)
    fun increaseAcademics(value: Int) { academicPoints += value }
    fun increaseCharm(value: Int) { charmPoints += value }
    fun increaseCourage(value: Int) { couragePoints += value }
}
