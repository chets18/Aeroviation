package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Course
import com.example.ui.theme.SecondaryNavy

@Composable
fun CourseCard(
    course: Course,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isLocked = course.isLocked

    val contentColor = SecondaryNavy
    val descriptionColor = Color(0xFF64748B)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .alpha(if (isLocked) 0.5f else 1.0f)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(16.dp),
                clip = false
            )
            .clip(RoundedCornerShape(16.dp))
            .clickable(enabled = !isLocked) { onClick() }
            .testTag("course_card_${course.id}"),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier
                .padding(20.dp)
        ) {
            // Aircraft low-opacity background simulation using Canvas / subtle lines
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .padding(10.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                // Subtle aerospace design line drawing (wings / thrust contours)
                Text(
                    text = if (isLocked) "🔒" else "✈️",
                    fontSize = 58.sp,
                    color = SecondaryNavy.copy(alpha = 0.04f)
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Level badge with clean capsule styling
                    val badgeBgColor = when (course.id) {
                        "beginner" -> Color(0xFF16A34A) // green #16A34A
                        "intermediate" -> Color(0xFF1A56DB) // blue #1A56DB
                        else -> Color(0xFF94A3B8) // general fallback/gray for expert, elite
                    }
                    Surface(
                        color = badgeBgColor,
                        shape = CircleShape,
                        modifier = Modifier.testTag("badge_${course.id}")
                    ) {
                        Text(
                            text = course.badge.uppercase(),
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }

                    if (isLocked) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Locked Course",
                            tint = Color(0xFF94A3B8),
                            modifier = Modifier.size(18.dp)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Tap to Learn",
                            tint = Color(0xFF94A3B8), // chevron color #94A3B8
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Text(
                    text = course.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = contentColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = course.description,
                    fontSize = 14.sp,
                    color = descriptionColor,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                if (!isLocked) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Learning Path progress",
                                fontSize = 11.sp,
                                color = descriptionColor.copy(alpha = 0.8f)
                            )
                            Text(
                                text = "${(course.progress * 1324 % 45).toInt()}% Complete", // Dynamic-looking complete state or 0%
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = contentColor
                            )
                        }
                        // Beautiful linear progress bar with rounded ends
                        LinearProgressIndicator(
                            progress = { 0.0f }, // Hardcoded 0% for now as per SPEC
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = Color(0xFF1A56DB), // progress bar color = #1A56DB
                            trackColor = Color(0xFFE2E8F0) // track color = #E2E8F0
                        )
                    }
                } else {
                    Surface(
                        color = Color(0xFFF8FAFC), // F8FAFC light background to fit cards cleanly
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Locked info",
                                tint = Color(0xFF94A3B8),
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "Level locked. Reach 100% on previous phase.",
                                fontSize = 11.sp,
                                color = Color(0xFF94A3B8)
                            )
                        }
                    }
                }
            }
        }
    }
}
