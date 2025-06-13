package com.example.dummyui.ui.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.Leaderboard
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dummyui.R
import com.example.dummyui.data.DummyData
import com.example.dummyui.ui.theme.DummyuiThemeColors
import kotlin.math.max

@Composable
fun MainScreen(
    navController: NavController
) {
    val colors = DummyuiThemeColors.colors
    var currentTab by remember { mutableStateOf(0) }
    val internalNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = colors.background,
                tonalElevation = 0.dp
            ) {
                NavigationBarItem(
                    selected = currentTab == 0,
                    onClick = {
                        currentTab = 0
                        internalNavController.navigate("dashboard") {
                            popUpTo("dashboard") { inclusive = true }
                        }
                    },
                    icon = {
                        Icon(
                            Icons.Outlined.Person,
                            contentDescription = "Dashboard",
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = { Text("Dashboard", fontSize = 12.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = colors.primaryButton,
                        selectedTextColor = colors.primaryButton,
                        indicatorColor = colors.cardBackground
                    )
                )
                NavigationBarItem(
                    selected = currentTab == 1,
                    onClick = {
                        currentTab = 1
                        internalNavController.navigate("leaderboard") {
                            popUpTo("leaderboard") { inclusive = true }
                        }
                    },
                    icon = {
                        Icon(
                            Icons.Outlined.Leaderboard,
                            contentDescription = "Leaderboard",
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = { Text("Leaderboard", fontSize = 12.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = colors.primaryButton,
                        selectedTextColor = colors.primaryButton,
                        indicatorColor = colors.cardBackground
                    )
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.background)
                .padding(padding)
        ) {
            DummyData.MainScreenBackgroundPattern(
                modifier = Modifier.fillMaxSize(),
                backgroundColor = colors.background,
                patternColor = colors.textPrimary.copy(alpha = 0.03f)
            )

            NavHost(
                navController = internalNavController,
                startDestination = "dashboard"
            ) {
                composable("dashboard") {
                    StudentDashboard(DummyData.mockUserProfile)
                }
                composable("leaderboard") {
                    LeaderboardScreen(DummyData.mockLeaderboardUsers)
                }
            }
        }
    }
}

@Composable
fun StudentDashboard(userProfile: DummyData.UserProfile) {
    val colors = DummyuiThemeColors.colors

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Dashboard",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = colors.textPrimary
                )

                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(colors.cardBackground)
                ) {
                    Icon(
                        Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = colors.textPrimary
                    )
                }
            }
        }

        item {
            ProfileHeader(userProfile)
        }

        item {
            StatsCard(userProfile)
        }

        item {
            MonthlyProgressCard(userProfile.monthlyProgress)
        }

        item {
            ProblemBreakdownCard(userProfile)
        }

        item {
            LanguageAndStreakCard(userProfile)
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ProfileHeader(userProfile: DummyData.UserProfile) {
    val colors = DummyuiThemeColors.colors

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colors.cardBackground
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .border(2.dp, colors.primaryButton, CircleShape)
                    .background(colors.surface)
                    .padding(3.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = userProfile.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = colors.textPrimary
                )

                Text(
                    text = "@${userProfile.username}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = colors.textSecondary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier.clip(RoundedCornerShape(16.dp)),
                        color = colors.primaryButton.copy(alpha = 0.2f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = colors.warning,
                                modifier = Modifier.size(16.dp)
                            )

                            Spacer(modifier = Modifier.width(4.dp))

                            Text(
                                text = "Rank #${userProfile.rank}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                color = colors.textPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatsCard(userProfile: DummyData.UserProfile) {
    val colors = DummyuiThemeColors.colors

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colors.cardBackground
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Statistics",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = colors.textPrimary
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatItem(
                    value = userProfile.totalQuestions.toString(),
                    label = "Problems",
                    icon = Icons.Default.Code,
                    color = Color(0xFF64DFDF)
                )

                StatItem(
                    value = userProfile.contestsParticipated.toString(),
                    label = "Contests",
                    icon = Icons.Default.EmojiEvents,
                    color = colors.warning
                )

                StatItem(
                    value = userProfile.activeDays.toString(),
                    label = "Active Days",
                    icon = Icons.Outlined.DateRange,
                    color = Color(0xFFCCB6F2)
                )
            }
        }
    }
}

@Composable
fun StatItem(
    value: String,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color
) {
    val colors = DummyuiThemeColors.colors

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(26.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = colors.textPrimary
        )

        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = colors.textSecondary
        )
    }
}

@Composable
fun MonthlyProgressCard(dailyProgress: List<DummyData.DailyProgress>) {
    val colors = DummyuiThemeColors.colors

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colors.cardBackground
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Monthly Progress",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = colors.textPrimary
                )

                Surface(
                    color = colors.primaryButton.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "May 2025",
                        style = MaterialTheme.typography.labelMedium,
                        color = colors.primaryButton,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
                    .padding(top = 12.dp, bottom = 12.dp, end = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(end = 8.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    for (i in 4 downTo 0) {
                        Text(
                            text = "${i * 2}",
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.textSecondary,
                            modifier = Modifier.padding(vertical = 9.dp)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(start = 24.dp)
                ) {
                    Canvas(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        val maxValue = 8f
                        val width = size.width
                        val height = size.height
                        val barWidth = width / dailyProgress.size

                        for (i in 0..4) {
                            val y = height - (height * i / 4)
                            drawLine(
                                color = colors.divider,
                                start = Offset(0f, y),
                                end = Offset(width, y),
                                strokeWidth = 1f
                            )
                        }

                        val points = dailyProgress.mapIndexed { index, data ->
                            val x = index * barWidth + barWidth / 2
                            val y = height - (height * data.questionsSolved / maxValue)
                            Offset(x, y)
                        }

                        for (i in 0 until points.size - 1) {
                            drawLine(
                                color = colors.primaryButton,
                                start = points[i],
                                end = points[i + 1],
                                strokeWidth = 3f,
                                cap = StrokeCap.Round
                            )
                        }

                        points.forEach { point ->
                            drawCircle(
                                color = colors.primaryButton.copy(alpha = 0.3f),
                                radius = 6f,
                                center = point
                            )

                            drawCircle(
                                color = colors.primaryButton,
                                radius = 3f,
                                center = point
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        listOf(1, 8, 15, 22, 29).forEach { day ->
                            Text(
                                text = "$day",
                                style = MaterialTheme.typography.bodySmall,
                                color = colors.textSecondary,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProblemBreakdownCard(userProfile: DummyData.UserProfile) {
    val colors = DummyuiThemeColors.colors
    var selectedDifficulty by remember { mutableStateOf<String?>(null) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colors.cardBackground
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Problem Breakdown",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = colors.textPrimary
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                DifficultyBar(
                    label = "Easy",
                    count = userProfile.easyQuestions,
                    total = userProfile.totalQuestions,
                    color = colors.success,
                    isSelected = selectedDifficulty == "Easy",
                    onClick = { selectedDifficulty = if (selectedDifficulty == "Easy") null else "Easy" }
                )

                DifficultyBar(
                    label = "Medium",
                    count = userProfile.mediumQuestions,
                    total = userProfile.totalQuestions,
                    color = colors.warning,
                    isSelected = selectedDifficulty == "Medium",
                    onClick = { selectedDifficulty = if (selectedDifficulty == "Medium") null else "Medium" }
                )

                DifficultyBar(
                    label = "Hard",
                    count = userProfile.hardQuestions,
                    total = userProfile.totalQuestions,
                    color = colors.error,
                    isSelected = selectedDifficulty == "Hard",
                    onClick = { selectedDifficulty = if (selectedDifficulty == "Hard") null else "Hard" }
                )
            }

            AnimatedVisibility(visible = selectedDifficulty != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                ) {
                    selectedDifficulty?.let { difficulty ->
                        val (count, percentage, suggestionText) = when(difficulty) {
                            "Easy" -> Triple(
                                userProfile.easyQuestions,
                                (userProfile.easyQuestions * 100f / userProfile.totalQuestions).toInt(),
                                "Try tackling more medium difficulty problems to improve your skills."
                            )
                            "Medium" -> Triple(
                                userProfile.mediumQuestions,
                                (userProfile.mediumQuestions * 100f / userProfile.totalQuestions).toInt(),
                                "Good balance! Continue practicing medium problems to strengthen your approach."
                            )
                            "Hard" -> Triple(
                                userProfile.hardQuestions,
                                (userProfile.hardQuestions * 100f / userProfile.totalQuestions).toInt(),
                                "Great job tackling hard problems! Continue to challenge yourself."
                            )
                            else -> Triple(0, 0, "")
                        }

                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            color = colors.surface
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "$difficulty Problems",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = when(difficulty) {
                                            "Easy" -> colors.success
                                            "Medium" -> colors.warning
                                            "Hard" -> colors.error
                                            else -> colors.textPrimary
                                        }
                                    )

                                    Text(
                                        text = "$count Solved ($percentage%)",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = colors.textPrimary
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = suggestionText,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = colors.textSecondary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DifficultyBar(
    label: String,
    count: Int,
    total: Int,
    color: Color,
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {
    val colors = DummyuiThemeColors.colors
    val percentage = if (total > 0) count.toFloat() / total else 0f
    val animatedPercentage by animateFloatAsState(
        targetValue = percentage,
        animationSpec = tween(1000),
        label = "Progress Animation"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(90.dp)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = if (isSelected) color else colors.textPrimary,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .width(36.dp)
                .height(140.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(colors.divider.copy(alpha = 0.3f))
                .then(
                    if (isSelected) {
                        Modifier.border(
                            width = 2.dp,
                            color = color,
                            shape = RoundedCornerShape(18.dp)
                        )
                    } else Modifier
                )
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .fillMaxHeight(animatedPercentage)
                    .clip(RoundedCornerShape(18.dp))
                    .background(color.copy(alpha = if (isSelected) 0.9f else 0.7f))
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = count.toString(),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = if (isSelected) color else colors.textPrimary
        )
    }
}

@Composable
fun LanguageAndStreakCard(userProfile: DummyData.UserProfile) {
    val colors = DummyuiThemeColors.colors

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.weight(1f),
            colors = CardDefaults.cardColors(
                containerColor = colors.cardBackground
            ),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(colors.primaryButton.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Code,
                        contentDescription = null,
                        tint = colors.primaryButton,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Language",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.textSecondary
                )

                Text(
                    text = userProfile.preferredLanguage,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = colors.textPrimary
                )
            }
        }

        Card(
            modifier = Modifier.weight(1f),
            colors = CardDefaults.cardColors(
                containerColor = colors.cardBackground
            ),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(colors.warning.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.LocalFireDepartment,
                        contentDescription = null,
                        tint = colors.warning,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Current Streak",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.textSecondary
                )

                Text(
                    text = "${userProfile.activeDays % 30} days",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = colors.textPrimary
                )
            }
        }
    }
}

@Composable
fun LeaderboardScreen(users: List<DummyData.LeaderboardUser>) {
    val colors = DummyuiThemeColors.colors
    var filterOption by remember { mutableStateOf("All Time") }
    val filterOptions = listOf("All Time", "This Month", "This Week")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Leaderboard",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = colors.textPrimary
            )

            Surface(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { /* Show dropdown */ },
                color = colors.cardBackground
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = filterOption,
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.textPrimary
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "Filter",
                        tint = colors.textSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TopLeaderItem(
                user = users.getOrNull(1) ?: return@Row,
                position = 2,
                modifier = Modifier.padding(top = 24.dp)
            )

            TopLeaderItem(
                user = users.first(),
                position = 1,
                modifier = Modifier.padding(bottom = 8.dp),
                isFirst = true
            )

            TopLeaderItem(
                user = users.getOrNull(2) ?: return@Row,
                position = 3,
                modifier = Modifier.padding(top = 32.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Surface(
            color = colors.cardBackground.copy(alpha = 0.7f),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Row(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Rank",
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.textSecondary,
                    modifier = Modifier.width(40.dp)
                )

                Text(
                    text = "User",
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.textSecondary,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "Rating",
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.textSecondary,
                    textAlign = TextAlign.End,
                    modifier = Modifier.width(48.dp)
                )
            }
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(users.drop(3)) { user ->
                LeaderboardItem(user)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun TopLeaderItem(
    user: DummyData.LeaderboardUser,
    position: Int,
    modifier: Modifier = Modifier,
    isFirst: Boolean = false
) {
    val colors = DummyuiThemeColors.colors
    val iconSize = if (isFirst) 40.dp else 32.dp
    val imageSize = if (isFirst) 70.dp else 56.dp
    val badgeColor = when(position) {
        1 -> Color(0xFFFFD700)
        2 -> Color(0xFFC0C0C0)
        3 -> Color(0xFFCD7F32)
        else -> colors.textSecondary
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.width(100.dp)
    ) {
        if (isFirst) {
            Icon(
                Icons.Default.EmojiEvents,
                contentDescription = null,
                tint = colors.warning,
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))
        }

        Box {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(imageSize)
                    .clip(CircleShape)
                    .background(colors.surface)
                    .border(3.dp, badgeColor, CircleShape),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(badgeColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = position.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = user.username,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = colors.textPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )

        Text(
            text = "${50000/(user.hardQuestions + user.contestsParticipated)} rating",
            style = MaterialTheme.typography.bodySmall,
            color = badgeColor,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun LeaderboardItem(user: DummyData.LeaderboardUser) {
    val colors = DummyuiThemeColors.colors

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colors.cardBackground
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(DummyData.getRankColor(user.rank).copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "#${user.rank}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = DummyData.getRankColor(user.rank)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(colors.surface),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = user.username,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = colors.textPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    StatChip(
                        value = user.hardQuestions.toString(),
                        label = "Problems",
                        color = colors.error
                    )

                    StatChip(
                        value = user.contestsParticipated.toString(),
                        label = "Contests",
                        color = colors.warning
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "${50000/(user.hardQuestions + user.contestsParticipated)}",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = colors.textPrimary
                )

                Text(
                    text = "rating",
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.textSecondary
                )
            }
        }
    }
}

@Composable
fun StatChip(value: String, label: String, color: Color) {
    Surface(
        shape = RoundedCornerShape(4.dp),
        color = color.copy(alpha = 0.15f)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$value $label",
                style = MaterialTheme.typography.bodySmall,
                color = color,
                fontWeight = FontWeight.Medium
            )
        }
    }
}