package com.example.data

import androidx.compose.runtime.mutableStateMapOf

object UserProgressTracker {
    val completedLessons = mutableStateMapOf<String, Boolean>()
    val examScores = mutableStateMapOf<String, Int>()
}
