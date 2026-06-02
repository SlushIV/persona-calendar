package com.example.calendar.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calendar.data.CalendarController
import com.example.calendar.data.TimeSlot
import com.example.calendar.ui.theme.P5Black
import com.example.calendar.ui.theme.P5Red
import com.example.calendar.ui.theme.P5White

@Composable
fun P5DailyLogNotepad(controller: CalendarController, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer { rotationZ = 1f }
            .padding(8.dp)
    ) {
        // Notepad Paper
        Canvas(modifier = Modifier.matchParentSize()) {
            val path = Path().apply {
                moveTo(15f, 0f)
                lineTo(size.width - 5f, 15f)
                lineTo(size.width, size.height)
                lineTo(5f, size.height - 5f)
                close()
            }
            drawPath(path, color = P5White, style = Fill)
            drawPath(path, color = P5Black, style = Stroke(width = 3.dp.toPx()))
        }

        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "DAILY LOG",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Black,
                color = P5Black,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            TimeSlot.entries.forEach { slot ->
                val entry = controller.dailyEntries[slot] ?: ""
                if (entry.isNotBlank()) {
                    Column(modifier = Modifier.padding(vertical = 2.dp)) {
                        Row {
                           Text(
                                text = "${slot.name.take(3)}: ",
                                color = P5Red,
                                fontWeight = FontWeight.Black,
                                fontSize = 14.sp
                            )
                            Text(
                                text = entry.uppercase(),
                                color = P5Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                        Canvas(modifier = Modifier.fillMaxWidth().height(1.dp)) {
                            drawLine(
                                color = Color.LightGray,
                                start = androidx.compose.ui.geometry.Offset(0f, 0f),
                                end = androidx.compose.ui.geometry.Offset(size.width, 0f),
                                strokeWidth = 1.dp.toPx()
                            )
                        }
                    }
                }
            }
            
            if (controller.dailyEntries.values.all { it.isBlank() }) {
                Text(
                    text = "NO PLANS RECORDED",
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
