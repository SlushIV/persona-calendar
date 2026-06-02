package com.example.calendar.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calendar.data.CalendarController
import com.example.calendar.ui.theme.P5Black
import com.example.calendar.ui.theme.P5Red
import com.example.calendar.ui.theme.P5White
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

val P5Blue = Color(0xFF00D0D0)

@Composable
fun MonthOverviewScreen(controller: CalendarController, onDateSelected: (LocalDate) -> Unit) {
    var viewMonth by remember { mutableStateOf(YearMonth.from(controller.currentDate)) }

    Surface(modifier = Modifier.fillMaxSize(), color = P5Black) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Persona 5 Styled Header
            P5Header(
                text = "${viewMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())}",
                onPrev = { viewMonth = viewMonth.minusMonths(1) },
                onNext = { viewMonth = viewMonth.plusMonths(1) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tilted Grid Section (Expanded)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.2f) // Give more priority to the calendar grid
                    .graphicsLayer { rotationZ = -3f }
            ) {
                // Weekday headers
                Row(modifier = Modifier.fillMaxWidth()) {
                    val daysOfWeek = listOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")
                    daysOfWeek.forEach { day ->
                        Text(
                            text = day,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Black,
                            color = when (day) {
                                "SUN" -> P5Red
                                "SAT" -> P5Blue
                                else -> P5White
                            },
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Grid of days
                val daysInMonth = getDaysInMonth(viewMonth)
                LazyVerticalGrid(
                    columns = GridCells.Fixed(7),
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(bottom = 8.dp)
                ) {
                    itemsIndexed(daysInMonth) { index, date ->
                        if (date != null) {
                            val isSunday = index % 7 == 0
                            val isSaturday = index % 7 == 6
                            
                            P5DayCell(
                                date = date,
                                isSelected = date == controller.currentDate,
                                hasEntries = controller.hasEntriesOnDate(date),
                                dayColor = when {
                                    isSunday -> P5Red
                                    isSaturday -> P5Blue
                                    else -> P5White
                                },
                                onClick = {
                                    controller.selectDate(date)
                                }
                            )
                        } else {
                            Spacer(modifier = Modifier.aspectRatio(1f))
                        }
                    }
                }
            }

            // Daily Log Notepad Section (Now at bottom, full width)
            P5DailyLogNotepad(
                controller = controller,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.8f) // Take up the bottom portion
                    .padding(bottom = 8.dp)
            )
        }
    }
}

@Composable
fun P5Header(text: String, onPrev: () -> Unit, onNext: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth().height(70.dp)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val path = Path().apply {
                moveTo(-20f, 15f)
                lineTo(size.width * 0.8f, 0f)
                lineTo(size.width + 20f, 45f)
                lineTo(size.width * 0.7f, size.height)
                lineTo(0f, size.height - 10f)
                close()
            }
            drawPath(path, color = P5Red, style = Fill)
        }
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onPrev) {
                Text("<", color = P5White, fontWeight = FontWeight.Black, fontSize = 24.sp)
            }
            Text(
                text = text.uppercase(),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Black,
                color = P5White,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = onNext) {
                Text(">", color = P5White, fontWeight = FontWeight.Black, fontSize = 24.sp)
            }
        }
    }
}

@Composable
fun P5DayCell(date: LocalDate, isSelected: Boolean, hasEntries: Boolean, dayColor: Color, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(4.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val path = Path().apply {
                    moveTo(size.width * 0.1f, 5f)
                    lineTo(size.width * 0.9f, 0f)
                    lineTo(size.width, size.height * 0.8f)
                    lineTo(size.width * 0.2f, size.height)
                    close()
                }
                drawPath(path, color = P5Red)
            }
        }
        
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = date.dayOfMonth.toString(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Black,
                color = if (isSelected) P5White else dayColor,
                fontSize = 22.sp
            )
            if (hasEntries) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .background(if (isSelected) P5White else P5Red)
                )
            }
        }
    }
}

fun getDaysInMonth(yearMonth: YearMonth): List<LocalDate?> {
    val firstDayOfMonth = yearMonth.atDay(1)
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7 
    val daysInMonth = yearMonth.lengthOfMonth()

    val days = mutableListOf<LocalDate?>()
    repeat(firstDayOfWeek) {
        days.add(null)
    }
    for (i in 1..daysInMonth) {
        days.add(yearMonth.atDay(i))
    }
    return days
}
