package com.example.calendar.data

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CalendarController(application: Application) : AndroidViewModel(application) {
    private val repository = CalendarRepository(application)
    
    var currentDate by mutableStateOf(LocalDate.now())
        private set

    var dailyEntries by mutableStateOf<Map<TimeSlot, String>>(emptyMap())
        private set

    private var statsController: StatsController? = null

    fun setStatsController(controller: StatsController) {
        this.statsController = controller
        // Initial recalculation when controller is connected
        controller.recalculateStats(repository.getAllEntries())
    }

    init {
        loadEntries()
    }

    private fun loadEntries() {
        val dateString = currentDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
        val entries = repository.getEntriesForDate(dateString)
        dailyEntries = TimeSlot.entries.associateWith { slot ->
            entries.find { it.slot == slot }?.description ?: ""
        }
    }

    fun updateEntry(slot: TimeSlot, description: String) {
        val dateString = currentDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
        repository.addOrUpdateEntry(dateString, slot, description)
        
        // Trigger global recalculation
        statsController?.recalculateStats(repository.getAllEntries())
        
        loadEntries()
    }

    fun nextDay() {
        currentDate = currentDate.plusDays(1)
        loadEntries()
    }

    fun previousDay() {
        currentDate = currentDate.minusDays(1)
        loadEntries()
    }

    fun getFormattedDate(): String {
        return currentDate.format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy"))
    }

    fun hasEntriesOnDate(date: LocalDate): Boolean {
        val dateString = date.format(DateTimeFormatter.ISO_LOCAL_DATE)
        return repository.getEntriesForDate(dateString).isNotEmpty()
    }

    fun selectDate(date: LocalDate) {
        currentDate = date
        loadEntries()
    }
}
