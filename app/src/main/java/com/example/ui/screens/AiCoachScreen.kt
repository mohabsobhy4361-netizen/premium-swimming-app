package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.ui.graphics.Color
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Water
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AcademyData
import com.example.data.GeminiCoachService
import com.example.model.AiCoachMessage
import com.example.model.StrokeType
import com.example.ui.components.AnimatedWaterWaves
import com.example.ui.components.LuxuryBadge
import com.example.ui.theme.AquaCyan
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.ChampagneGold
import com.example.ui.theme.DeepAbyss
import com.example.ui.theme.MidnightNavy
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.SurfaceNavy
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import kotlinx.coroutines.launch

@Composable
fun AiCoachScreen(
    coachService: GeminiCoachService,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    var userInput by remember { mutableStateOf("") }
    var selectedStroke by remember { mutableStateOf(StrokeType.FREESTYLE) }
    var selectedLevel by remember { mutableStateOf("National / Competitive") }
    var isLoading by remember { mutableStateOf(false) }

    val messages = remember {
        mutableStateListOf(
            AiCoachMessage(
                id = "welcome_0",
                isUser = false,
                text = "Welcome to the Academy Biomechanics & AI Coaching Lab. I am your Gemini Master Coach. " +
                        "Whether you are refining infant water confidence, designing sprint sets for national trials, " +
                        "or dialing in triathlon breathing, ask me anything or select a stroke flaw below."
            )
        )
    }

    val swimmerLevels = listOf("Baby & Parent", "Youth Level 1-4", "Women Fitness", "Men Performance", "National / Competitive")

    fun sendPrompt(promptText: String) {
        if (promptText.isBlank() || isLoading) return
        val userMsg = AiCoachMessage(
            id = "usr_" + System.currentTimeMillis(),
            isUser = true,
            text = promptText
        )
        messages.add(userMsg)
        userInput = ""
        isLoading = true

        coroutineScope.launch {
            val systemContext = "Swimmer Level: $selectedLevel. Focus Stroke: ${selectedStroke.displayName}. " +
                    "Provide Olympic-tier analysis, exact biomechanical cues, and structured practice sets."
            val reply = coachService.queryGemini(promptText, systemContext)
            messages.add(
                AiCoachMessage(
                    id = "bot_" + System.currentTimeMillis(),
                    isUser = false,
                    text = reply
                )
            )
            isLoading = false
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MidnightNavy)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.verticalGradient(listOf(SurfaceNavy, MidnightNavy)))
                .padding(horizontal = 20.dp, vertical = 14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = ChampagneGold,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "AI SWIM COACH",
                            style = MaterialTheme.typography.titleLarge,
                            color = AquaCyan,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.5.sp
                        )
                    }
                    Text(
                        text = "Powered by Gemini 3.8 Flash • Biomechanics Telemetry",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                }
                LuxuryBadge(text = "Gemini Flash", isGold = true)
            }
        }

        AnimatedWaterWaves(height = 30.dp)

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Stroke & Level Selectors
            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.dp, BorderSubtle, RoundedCornerShape(16.dp)),
                    color = DeepAbyss
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "Target Stroke Focus",
                            color = TextSecondary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(vertical = 2.dp)
                        ) {
                            items(StrokeType.values()) { stroke ->
                                val isSelected = stroke == selectedStroke
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(if (isSelected) AquaCyan.copy(alpha = 0.2f) else SurfaceNavy)
                                        .border(1.dp, if (isSelected) AquaCyan else BorderSubtle, RoundedCornerShape(10.dp))
                                        .clickable { selectedStroke = stroke }
                                        .padding(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = stroke.displayName.substringBefore(" ("),
                                        color = if (isSelected) AquaCyan else TextSecondary,
                                        fontSize = 12.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Target Swimmer Category",
                            color = TextSecondary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(vertical = 2.dp)
                        ) {
                            items(swimmerLevels) { level ->
                                val isSelected = level == selectedLevel
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(if (isSelected) ChampagneGold.copy(alpha = 0.2f) else SurfaceNavy)
                                        .border(1.dp, if (isSelected) ChampagneGold else BorderSubtle, RoundedCornerShape(10.dp))
                                        .clickable { selectedLevel = level }
                                        .padding(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = level,
                                        color = if (isSelected) ChampagneGold else TextSecondary,
                                        fontSize = 11.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Quick Prompt Suggestions
            item {
                Column {
                    Text(
                        text = "Quick Consultation Topics",
                        style = MaterialTheme.typography.titleSmall,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(vertical = 2.dp)
                    ) {
                        items(AcademyData.quickQuestions) { q ->
                            Surface(
                                modifier = Modifier
                                    .width(220.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .border(1.dp, BorderSubtle, RoundedCornerShape(12.dp))
                                    .clickable { sendPrompt(q) },
                                color = SurfaceNavy
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.Water,
                                        contentDescription = null,
                                        tint = AquaCyan,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = q,
                                        color = TextPrimary,
                                        fontSize = 11.sp,
                                        maxLines = 2
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Message History
            items(messages) { msg ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + slideInVertically()
                ) {
                    val isBot = !msg.isUser
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = if (isBot) Arrangement.Start else Arrangement.End
                    ) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth(if (isBot) 0.95f else 0.85f)
                                .clip(RoundedCornerShape(16.dp))
                                .border(
                                    1.dp,
                                    if (isBot) AquaCyan.copy(alpha = 0.4f) else BorderSubtle,
                                    RoundedCornerShape(16.dp)
                                ),
                            color = if (isBot) DeepAbyss else SurfaceCard
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(24.dp)
                                                .clip(CircleShape)
                                                .background(if (isBot) AquaCyan.copy(alpha = 0.2f) else ChampagneGold.copy(alpha = 0.2f)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = if (isBot) Icons.Default.Pool else Icons.Default.FitnessCenter,
                                                contentDescription = null,
                                                tint = if (isBot) AquaCyan else ChampagneGold,
                                                modifier = Modifier.size(14.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = if (isBot) "Academy Master Coach" else "You",
                                            color = if (isBot) AquaCyan else ChampagneGold,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp
                                        )
                                    }

                                    if (isBot) {
                                        IconButton(
                                            onClick = {
                                                clipboardManager.setText(AnnotatedString(msg.text))
                                                Toast.makeText(context, "Copied coach instructions", Toast.LENGTH_SHORT).show()
                                            },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Icon(
                                                Icons.Default.ContentCopy,
                                                contentDescription = "Copy",
                                                tint = TextMuted,
                                                modifier = Modifier.size(14.dp)
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = msg.text,
                                    color = TextPrimary,
                                    fontSize = 13.sp,
                                    lineHeight = 19.sp
                                )
                            }
                        }
                    }
                }
            }

            if (isLoading) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            color = AquaCyan,
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Analyzing biomechanics telemetry with Gemini...",
                            color = AquaCyan,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }

        // Input Field Bar
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            color = DeepAbyss,
            shape = RoundedCornerShape(24.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = userInput,
                    onValueChange = { userInput = it },
                    placeholder = {
                        Text(
                            text = "Ask coach about technique, drills, or pace...",
                            color = TextMuted,
                            fontSize = 13.sp
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("ai_coach_input"),
                    shape = RoundedCornerShape(20.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    ),
                    maxLines = 3
                )

                IconButton(
                    onClick = { sendPrompt(userInput) },
                    enabled = userInput.isNotBlank() && !isLoading,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(if (userInput.isNotBlank() && !isLoading) AquaCyan else SurfaceNavy)
                        .testTag("send_ai_prompt_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send",
                        tint = if (userInput.isNotBlank() && !isLoading) MidnightNavy else TextMuted,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
