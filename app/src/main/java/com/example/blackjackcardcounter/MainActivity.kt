package com.example.blackjackcardcounter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blackjackcardcounter.ui.theme.BlackjackCardCounterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlackjackCardCounterTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    BlackjackCounterApp()
                }
            }
        }
    }
}

@Composable
fun BlackjackCounterApp() {
    var counter by remember { mutableIntStateOf(0) }
    var showInfo by remember { mutableStateOf(false) }
    var deckCount by remember { mutableIntStateOf(1) }
    var showDeckMenu by remember { mutableStateOf(false) }
    val image = painterResource(id = R.drawable.background)

    var totalCards by remember { mutableIntStateOf(deckCount * 52) }
    val decksRemaining = totalCards.toFloat() / 52f
    val trueCount = if (decksRemaining > 0f) {
        counter / decksRemaining
    } else 0f

    val advantageColor = when {
        trueCount > 1.5 -> Color.Green
        trueCount < -1.5 -> Color.Red
        else -> Color.Yellow
    }

    val advantage = -0.5f + (0.5f * trueCount)
    val baseWinProb = 50f + advantage
    val winProbability = baseWinProb.coerceIn(0f, 100f)
    val loseProbability = 100f - winProbability

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = image,
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_info),
                    contentDescription = "Info Icon",
                    modifier = Modifier
                        .padding(16.dp)
                        .size(32.dp)
                        .clickable { showInfo = !showInfo }
                )

                Box {
                    Text(
                        text = deckCount.toString(),
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable { showDeckMenu = true }
                    )

                    DropdownMenu(expanded = showDeckMenu, onDismissRequest = { showDeckMenu = false }) {
                        for (i in 1..8) {
                            DropdownMenuItem(
                                text = { Text(text = "$i Decks") },
                                onClick = {
                                    // Reset deckCount, counter, and totalCards
                                    deckCount = i
                                    counter = 0
                                    totalCards = i * 52
                                    showDeckMenu = false
                                }
                            )
                        }
                    }
                }
            }

            AnimatedVisibility(
                visible = showInfo,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 50.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .graphicsLayer { alpha = 0.9f },
                    contentAlignment = Alignment.TopStart
                ) {
                    Text(
                        text = "Blackjack Card Counting Rules:\n- High cards (10, J, Q, K, A) = -1\n- Neutral cards (7, 8, 9) = 0\n- Low cards (2, 3, 4, 5, 6) = +1",
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Counter: $counter",
                    fontSize = 50.sp,
                    color = Color.White,
                    modifier = Modifier.padding(45.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    CounterButton(label = "-1") {
                        // Only decrement if we have cards left
                        if (totalCards > 0) {
                            counter--
                            totalCards--
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    CounterButton(label = "0") {
                        // Neutral: no change to counter, but one card is used
                        if (totalCards > 0) {
                            counter += 0
                            totalCards--
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    CounterButton(label = "+1") {
                        if (totalCards > 0) {
                            counter++
                            totalCards--
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                AnimatedButton(label = "Reset") {
                    // Reset everything to the default for the current deckCount
                    counter = 0
                    totalCards = deckCount * 52
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .graphicsLayer { shadowElevation = 8f }
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Win Probability: ${"%.1f".format(winProbability)}%",
                        color = advantageColor,
                        fontSize = 20.sp
                    )
                    Text(
                        text = "Lose Probability: ${"%.1f".format(loseProbability)}%",
                        color = advantageColor,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}

@Composable
fun CounterButton(label: String, onClick: () -> Unit) {
    AnimatedButton(label = label, onClick = onClick)
}

@Composable
fun AnimatedButton(label: String, onClick: () -> Unit) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.1f else 1f,
        animationSpec = tween(durationMillis = 150),
        label = "Button Scale Animation"
    )
    val brightness by animateFloatAsState(
        targetValue = if (isPressed) 1.2f else 1f,
        animationSpec = tween(durationMillis = 150),
        label = "Button Brightness Animation"
    )

    Button(
        onClick = {
            isPressed = true
            onClick()
        },
        modifier = Modifier
            .scale(scale)
            .graphicsLayer { alpha = brightness }
    ) {
        Text(text = label, fontSize = 24.sp)
    }
    LaunchedEffect(isPressed) {
        if (isPressed) {
            kotlinx.coroutines.delay(100)
            isPressed = false
        }
    }
}
