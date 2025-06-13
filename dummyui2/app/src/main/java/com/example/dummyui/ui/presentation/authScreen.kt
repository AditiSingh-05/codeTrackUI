package com.example.dummyui.ui.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import com.example.dummyui.ui.theme.DummyuiThemeColors
import androidx.compose.material3.LocalTextStyle

enum class AuthMode {
    LOGIN, SIGNUP
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(navController: NavController) {
    val colors = DummyuiThemeColors.colors
    var currentTab by remember { mutableStateOf(AuthMode.LOGIN) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        BackgroundPattern(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(0f),
            backgroundColor = colors.background,
            patternColor = Color.White
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF222222))
                        .clickable { /* Handle back navigation */ },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Go ahead and set up\nyour account",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        lineHeight = 40.sp,
                        color = colors.textPrimary
                    ),
                    color = colors.textPrimary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Sign in/up to enjoy the best code tracking experience",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = colors.textSecondary
                    ),
                    color = colors.textSecondary
                )

                Spacer(modifier = Modifier.height(32.dp))
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colors.surface,
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 24.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(50))
                                .background(
                                    if (!isSystemInDarkTheme())
                                        Color(0xFFF5F5F5)
                                    else
                                        colors.cardBackground.copy(alpha = 0.9f)
                                )
                                .padding(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(50))
                                    .background(if (currentTab == AuthMode.LOGIN) Color.White else Color.Transparent)
                                    .clickable { currentTab = AuthMode.LOGIN }
                                    .padding(vertical = 12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Login",
                                    fontWeight = FontWeight.Medium,
                                    color = if (currentTab == AuthMode.LOGIN) Color.Black else Color.Gray
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(50))
                                    .background(if (currentTab == AuthMode.SIGNUP) Color.White else Color.Transparent)
                                    .clickable { currentTab = AuthMode.SIGNUP }
                                    .padding(vertical = 12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Register",
                                    fontWeight = FontWeight.Medium,
                                    color = if (currentTab == AuthMode.SIGNUP) Color.Black else Color.Gray
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        AnimatedVisibility(
                            visible = currentTab == AuthMode.LOGIN,
                            enter = slideInHorizontally(animationSpec = tween(300)) { -it } + fadeIn(
                                tween(300)
                            ),
                            exit = slideOutHorizontally(animationSpec = tween(300)) { it } + fadeOut(
                                tween(300)
                            )
                        ) {
                            Column {
                                OutlinedTextField(
                                    value = email,
                                    onValueChange = { email = it },
                                    label = { Text("Email Address", color = colors.textSecondary) },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.Email,
                                            contentDescription = null,
                                            tint = Color(0xFF7D8F69)
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = Color(0xFF7D8F69),
                                        unfocusedBorderColor = Color(0xFFE0E0E0),
                                        focusedTextColor = colors.textPrimary,
                                        unfocusedTextColor = colors.textPrimary
                                    ),
                                    textStyle = LocalTextStyle.current.copy(color = colors.textPrimary)
                                )

                                OutlinedTextField(
                                    value = password,
                                    onValueChange = { password = it },
                                    label = { Text("Password", color = colors.textSecondary) },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.Lock,
                                            contentDescription = null,
                                            tint = Color(0xFF7D8F69)
                                        )
                                    },
                                    trailingIcon = {
                                        IconButton(onClick = {
                                            passwordVisible = !passwordVisible
                                        }) {
                                            Icon(
                                                imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                                contentDescription = if (passwordVisible) "Hide password" else "Show password",
                                                tint = Color.Gray
                                            )
                                        }
                                    },
                                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = Color(0xFF7D8F69),
                                        unfocusedBorderColor = Color(0xFFE0E0E0),
                                        focusedTextColor = colors.textPrimary,
                                        unfocusedTextColor = colors.textPrimary
                                    ),
                                    textStyle = LocalTextStyle.current.copy(color = colors.textPrimary)
                                )

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Checkbox(
                                            checked = rememberMe,
                                            onCheckedChange = { rememberMe = it }
                                        )
                                        Text(
                                            text = "Remember me",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = colors.textSecondary
                                        )
                                    }

                                    Text(
                                        text = "Forgot Password?",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color(0xFF7D8F69),
                                        modifier = Modifier.clickable { /* Handle forgot password */ }
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Button(
                                    onClick = {
                                        isLoading = true
                                        navController.navigate("main_screen")
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(56.dp),
                                    enabled = !isLoading,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF7D8F69),
                                        disabledContainerColor = Color(0xFF7D8F69).copy(alpha = 0.5f)
                                    ),
                                    shape = RoundedCornerShape(50)
                                ) {
                                    Text(
                                        text = "Login",
                                        style = MaterialTheme.typography.labelLarge,
                                        color = Color.White,
                                        fontWeight = FontWeight.Medium
                                    )
                                }

                                Spacer(modifier = Modifier.height(20.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Divider(
                                        modifier = Modifier.weight(1f),
                                        color = Color(0xFFE0E0E0)
                                    )
                                    Text(
                                        text = "Or login with",
                                        modifier = Modifier.padding(horizontal = 16.dp),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray
                                    )
                                    Divider(
                                        modifier = Modifier.weight(1f),
                                        color = Color(0xFFE0E0E0)
                                    )
                                }

                                Spacer(modifier = Modifier.height(20.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    SocialButton(
                                        text = "Google",
                                        onClick = { /* Handle Google sign in */ },
                                        modifier = Modifier.weight(1f)
                                    )

                                    SocialButton(
                                        text = "Facebook",
                                        onClick = { /* Handle Facebook sign in */ },
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }

                        AnimatedVisibility(
                            visible = currentTab == AuthMode.SIGNUP,
                            enter = slideInHorizontally(animationSpec = tween(300)) { it } + fadeIn(
                                tween(300)
                            ),
                            exit = slideOutHorizontally(animationSpec = tween(300)) { -it } + fadeOut(
                                tween(300)
                            )
                        ) {
                            Column {
                                OutlinedTextField(
                                    value = username,
                                    onValueChange = { username = it },
                                    label = { Text("Username", color = colors.textSecondary) },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.Person,
                                            contentDescription = null,
                                            tint = Color(0xFF7D8F69)
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = Color(0xFF7D8F69),
                                        unfocusedBorderColor = Color(0xFFE0E0E0),
                                        focusedTextColor = colors.textPrimary,
                                        unfocusedTextColor = colors.textPrimary
                                    ),
                                    textStyle = LocalTextStyle.current.copy(color = colors.textPrimary)
                                )

                                OutlinedTextField(
                                    value = email,
                                    onValueChange = { email = it },
                                    label = { Text("Email Address", color = colors.textSecondary) },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.Email,
                                            contentDescription = null,
                                            tint = Color(0xFF7D8F69)
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = Color(0xFF7D8F69),
                                        unfocusedBorderColor = Color(0xFFE0E0E0),
                                        focusedTextColor = colors.textPrimary,
                                        unfocusedTextColor = colors.textPrimary
                                    ),
                                    textStyle = LocalTextStyle.current.copy(color = colors.textPrimary)
                                )

                                OutlinedTextField(
                                    value = password,
                                    onValueChange = { password = it },
                                    label = { Text("Password", color = colors.textSecondary) },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.Lock,
                                            contentDescription = null,
                                            tint = Color(0xFF7D8F69)
                                        )
                                    },
                                    trailingIcon = {
                                        IconButton(onClick = {
                                            passwordVisible = !passwordVisible
                                        }) {
                                            Icon(
                                                imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                                contentDescription = if (passwordVisible) "Hide password" else "Show password",
                                                tint = Color.Gray
                                            )
                                        }
                                    },
                                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = Color(0xFF7D8F69),
                                        unfocusedBorderColor = Color(0xFFE0E0E0),
                                        focusedTextColor = colors.textPrimary,
                                        unfocusedTextColor = colors.textPrimary
                                    ),
                                    textStyle = LocalTextStyle.current.copy(color = colors.textPrimary)
                                )

                                Spacer(modifier = Modifier.height(24.dp))

                                Button(
                                    onClick = {
                                        isLoading = true
                                        navController.navigate("onboarding_screen")
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(56.dp),
                                    enabled = !isLoading,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF7D8F69),
                                        disabledContainerColor = Color(0xFF7D8F69).copy(alpha = 0.5f)
                                    ),
                                    shape = RoundedCornerShape(50)
                                ) {
                                    Text(
                                        text = "Register",
                                        style = MaterialTheme.typography.labelLarge,
                                        color = Color.White,
                                        fontWeight = FontWeight.Medium
                                    )
                                }

                                Spacer(modifier = Modifier.height(20.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Divider(
                                        modifier = Modifier.weight(1f),
                                        color = Color(0xFFE0E0E0)
                                    )
                                    Text(
                                        text = "Or login with",
                                        modifier = Modifier.padding(horizontal = 16.dp),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray
                                    )
                                    Divider(
                                        modifier = Modifier.weight(1f),
                                        color = Color(0xFFE0E0E0)
                                    )
                                }

                                Spacer(modifier = Modifier.height(20.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    SocialButton(
                                        text = "Google",
                                        onClick = { /* Handle Google sign in */ },
                                        modifier = Modifier.weight(1f)
                                    )

                                    SocialButton(
                                        text = "GitHub",
                                        onClick = { /* Handle Facebook sign in */ },
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(isLoading) {
        if (isLoading) {
            delay(1500)
            isLoading = false
        }
    }
}

@Composable
fun SocialButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(48.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(
            width = 1.dp,
            color = Color(0xFFE0E0E0)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )
        }
    }
}