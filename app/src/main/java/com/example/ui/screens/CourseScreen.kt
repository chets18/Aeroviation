package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Course
import com.example.data.CourseRepository
import com.example.ui.annotatedMarkdownString
import com.example.ui.components.CourseCard
import com.example.ui.theme.PrimaryBlue
import com.example.ui.theme.SecondaryNavy

@Composable
fun CourseScreen(
    onCourseClick: (Course) -> Unit,
    modifier: Modifier = Modifier
) {
    var isCopilotOpen by remember { mutableStateOf(false) }
    var selectedQuestion by remember { mutableStateOf<String?>(null) }
    var answerText by remember { mutableStateOf("") }

    val copilotAnswers = mapOf(
        "supersonic" to "A wing creates lift at supersonic speeds through shock expansion theory. The leading edge creates an oblique shock wave that increases pressure on the lower surface, while expansion fans on the upper surface decrease pressure, resulting in net lift but with high wave drag.",
        "bernoulli" to "Bernoulli's Equation is: P + 0.5 * rho * V^2 = Constant. It states that for an incompressible, inviscid flow, an increase in airspeed (velocity V) occurs simultaneously with a decrease in static fluid pressure P.",
        "rocket" to "Rocket nozzles are bell-shaped to optimize the expansion of high-pressure exhaust gas into space. The contour gradually changes from supersonic throat expansion to clean horizontal exit flow, maximizing thrust and specific impulse (Isp) output."
    )

    Scaffold(
        modifier = modifier.testTag("course_screen"),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { isCopilotOpen = true },
                containerColor = SecondaryNavy,
                contentColor = Color.White,
                modifier = Modifier
                    .padding(8.dp)
                    .testTag("copilot_fab")
            ) {
                Icon(
                    imageVector = Icons.Default.Forum,
                    contentDescription = "Open AI Copilot Assistant"
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF8FAFC))
                    .padding(innerPadding)
            ) {
                // Header (AEROVIATION, Bell, Profile Icon)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "AEROVIATION",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0D2137),
                        letterSpacing = 2.sp
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = Color(0xFFEFF6FF),
                            shape = CircleShape,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = "Notifications",
                                    tint = Color(0xFF0D2137),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        // Avatar
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(Color(0xFF0D2137), CircleShape)
                                .clip(CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "CR",
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                // Scrollable content of the course screen
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Welcome back message
                    item {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                        ) {
                            val timeText = "afternoon" // Simple static logic for time of day
                            Text(
                                text = "Welcome back",
                                fontSize = 14.sp,
                                color = Color(0xFF64748B),
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "Hey Chetraj, Ready to master the skies?",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Black,
                                color = SecondaryNavy,
                                lineHeight = 30.sp
                            )
                        }
                    }

                    // Strategic challenge promo card
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("challenge_card"),
                            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        Brush.linearGradient(
                                            colors = listOf(Color(0xFF3B82F6), Color(0xFF1E3A8A))
                                        )
                                    )
                                    .padding(20.dp)
                            ) {
                                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    Surface(
                                        color = Color.White.copy(alpha = 0.2f),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.HelpOutline,
                                                contentDescription = "Challenge",
                                                tint = Color.White,
                                                modifier = Modifier.size(12.dp)
                                            )
                                            Text(
                                                text = "STRATEGIC CHALLENGE",
                                                color = Color.White,
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }

                                    Text(
                                        text = "Why does a wing create lift at supersonic speeds?",
                                        color = Color.White,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        lineHeight = 24.sp
                                    )

                                    Button(
                                        onClick = {
                                            selectedQuestion = "supersonic"
                                            answerText = copilotAnswers["supersonic"]!!
                                            isCopilotOpen = true
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color.White,
                                            contentColor = Color(0xFF1E3A8A)
                                        ),
                                        shape = RoundedCornerShape(12.dp),
                                        modifier = Modifier.testTag("solve_challenge_button")
                                    ) {
                                        Text(
                                            text = "Solve Challenge",
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 13.sp
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Learning path block header
                    item {
                        Text(
                            text = "Learning Path",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            color = SecondaryNavy,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        Text(
                            text = "Your aerospace engineering roadmap",
                            fontSize = 12.sp,
                            color = Color(0xFF64748B),
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }

                    // Render Course cards
                    items(CourseRepository.courses) { course ->
                        CourseCard(
                            course = course,
                            onClick = { onCourseClick(course) }
                        )
                    }

                    // Stats panel at bottom
                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            border = BorderStroke(1.dp, Color(0xFFF1F5F9)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(
                                    text = "CADET STATS",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF64748B)
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text("Flight Hours", fontSize = 11.sp, color = Color(0xFF94A3B8))
                                        Text("124.5 h", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = SecondaryNavy)
                                    }
                                    Column {
                                        Text("Missions Done", fontSize = 11.sp, color = Color(0xFF94A3B8))
                                        Text("12 complete", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = SecondaryNavy)
                                    }
                                    Column {
                                        Text("Global Ranking", fontSize = 11.sp, color = Color(0xFF94A3B8))
                                        Text("Top 3%", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F5132))
                                    }
                                    Column {
                                        Text("Badges Claimed", fontSize = 11.sp, color = Color(0xFF94A3B8))
                                        Text("4 primary", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = SecondaryNavy)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // AI COPILOT ASSISTANT DRAWERS / BOTTOMSHEET SIMULATOR
            AnimatedVisibility(
                visible = isCopilotOpen,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.matchParentSize()
            ) {
                // Dimmer background
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .clickable { isCopilotOpen = false },
                    contentAlignment = Alignment.BottomCenter
                ) {
                    // Chat sheet itself
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.65f)
                            .clickable(enabled = false) { /* Stops click-through */ }
                            .testTag("copilot_sheet")
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Header
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .background(SecondaryNavy, CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text("AI", color = Color.White, fontWeight = FontWeight.Black, fontSize = 13.sp)
                                    }
                                    Column {
                                        Text(
                                            text = "Aeroviation Copilot",
                                            fontWeight = FontWeight.Black,
                                            fontSize = 16.sp,
                                            color = SecondaryNavy
                                        )
                                        Text(
                                            text = "Personal Aerospace Assistant",
                                            fontSize = 11.sp,
                                            color = Color(0xFF64748B)
                                        )
                                    }
                                }

                                IconButton(
                                    onClick = { isCopilotOpen = false },
                                    modifier = Modifier.testTag("close_copilot_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Close Copilot",
                                        tint = SecondaryNavy
                                    )
                                }
                            }

                            Divider(color = Color(0xFFEFF6FF))

                            // Scrollable message arena
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .verticalScroll(rememberScrollState()),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                // Assistant introduction
                                Surface(
                                    color = Color(0xFFEFF6FF),
                                    shape = RoundedCornerShape(8.dp, 16.dp, 16.dp, 16.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = annotatedMarkdownString("Hello Cadet Chetraj! I am your Aeroviation flight helper. Tap on any core aerophysics query below to test me, or ask anything about supersonic flight contours!"),
                                        fontSize = 13.sp,
                                        color = SecondaryNavy,
                                        lineHeight = 18.sp,
                                        modifier = Modifier.padding(12.dp)
                                    )
                                }

                                if (selectedQuestion != null && answerText.isNotEmpty()) {
                                    // User chat bubble
                                    Surface(
                                        color = Color(0xFFF1F5F9),
                                        shape = RoundedCornerShape(16.dp, 16.dp, 8.dp, 16.dp),
                                        modifier = Modifier.align(Alignment.End)
                                    ) {
                                        val qTitle = if (selectedQuestion == "supersonic") {
                                            "How does supersonic lift work?"
                                        } else if (selectedQuestion == "bernoulli") {
                                            "Explain Bernoulli's principle"
                                        } else {
                                            "Why are rocket nozzles bell-shaped?"
                                        }
                                        Text(
                                            text = annotatedMarkdownString(qTitle),
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = SecondaryNavy,
                                            modifier = Modifier.padding(12.dp)
                                        )
                                    }

                                    // Bot response bubble
                                    Surface(
                                        color = Color(0xFFEFF6FF),
                                        shape = RoundedCornerShape(8.dp, 16.dp, 16.dp, 16.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = annotatedMarkdownString(answerText),
                                            fontSize = 13.sp,
                                            color = SecondaryNavy,
                                            lineHeight = 18.sp,
                                            modifier = Modifier.padding(12.dp)
                                        )
                                    }
                                }
                            }

                            // Interactive quick question chip rows
                            Text(
                                text = "QUICK STUDY GUIDES",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF94A3B8)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Surface(
                                    color = Color(0xFFEFF6FF),
                                    shape = RoundedCornerShape(8.dp),
                                    border = BorderStroke(1.dp, PrimaryBlue.copy(alpha = 0.2f)),
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable {
                                            selectedQuestion = "bernoulli"
                                            answerText = copilotAnswers["bernoulli"]!!
                                        }
                                ) {
                                    Text(
                                        text = "Bernoulli Formula",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = PrimaryBlue,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }

                                Surface(
                                    color = Color(0xFFEFF6FF),
                                    shape = RoundedCornerShape(8.dp),
                                    border = BorderStroke(1.dp, PrimaryBlue.copy(alpha = 0.2f)),
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable {
                                            selectedQuestion = "rocket"
                                            answerText = copilotAnswers["rocket"]!!
                                        }
                                ) {
                                    Text(
                                        text = "Rocket Nozzles",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = PrimaryBlue,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
