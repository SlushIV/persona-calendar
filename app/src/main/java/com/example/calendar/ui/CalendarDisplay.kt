package com.example.calendar.ui

import androidx.compose.animation.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calendar.data.CalendarController
import com.example.calendar.data.TimeSlot
import com.example.calendar.ui.theme.P5Black
import com.example.calendar.ui.theme.P5Red
import com.example.calendar.ui.theme.P5White
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CalendarScreen(controller: CalendarController, modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    
    Surface(modifier = modifier.fillMaxSize(), color = P5Black) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            // Styled Date Header
            P5Header(
                text = controller.getFormattedDate(),
                onPrev = { controller.previousDay() },
                onNext = { controller.nextDay() }
            )

            Spacer(modifier = Modifier.height(24.dp))

            TimeSlot.entries.forEach { slot ->
                P5TimeSlotItem(
                    slot = slot,
                    description = controller.dailyEntries[slot] ?: "",
                    onUpdate = { newDesc -> controller.updateEntry(slot, newDesc) }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun P5TimeSlotItem(slot: TimeSlot, description: String, onUpdate: (String) -> Unit) {
    var text by remember(description) { mutableStateOf(description) }
    var showSaved by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxWidth()) {
        // Jagged Label Background
        Box(modifier = Modifier.wrapContentSize()) {
            Canvas(modifier = Modifier.matchParentSize()) {
                val path = Path().apply {
                    moveTo(0f, 0f)
                    lineTo(size.width * 0.9f, 0f)
                    lineTo(size.width, size.height)
                    lineTo(size.width * 0.1f, size.height)
                    close()
                }
                drawPath(path, color = P5Red, style = Fill)
            }
            Text(
                text = slot.name,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Black,
                color = P5White,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                fontSize = 18.sp
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = P5White.copy(alpha = 0.1f),
                    unfocusedContainerColor = P5White.copy(alpha = 0.1f),
                    focusedTextColor = P5White,
                    unfocusedTextColor = P5White,
                    cursorColor = P5Red,
                    focusedIndicatorColor = P5Red,
                    unfocusedIndicatorColor = P5White
                ),
                placeholder = { Text("WHAT'S THE PLAN?", color = P5White.copy(alpha = 0.5f)) }
            )
            
            Spacer(modifier = Modifier.width(8.dp))

            Box {
                Button(
                    onClick = {
                        onUpdate(text)
                        scope.launch {
                            showSaved = true
                            delay(1500)
                            showSaved = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = P5Red),
                    shape = androidx.compose.ui.graphics.RectangleShape,
                    modifier = Modifier.height(56.dp)
                ) {
                    Text("SET", fontWeight = FontWeight.Black)
                }

                // Visual Feedback Overlay
                androidx.compose.animation.AnimatedVisibility(
                    visible = showSaved,
                    enter = fadeIn() + expandHorizontally(),
                    exit = fadeOut() + shrinkHorizontally()
                ) {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(P5White)
                            .padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("SAVED!!", color = P5Black, fontWeight = FontWeight.Black, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}
