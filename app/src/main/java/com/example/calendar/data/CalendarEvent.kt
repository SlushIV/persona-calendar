package com.example.calendar.data

enum class TimeSlot { MORNING, DAYTIME, AFTERNOON, NIGHT }

data class CalendarEntry(
    val date: String, // e.g., "2024-05-28"
    val slot: TimeSlot,
    val description: String
)
