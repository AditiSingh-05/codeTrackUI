package com.example.dummyui.ui.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import com.example.dummyui.ui.theme.DummyuiThemeColors
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

data class OnboardingItem(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val actionText: String,
    val iconTint: Color
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    navController: NavController
) {
    val colors = DummyuiThemeColors.colors
    val items = listOf(
        OnboardingItem(
            title = "Track Your Coding Progress",
            description = "Visualize your solved problems, monitor daily streaks, and never miss a day of practice.",
            icon = Icons.Default.TrendingUp,
            actionText = "Skip",
            iconTint = colors.primaryButton
        ),
        OnboardingItem(
            title = "Compete & Climb the Leaderboard",
            description = "Participate in contests, challenge friends, and see how you rank in the CodeTrack community.",
            icon = Icons.Default.Leaderboard,
            actionText = "Skip",
            iconTint = colors.warning
        ),
        OnboardingItem(
            title = "Get Notified, Stay Ahead",
            description = "Receive instant notifications for new challenges, contest reminders, and streak alerts.",
            icon = Icons.Default.Notifications,
            actionText = "Get Started",
            iconTint = colors.secondaryButton
        )
    )
    val pagerState = rememberPagerState(pageCount = { items.size })
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        BackgroundPatternWithDottedLines(
            modifier = Modifier.fillMaxSize(),
            backgroundColor = colors.background,
            patternColor = colors.textPrimary
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, end = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = {
                        navController.navigate("main_screen")
                    }
                ) {
                    Text(
                        text = "Skip",
                        color = colors.textPrimary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            Spacer(modifier = Modifier.weight(0.1f))
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.7f),
                pageSpacing = 0.dp
            ) { page ->
                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
                            lerp(
                                start = 0.85f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            ).also { scale ->
                                scaleX = scale
                                scaleY = scale
                            }
                            alpha = lerp(
                                start = 0.5f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )
                        }
                ) {
                    OnboardingCard(
                        item = items[page],
                        onAction = {
                            if (page < items.size - 1) {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(page + 1)
                                }
                            } else {
                                navController.navigate("main_screen")
                            }
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.weight(0.05f))
            PageIndicator(
                pagerState = pagerState,
                items = items
            )
            Spacer(modifier = Modifier.weight(0.15f))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PageIndicator(
    pagerState: PagerState,
    items: List<OnboardingItem>
) {
    val colors = DummyuiThemeColors.colors
    Row(
        modifier = Modifier
            .height(30.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEachIndexed { index, item ->
            val isSelected = index == pagerState.currentPage
            val indicatorColor = if (isSelected) {
                item.iconTint
            } else {
                colors.textSecondary.copy(alpha = 0.3f)
            }
            Box(
                modifier = Modifier
                    .padding(horizontal = 6.dp)
                    .size(if (isSelected) 10.dp else 8.dp)
                    .clip(CircleShape)
                    .background(indicatorColor)
                    .shadow(
                        elevation = if (isSelected) 2.dp else 0.dp,
                        shape = CircleShape
                    )
            )
        }
    }
}

@Composable
fun OnboardingCard(
    item: OnboardingItem,
    onAction: () -> Unit
) {
    val colors = DummyuiThemeColors.colors
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colors.cardBackground
        ),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 24.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.foundation.Canvas(
                    modifier = Modifier.size(140.dp)
                ) {
                    drawCircle(
                        color = item.iconTint,
                        radius = size.minDimension / 2,
                        style = Stroke(width = 4.dp.toPx())
                    )
                }
                Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    tint = item.iconTint,
                    modifier = Modifier.size(100.dp)
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    text = item.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.textPrimary,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = item.description,
                    fontSize = 16.sp,
                    color = colors.textSecondary,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            if (item.actionText == "Get Started") {
                Button(
                    onClick = onAction,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.primaryButton
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = item.actionText,
                        color = colors.surface,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun BackgroundPatternWithDottedLines(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFF121212),
    patternColor: Color = Color.White
) {
    Box(modifier = modifier) {
        androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
            val dotRadius = 1.2f
            val gapBetweenDots = 20f
            val lineColor = patternColor.copy(alpha = 0.15f)
            for (y in 0..size.height.toInt() step gapBetweenDots.toInt()) {
                for (x in 0..size.width.toInt() step gapBetweenDots.toInt()) {
                    drawCircle(
                        color = lineColor,
                        radius = dotRadius,
                        center = androidx.compose.ui.geometry.Offset(x.toFloat(), y.toFloat())
                    )
                }
            }
            val curves = 3
            for (curve in 0 until curves) {
                val curveControlY = size.height * (curve + 1) / (curves + 1)
                val curveWidth = size.width
                val steps = 50
                for (i in 0..steps) {
                    val fraction = i.toFloat() / steps
                    val x = curveWidth * fraction
                    val amplitude = 80f + (curve * 20f)
                    val offsetY = kotlin.math.sin(fraction * Math.PI * 2) * amplitude
                    drawCircle(
                        color = lineColor,
                        radius = dotRadius,
                        center = androidx.compose.ui.geometry.Offset(x, curveControlY + offsetY.toFloat())
                    )
                }
            }
        }
    }
}
