package com.example.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AssignmentTurnedIn
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ui.screens.*

sealed class Screen(val route: String) {
    object Course : Screen("course_home")
    object Modules : Screen("modules_list/{courseId}") {
        fun createRoute(courseId: String) = "modules_list/$courseId"
    }
    object Lessons : Screen("lessons_list/{moduleId}") {
        fun createRoute(moduleId: String) = "lessons_list/$moduleId"
    }
    object LessonDetail : Screen("lesson_detail/{lessonId}") {
        fun createRoute(lessonId: String) = "lesson_detail/$lessonId"
    }
    object Test : Screen("test_tab")
    object Profile : Screen("profile_tab")
}

data class BottomNavItem(
    val route: String,
    val title: String,
    val activeIcon: ImageVector,
    val inactiveIcon: ImageVector,
    val testTag: String
)

@Composable
fun MainRootContainer(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val navigationItems = listOf(
        BottomNavItem(
            route = Screen.Course.route,
            title = "Course",
            activeIcon = Icons.Filled.Home,
            inactiveIcon = Icons.Outlined.Home,
            testTag = "course_nav_tab"
        ),
        BottomNavItem(
            route = Screen.Test.route,
            title = "Test",
            activeIcon = Icons.Filled.AssignmentTurnedIn,
            inactiveIcon = Icons.Outlined.AssignmentTurnedIn,
            testTag = "test_nav_tab"
        ),
        BottomNavItem(
            route = Screen.Profile.route,
            title = "Profile",
            activeIcon = Icons.Filled.Person,
            inactiveIcon = Icons.Outlined.Person,
            testTag = "profile_nav_tab"
        )
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .testTag("main_bottom_nav")
                    .drawBehind {
                        drawLine(
                            color = Color(0xFFE2E8F0),
                            start = Offset(0f, 0f),
                            end = Offset(size.width, 0f),
                            strokeWidth = 1.dp.toPx()
                        )
                    },
                containerColor = Color.White,
                tonalElevation = 0.dp
            ) {
                navigationItems.forEach { item ->
                    val isCourseTabActive = item.route == Screen.Course.route && (
                        currentRoute == Screen.Course.route ||
                        currentRoute == Screen.Modules.route ||
                        currentRoute == Screen.Lessons.route ||
                        currentRoute == Screen.LessonDetail.route
                    )
                    
                    val isSelected = currentRoute == item.route || isCourseTabActive

                    NavigationBarItem(
                        modifier = Modifier.testTag(item.testTag),
                        icon = {
                            Icon(
                                imageVector = if (isSelected) item.activeIcon else item.inactiveIcon,
                                contentDescription = item.title
                            )
                        },
                        label = {
                            Text(
                                text = item.title,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) androidx.compose.ui.text.font.FontWeight.Bold else androidx.compose.ui.text.font.FontWeight.Normal
                            )
                        },
                        selected = isSelected,
                        alwaysShowLabel = true,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF1A56DB),
                            selectedTextColor = Color(0xFF1A56DB),
                            unselectedIconColor = Color(0xFF94A3B8),
                            unselectedTextColor = Color(0xFF94A3B8),
                            indicatorColor = Color.Transparent
                        ),
                        onClick = {
                            if (currentRoute != item.route) {
                                navController.navigate(item.route) {
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    // Avoid multiple copies of the same destination when
                                    // reselecting the same item
                                    launchSingleTop = true
                                    // Restore state when reselecting a previously selected item
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Course.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Level 1: Course home dashboard
            composable(Screen.Course.route) {
                CourseScreen(
                    onCourseClick = { course ->
                        navController.navigate(Screen.Modules.createRoute(course.id))
                    }
                )
            }

            // Level 2: Modules List for tapped Course
            composable(
                route = Screen.Modules.route,
                arguments = listOf(navArgument("courseId") { type = NavType.StringType })
            ) { backStackEntry ->
                val courseId = backStackEntry.arguments?.getString("courseId") ?: "beginner"
                ModuleListScreen(
                    courseId = courseId,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onModuleClick = { module ->
                        navController.navigate(Screen.Lessons.createRoute(module.id))
                    }
                )
            }

            // Level 3: Lessons List for tapped Module
            composable(
                route = Screen.Lessons.route,
                arguments = listOf(navArgument("moduleId") { type = NavType.StringType })
            ) { backStackEntry ->
                val moduleId = backStackEntry.arguments?.getString("moduleId") ?: "b_mod1"
                LessonListScreen(
                    moduleId = moduleId,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onLessonClick = { lesson ->
                        navController.navigate(Screen.LessonDetail.createRoute(lesson.id))
                    }
                )
            }

            // Level 4: Lesson Detail view for tapped Lesson
            composable(
                route = Screen.LessonDetail.route,
                arguments = listOf(navArgument("lessonId") { type = NavType.StringType })
            ) { backStackEntry ->
                val lessonId = backStackEntry.arguments?.getString("lessonId") ?: "b_m1_l1"
                LessonDetailScreen(
                    lessonId = lessonId,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            // Test screen tab
            composable(Screen.Test.route) {
                TestScreen()
            }

            // Profile screen tab
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
        }
    }
}
