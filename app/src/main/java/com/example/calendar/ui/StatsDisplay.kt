package com.example.calendar.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calendar.data.StatsController
import com.example.calendar.ui.theme.P5Black
import com.example.calendar.ui.theme.P5Red
import com.example.calendar.ui.theme.P5White

@Composable
fun StatScreen(controller: StatsController, modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    Surface(modifier = modifier.fillMaxSize(), color = P5Black) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            // P5 Header for Stats
            Box(modifier = Modifier.fillMaxWidth().height(100.dp)) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val path = Path().apply {
                        moveTo(-20f, 0f)
                        lineTo(size.width * 0.8f, 10f)
                        lineTo(size.width + 20f, size.height - 10f)
                        lineTo(20f, size.height + 10f)
                        close()
                    }
                    drawPath(path, color = P5Red, style = Fill)
                }
                Text(
                    text = "SOCIAL STATS",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Black,
                    color = P5White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            P3SegmentedStatItem("ACADEMICS", controller.academicPoints, controller.getAcademicsTitle(), controller.thresholds)
            Spacer(modifier = Modifier.height(32.dp))
            P3SegmentedStatItem("CHARM", controller.charmPoints, controller.getCharmTitle(), controller.thresholds)
            Spacer(modifier = Modifier.height(32.dp))
            P3SegmentedStatItem("COURAGE", controller.couragePoints, controller.getCourageTitle(), controller.thresholds)
        }
    }
}

@Composable
fun P3SegmentedStatItem(label: String, points: Int, rank: String, thresholds: List<Int>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = label,
                color = P5White,
                fontWeight = FontWeight.Black,
                fontSize = 24.sp
            )
            Text(
                text = points.toString(),
                color = P5Red,
                fontWeight = FontWeight.Black,
                fontSize = 40.sp
            )
        }
        
        Text(
            text = "RANK: $rank",
            color = P5White,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Black
        )

        Spacer(modifier = Modifier.height(12.dp))

        // P3-Style Segmented Bar
        Row(
            modifier = Modifier.fillMaxWidth().height(30.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (i in 0 until 5) {
                val startThreshold = thresholds[i]
                val endThreshold = thresholds[i + 1]
                val segmentProgress = when {
                    points >= endThreshold -> 1f
                    points <= startThreshold -> 0f
                    else -> (points - startThreshold).toFloat() / (endThreshold - startThreshold)
                }
                
                Segment(progress = segmentProgress, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun Segment(progress: Float, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxHeight()) {
        val path = Path().apply {
            moveTo(10f, 0f)
            lineTo(size.width, 0f)
            lineTo(size.width - 10f, size.height)
            lineTo(0f, size.height)
            close()
        }
        
        // Background/Empty segment
        drawPath(path, color = P5White.copy(alpha = 0.1f), style = Fill)
        
        // Progress fill
        if (progress > 0) {
            val fillWidth = size.width * progress
            val fillPath = Path().apply {
                moveTo(10f, 0f)
                lineTo(fillWidth, 0f)
                lineTo(fillWidth - 10f, size.height)
                lineTo(0f, size.height)
                close()
            }
            drawPath(fillPath, color = P5Red, style = Fill)
        }
        
        // Border
        drawPath(path, color = P5White, style = Stroke(width = 2.dp.toPx()))
    }
}
