package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CourseRepository
import com.example.data.LessonContentBlock
import com.example.ui.annotatedMarkdownString
import com.example.ui.theme.PrimaryBlue
import com.example.ui.theme.SecondaryNavy

@Composable
fun LessonDetailScreen(
    lessonId: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lesson = CourseRepository.getLessonById(lessonId) ?: return

    Scaffold(
        modifier = modifier.testTag("lesson_detail_screen"),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.testTag("back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Back to Level 3",
                        tint = SecondaryNavy
                    )
                }

                Text(
                    text = "AEROVIATION COMPANION LOOPS",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF64748B),
                    letterSpacing = 1.sp
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8FAFC))
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Lesson Indicator (e.g., LESSON 1)
            Surface(
                color = Color(0xFFEFF6FF),
                shape = CircleShape
            ) {
                Text(
                    text = lesson.lessonNumber.uppercase(),
                    color = PrimaryBlue,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                )
            }

            // H1 Lesson Title
            Text(
                text = annotatedMarkdownString(lesson.title),
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = SecondaryNavy,
                lineHeight = 34.sp,
                modifier = Modifier.testTag("lesson_title_h1")
            )

            // Duration and module data info row
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = "Reading Time",
                        tint = Color(0xFF64748B),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "Estimated: ${lesson.duration}",
                        fontSize = 12.sp,
                        color = Color(0xFF64748B),
                        fontWeight = FontWeight.Medium
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Book,
                        contentDescription = "Study Type",
                        tint = Color(0xFF64748B),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "Interactive Article",
                        fontSize = 12.sp,
                        color = Color(0xFF64748B),
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Subtitle / short description
            Text(
                text = annotatedMarkdownString(lesson.description),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = SecondaryNavy.copy(alpha = 0.8f),
                lineHeight = 22.sp,
                modifier = Modifier.testTag("lesson_description_subtitle")
            )

            Divider(color = Color(0xFFE2E8F0))

            // Beautiful Card showing the manual placeholder or dynamic content loop as requested
            if (lesson.coreContent.isEmpty()) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Simulated Book/Lab Icon
                        Text(
                            text = "📖",
                            fontSize = 48.sp
                        )

                        Text(
                            text = "Lesson content coming soon — to be added manually",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = SecondaryNavy,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.testTag("placeholder_coming_soon")
                        )

                        Text(
                            text = "Lesson content will be added here.",
                            fontSize = 13.sp,
                            color = Color(0xFF64748B),
                            fontFamily = FontFamily.Monospace,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.testTag("placeholder_content_area")
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Surface(
                            color = Color(0xFFF1F5F9),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "AeroLab Interactive sandbox editor will activate here in the next curriculum launch phase.",
                                fontSize = 11.sp,
                                color = Color(0xFF64748B),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(12.dp),
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            } else {
                lesson.coreContent.forEach { block ->
                    when (block) {
                        is LessonContentBlock.Paragraph -> {
                            Text(
                                text = annotatedMarkdownString(block.text),
                                fontSize = 15.sp,
                                color = Color(0xFF334155),
                                lineHeight = 22.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            )
                        }
                        is LessonContentBlock.BulletList -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 12.dp, top = 6.dp, bottom = 6.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                block.items.forEach { item ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.Top,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Text(
                                            text = "•",
                                            color = PrimaryBlue,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = annotatedMarkdownString(item),
                                            fontSize = 15.sp,
                                            color = Color(0xFF334155),
                                            lineHeight = 22.sp
                                        )
                                    }
                                }
                            }
                        }
                        is LessonContentBlock.DataTable -> {
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9)),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    // Table Headers Row
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        block.headers.forEach { header ->
                                            Text(
                                                text = header,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 13.sp,
                                                color = SecondaryNavy,
                                                modifier = Modifier.weight(1f)
                                            )
                                        }
                                    }
                                    HorizontalDivider(color = Color(0xFFCBD5E1), thickness = 1.dp)
                                    // Table Rows Row
                                    block.rows.forEach { row ->
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            row.forEach { cell ->
                                                Text(
                                                    text = cell,
                                                    fontSize = 13.sp,
                                                    color = Color(0xFF475569),
                                                    modifier = Modifier.weight(1f)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        is LessonContentBlock.DidYouKnow -> {
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDFA)),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(text = "💡", fontSize = 24.sp)
                                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                        Text(
                                            text = annotatedMarkdownString("Did You Know? - ${block.title}"),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp,
                                            color = Color(0xFF0F766E)
                                        )
                                        Text(
                                            text = annotatedMarkdownString(block.content),
                                            fontSize = 13.sp,
                                            color = Color(0xFF115E59),
                                            lineHeight = 18.sp
                                        )
                                    }
                                }
                            }
                        }
                        is LessonContentBlock.Timeline -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp),
                                verticalArrangement = Arrangement.spacedBy(24.dp)
                            ) {
                                block.items.forEach { item ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                                    ) {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            modifier = Modifier.width(60.dp)
                                        ) {
                                            Surface(
                                                color = PrimaryBlue,
                                                shape = RoundedCornerShape(8.dp)
                                            ) {
                                                Text(
                                                    text = item.year,
                                                    color = Color.White,
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                                )
                                            }
                                            Box(
                                                modifier = Modifier
                                                    .width(2.dp)
                                                    .weight(1f)
                                                    .background(Color(0xFFE2E8F0))
                                            )
                                        }
                                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                            Text(
                                                text = annotatedMarkdownString(item.event),
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 16.sp,
                                                color = SecondaryNavy
                                            )
                                            Text(
                                                text = annotatedMarkdownString(item.description),
                                                fontSize = 14.sp,
                                                color = Color(0xFF64748B),
                                                lineHeight = 20.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        is LessonContentBlock.InfoCard -> {
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(20.dp),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = block.icon, fontSize = 32.sp)
                                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                        Text(
                                            text = annotatedMarkdownString(block.title),
                                            fontWeight = FontWeight.Black,
                                            fontSize = 16.sp,
                                            color = PrimaryBlue
                                        )
                                        Text(
                                            text = annotatedMarkdownString(block.content),
                                            fontSize = 14.sp,
                                            color = Color(0xFF1E40AF),
                                            lineHeight = 20.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                if (lesson.selfCheckList.isNotEmpty()) {
                    Divider(color = Color(0xFFE2E8F0), modifier = Modifier.padding(vertical = 8.dp))
                    Text(
                        text = "Self-Check Practice",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = SecondaryNavy
                    )
                    
                    lesson.selfCheckList.forEach { question ->
                        SelfCheckCard(question)
                    }
                }
            }
        }
    }
}

@Composable
fun SelfCheckCard(question: com.example.data.SelfCheckQuestion) {
    var selectedOption by remember { mutableIntStateOf(-1) }
    var showExplanation by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = annotatedMarkdownString(question.question),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = SecondaryNavy
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                question.options.forEachIndexed { index, option ->
                    val isSelected = selectedOption == index
                    val isCorrect = index == question.correctIndex
                    
                    val backgroundColor = when {
                        isSelected && isCorrect -> Color(0xFFDCFCE7)
                        isSelected && !isCorrect -> Color(0xFFFEE2E2)
                        else -> Color.White
                    }
                    
                    val borderColor = when {
                        isSelected && isCorrect -> Color(0xFF22C55E)
                        isSelected && !isCorrect -> Color(0xFFEF4444)
                        else -> Color(0xFFE2E8F0)
                    }

                    Surface(
                        onClick = {
                            selectedOption = index
                            showExplanation = true
                        },
                        shape = RoundedCornerShape(10.dp),
                        color = backgroundColor,
                        border = BorderStroke(1.dp, borderColor),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .background(
                                        if (isSelected) borderColor else Color.Transparent,
                                        CircleShape
                                    )
                                    .border(1.dp, borderColor, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                            Text(
                                text = annotatedMarkdownString(option),
                                fontSize = 14.sp,
                                color = SecondaryNavy
                            )
                        }
                    }
                }
            }

            if (showExplanation) {
                Surface(
                    color = if (selectedOption == question.correctIndex) Color(0xFFF0FDF4) else Color(0xFFFFF1F2),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = annotatedMarkdownString(question.explanation),
                        fontSize = 12.sp,
                        color = if (selectedOption == question.correctIndex) Color(0xFF166534) else Color(0xFF991B1B),
                        modifier = Modifier.padding(12.dp),
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}
