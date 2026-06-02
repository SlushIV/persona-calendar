package com.example.calendar.data

import android.content.Context
import androidx.compose.runtime.mutableStateListOf

class CalendarRepository(private val context: Context) {
    // In-memory storage for now
    private val entries = mutableStateListOf<CalendarEntry>()

    fun getEntriesForDate(date: String): List<CalendarEntry> {
        return entries.filter { it.date == date }
    }

    fun addOrUpdateEntry(date: String, slot: TimeSlot, description: String) {
        val existingIndex = entries.indexOfFirst { it.date == date && it.slot == slot }
        
        if (description.isBlank()) {
            if (existingIndex != -1) entries.removeAt(existingIndex)
            return
        }

        val newEntry = CalendarEntry(date, slot, description)
        
        if (existingIndex != -1) {
            entries[existingIndex] = newEntry
        } else {
            entries.add(newEntry)
        }
    }

    fun getAllEntries(): List<CalendarEntry> {
        return entries.toList()
    }
}
