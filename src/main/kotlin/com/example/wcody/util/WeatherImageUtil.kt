package com.example.wcody.util

fun getSkyImageFileName(description: String, hour: Int): String {
    val timeRange = when (hour) {
        in 0..5 -> "새벽"
        in 6..8 -> "아침"
        in 9..11 -> "낮"
        in 12..17 -> "오후"
        in 18..19 -> "저녁"
        else -> "밤"
    }
    return "sky_${description}_${timeRange}.png"
}