package com.example.lab13

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab13.ui.theme.Lab13Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab13Theme {
                //AnimatedVisibilityExample()
                //AnimateColorExample()
                //AnimateSizeAndPositionExample()
                AnimatedContentExample()
            }
        }
    }
}

@Composable
fun AnimatedVisibilityExample() {
    var visible by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { visible = !visible }) {
            Text(text = if (visible) "Ocultar" else "Mostrar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .background(Color.Blue)
            )
        }
    }
}

@Composable
fun AnimateColorExample() {
    var isBlue by remember { mutableStateOf(true) }

    val backgroundColor by animateColorAsState(
        targetValue = if (isBlue) Color.Red else Color.Green,
        //animationSpec = tween(durationMillis = 1000),  //tween
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,  //spring
            stiffness = Spring.StiffnessLow
        ), // animación suave de 1 segundo
        label = "colorAnimation"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(backgroundColor)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { isBlue = !isBlue }) {
            Text("Cambiar color")
        }
    }
}

@Composable
fun AnimateSizeAndPositionExample() {
    var expanded by remember { mutableStateOf(false) }

    val boxSize by animateDpAsState(
        targetValue = if (expanded) 200.dp else 100.dp,
        animationSpec = tween(durationMillis = 600),
        label = "sizeAnimation"
    )

    val offsetY by animateDpAsState(
        targetValue = if (expanded) (-150).dp else 0.dp,
        animationSpec = tween(durationMillis = 600),
        label = "offsetY"
    )

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .offset(y = offsetY)
                .size(boxSize)
                .background(Color.Red)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { expanded = !expanded }) {
            Text("Animar cuadro")
        }
    }
}

enum class UIState {
    Cargando, Contenido, Error
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContentExample() {
    var currentState by remember { mutableStateOf(UIState.Cargando) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedContent(
            targetState = currentState,
            transitionSpec = {
                fadeIn(tween(600)) with fadeOut(tween(600))
            },
            label = "stateTransition"
        ) { state ->
            when (state) {
                UIState.Cargando -> Text("Cargando...", fontSize = 20.sp)
                UIState.Contenido -> Text("¡Contenido cargado!", fontSize = 20.sp)
                UIState.Error -> Text("Ocurrió un error.", fontSize = 20.sp, color = Color.Red)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { currentState = UIState.Cargando }) {
                Text("Cargando")
            }
            Button(onClick = { currentState = UIState.Contenido }) {
                Text("Contenido")
            }
            Button(onClick = { currentState = UIState.Error }) {
                Text("Error")
            }
        }
    }
}




