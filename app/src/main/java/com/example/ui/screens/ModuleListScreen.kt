package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Course
import com.example.data.CourseRepository
import com.example.data.Module
import com.example.ui.components.ModuleCard
import com.example.ui.theme.PrimaryBlue
import com.example.ui.theme.SecondaryNavy

@Composable
fun ModuleListScreen(
    courseId: String,
    onBackClick: () -> Unit,
    onModuleClick: (Module) -> Unit,
    modifier: Modifier = Modifier
) {
    val course = CourseRepository.getCourseById(courseId) ?: return
    val modules = CourseRepository.getModulesForCourse(courseId)

    Scaffold(
        modifier = modifier.testTag("module_list_screen"),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.testTag("back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back to Level 1",
                            tint = Color(0xFF0D2137)
                        )
                    }

                    Text(
                        text = "AEROVIATION",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0D2137),
                        letterSpacing = 2.sp
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifications indicator",
                        tint = Color(0xFF0D2137),
                        modifier = Modifier.size(20.dp)
                    )
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(Color(0xFF0D2137), CircleShape)
                            .clip(CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("CR", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8FAFC))
                .padding(innerPadding),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Course Info Large Hero Card at the top (from Image 1 style)
            item {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(Color(0xFFEFF6FF), Color(0xFFDBEAFE))
                                )
                            )
                            .padding(24.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    color = Color(0xFF16A34A),
                                    shape = CircleShape
                                ) {
                                    Text(
                                        text = "IN PROGRESS",
                                        color = Color.White,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                    )
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Rating",
                                        tint = Color(0xFFFACC15),
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        text = "4.8",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SecondaryNavy
                                    )
                                }
                            }

                            Text(
                                text = course.title,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Black,
                                color = SecondaryNavy
                            )

                            Text(
                                text = course.description,
                                fontSize = 13.sp,
                                color = SecondaryNavy.copy(alpha = 0.8f),
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }

            // Course Curriculum bar
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Course Curriculum",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        color = SecondaryNavy
                    )

                    Surface(
                        color = Color(0xFFEFF6FF),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CalendarToday,
                                contentDescription = "Time span",
                                tint = Color(0xFF1A56DB),
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "8 Weeks Left",
                                fontSize = 11.sp,
                                color = Color(0xFF1A56DB),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Timeline Module cards list
            itemsIndexed(modules) { index, module ->
                Box(modifier = Modifier.fillMaxWidth()) {
                    // Vertical Timeline Node line simulation (using canvas or bounds)
                    if (index < modules.size - 1) {
                        Box(
                            modifier = Modifier
                                .padding(start = 28.dp) // align directly with ModuleCard check circle
                                .width(2.dp)
                                .height(110.dp)
                                .background(Color(0xFFE2E8F0))
                                .align(Alignment.BottomStart)
                        )
                    }
                    
                    ModuleCard(
                        module = module,
                        onClick = { onModuleClick(module) }
                    )
                }
            }

            // Final knowledge assessment block
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                    border = BorderStroke(1.dp, Color(0xFFFCA5A5).copy(alpha = 0.5f))
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(Color(0xFFFFF1F2), Color(0xFFFFE4E6))
                                )
                            )
                            .padding(20.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = "Final Knowledge Assessment",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF991B1B)
                            )

                            Text(
                                text = "Validate your expertise in aeronautic mechanics to earn your graduation wings. This examination covers all 5 modules.",
                                fontSize = 13.sp,
                                color = Color(0xFF991B1B).copy(alpha = 0.8f),
                                lineHeight = 18.sp
                            )

                            Button(
                                onClick = { /* Assessment toggle */ },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF1E293B),
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = "Start Assessment",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
