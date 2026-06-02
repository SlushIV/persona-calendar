package com.example.calendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.calendar.data.CalendarController
import com.example.calendar.data.StatsController
import com.example.calendar.ui.*
import com.example.calendar.ui.theme.CalendarTheme
import com.example.calendar.ui.theme.P5Black
import com.example.calendar.ui.theme.P5Red
import com.example.calendar.ui.theme.P5White

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Month : Screen("month", "MONTH", Icons.Default.DateRange)
    object Day : Screen("day", "DAY", Icons.Default.Info)
    object Stats : Screen("stats", "STATS", Icons.Default.Person)
}

class MainActivity : ComponentActivity() {

    private val statsController: StatsController by viewModels()
    private val calendarController: CalendarController by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Connect controllers for dynamic stat evaluation
        calendarController.setStatsController(statsController)

        setContent {
            CalendarTheme {
                val navController = rememberNavController()
                
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = P5Black
                ) {
                    Scaffold(
                        containerColor = P5Black,
                        bottomBar = {
                            P5BottomNavigationBar(navController)
                        }
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = Screen.Month.route,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable(Screen.Month.route) {
                                MonthOverviewScreen(
                                    controller = calendarController,
                                    onDateSelected = {
                                        navController.navigate(Screen.Day.route)
                                    }
                                )
                            }
                            composable(Screen.Day.route) {
                                CalendarScreen(controller = calendarController)
                            }
                            composable(Screen.Stats.route) {
                                StatScreen(controller = statsController)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun P5BottomNavigationBar(navController: NavHostController) {
    val items = listOf(Screen.Month, Screen.Day, Screen.Stats)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = P5Red,
        contentColor = P5White
    ) {
        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.label) },
                label = { Text(screen.label) },
                selected = currentRoute == screen.route,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = P5Black,
                    selectedTextColor = P5Black,
                    unselectedIconColor = P5White,
                    unselectedTextColor = P5White,
                    indicatorColor = P5White
                ),
                onClick = {
                    navController.navigate(screen.route) {
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
