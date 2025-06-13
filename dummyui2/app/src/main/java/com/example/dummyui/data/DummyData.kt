package com.example.dummyui.data

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color


object DummyData {
    data class UserProfile(
        val id: String,
        val username: String,
        val name: String,
        val profileImageUrl: String,
        val rank: Int,
        val totalQuestions: Int,
        val easyQuestions: Int,
        val mediumQuestions: Int,
        val hardQuestions: Int,
        val contestsParticipated: Int,
        val activeDays: Int,
        val preferredLanguage: String,
        val monthlyProgress: List<DailyProgress>


    )
    data class DailyProgress(
        val day: Int,
        val questionsSolved: Int
    )

    data class LeaderboardUser(
        val id: String,
        val username: String,
        val profileImageUrl: String,
        val rank: Int,
        val contestsParticipated: Int,
        val hardQuestions: Int,
        val activeDays: Int
    )


    val mockUserProfile = DummyData.UserProfile(
        id = "1",
        username = "developer104",
        name = "Alex Johnson",
        profileImageUrl = "",
        rank = 14,
        totalQuestions = 387,
        easyQuestions = 185,
        mediumQuestions = 152,
        hardQuestions = 50,
        contestsParticipated = 24,
        activeDays = 167,
        preferredLanguage = "Java",
        monthlyProgress = List(30) { index ->
            DummyData.DailyProgress(
                day = index + 1,
                questionsSolved = (1..5).random()
            )
        }
    )

    val mockLeaderboardUsers = List(15) { index ->
        DummyData.LeaderboardUser(
            id = (index + 1).toString(),
            username = "dev${100 + index}",
            profileImageUrl = "",
            rank = index + 1,
            contestsParticipated = (5..25).random(),
            hardQuestions = (10..80).random(),
            activeDays = (30..250).random()
        )
    }.sortedBy { it.rank }


    fun getRankColor(rank: Int): Color {
        return when {
            rank == 1 -> Color(0xFFFFD700)  // Gold
            rank == 2 -> Color(0xFFC0C0C0)  // Silver
            rank == 3 -> Color(0xFFCD7F32)  // Bronze
            rank <= 10 -> Color(0xFF7D8F69) // Green
            rank <= 50 -> Color(0xFF5D8AA8) // Blue
            else -> Color(0xFF808080)       // Gray
        }
    }


    @Composable
    fun MainScreenBackgroundPattern(
        modifier: Modifier = Modifier,
        backgroundColor: Color,
        patternColor: Color
    ) {
        Box(modifier = modifier.background(backgroundColor)) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val dotRadius = 1.5f
                val spacing = 40f

                for (x in 0..size.width.toInt() step spacing.toInt()) {
                    for (y in 0..size.height.toInt() step spacing.toInt()) {
                        drawCircle(
                            color = patternColor,
                            radius = dotRadius,
                            center = Offset(x.toFloat(), y.toFloat())
                        )
                    }
                }
            }
        }
    }

}