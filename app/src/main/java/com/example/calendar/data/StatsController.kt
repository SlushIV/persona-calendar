package com.example.calendar.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class StatsController : ViewModel() {

    // public read private write
    var academicPoints by mutableIntStateOf(0)
        private set

    var charmPoints by mutableIntStateOf(0)
        private set

    var couragePoints by mutableIntStateOf(0)
        private set

    fun getAcademicsTitle(): String {
        return if (academicPoints < 50) "Slacker"
        else if (academicPoints < 100) "Average"
        else if (academicPoints < 200) "Above Average"
        else if (academicPoints < 400) "Smart"
        else if (academicPoints < 800) "Intelligent"
        else "Genius"
    }

    fun getCharmTitle(): String {
        return if (charmPoints < 50) "Plain"
        else if (charmPoints < 100) "Unpolished"
        else if (charmPoints < 200) "Confident"
        else if (charmPoints < 400) "Smooth"
        else if (charmPoints < 800) "Popular"
        else "Charismatic"
    }

    fun getCourageTitle(): String {
        return if (couragePoints < 50) "Timid"
        else if (couragePoints < 100) "Ordinary"
        else if (couragePoints < 200) "Determined"
        else if (couragePoints < 400) "Tough"
        else if (couragePoints < 800) "Fearless"
        else "Badass"
    }

    // change stats
    fun increaseAcademics(value: Int) {
        academicPoints += value
    }

    fun increaseCharm(value: Int) {
        charmPoints += value
    }

    fun increaseCourage(value: Int) {
        couragePoints += value
    }
}