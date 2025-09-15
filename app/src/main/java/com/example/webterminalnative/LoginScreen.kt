package com.example.webterminalnative

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil.compose.rememberAsyncImagePainter

@Composable
fun LoginScreen(
    onLogin: (username: String, password: String) -> Unit,
    onLoginWithGitHub: () -> Unit,
    onLoginWithGoogle: () -> Unit,
    onRegister: () -> Unit,
    onForgotPassword: () -> Unit,
    onOpenTos: () -> Unit,
    errorMessage: String? = null
) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var agreeTos by remember { mutableStateOf(false) }
    var videoEnded by remember { mutableStateOf(false) }

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri("android.resource://${context.packageName}/${R.raw.log}")
            setMediaItem(mediaItem)
            playWhenReady = true
            repeatMode = ExoPlayer.REPEAT_MODE_OFF
            prepare()
        }
    }

    DisposableEffect(exoPlayer) {
        val listener = object : com.google.android.exoplayer2.Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == com.google.android.exoplayer2.Player.STATE_ENDED) {
                    videoEnded = true
                }
            }
        }
        exoPlayer.addListener(listener)
        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (!videoEnded) {
            AndroidView(
                factory = { ctx ->
                    PlayerView(ctx).apply {
                        layoutParams = FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        useController = false
                        player = exoPlayer
                        resizeMode = com.google.android.exoplayer2.ui.AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Image(
                painter = rememberAsyncImagePainter(model = R.drawable.login_final_bg),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = 360.dp)
                    .fillMaxWidth()
                    .background(
                        color = Color.Black.copy(alpha = 0.75f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Login", style = MaterialTheme.typography.headlineSmall, color = Color.White)
                Text(
                    text = "Create an account",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF7EDCFF),
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .clickable { onRegister() }
                )
                if (!errorMessage.isNullOrBlank()) {
                    Text(
                        text = errorMessage,
                        color = Color(0xFFFF6B6B),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                }
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Forgot your password?",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF7EDCFF),
                    modifier = Modifier.clickable { onForgotPassword() }
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 12.dp)
                ) {
                    Checkbox(checked = agreeTos, onCheckedChange = { agreeTos = it })
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "I agree to the Terms of Service",
                        color = Color.White,
                        modifier = Modifier.clickable { onOpenTos() }
                    )
                }
                Button(
                    onClick = { if (agreeTos) onLogin(username, password) },
                    enabled = agreeTos,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                ) {
                    Text("Login")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = onLoginWithGitHub,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Login with GitHub")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = onLoginWithGoogle,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Login with Google")
                }
            }
        }
    }
}
