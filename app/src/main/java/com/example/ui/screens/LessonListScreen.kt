package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CourseRepository
import com.example.data.Lesson
import com.example.data.Module
import com.example.ui.components.LessonCard
import com.example.ui.theme.SecondaryNavy

@Composable
fun LessonListScreen(
    moduleId: String,
    onBackClick: () -> Unit,
    onLessonClick: (Lesson) -> Unit,
    modifier: Modifier = Modifier
) {
    val module = CourseRepository.getModuleById(moduleId) ?: return
    val lessons = CourseRepository.getLessonsForModule(moduleId)

    Scaffold(
        modifier = modifier.testTag("lesson_list_screen"),
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
                            contentDescription = "Back to Level 2",
                            tint = SecondaryNavy
                        )
                    }

                    Column {
                        Text(
                            text = module.moduleNumber,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF64748B)
                        )
                        Text(
                            text = module.title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            color = SecondaryNavy
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8FAFC))
                .padding(innerPadding)
        ) {
            // Summary header badge helper
            Surface(
                color = Color(0xFFEFF6FF),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Module progress info",
                        tint = Color(0xFF1A56DB),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "Complete Lesson 1 to unlock the rest of the chain timeline.",
                        fontSize = 12.sp,
                        color = Color(0xFF1E3A8A),
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(lessons) { index, lesson ->
                    Box(modifier = Modifier.fillMaxWidth()) {
                        // Left line simulation connecting Lesson circular timeline nodes
                        if (index < lessons.size - 1) {
                            Box(
                                modifier = Modifier
                                    .padding(start = 36.dp)
                                    .width(2.dp)
                                    .height(98.dp)
                                    .background(Color(0xFFE2E8F0))
                                    .align(Alignment.BottomStart)
                            )
                        }

                        LessonCard(
                            lesson = lesson,
                            onClick = { onLessonClick(lesson) }
                        )
                    }
                }
            }
        }
    }
}
