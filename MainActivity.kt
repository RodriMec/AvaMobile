package com.example.calculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorApp()
        }
    }
}

@Composable
fun CalculatorApp() {
    var currentExpression by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = if (result.isEmpty()) currentExpression else result,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End
        )

        Spacer(modifier = Modifier.height(8.dp))

        val buttons = listOf(
            listOf("7", "8", "9", "/"),
            listOf("4", "5", "6", "*"),
            listOf("1", "2", "3", "-"),
            listOf("0", ".", "=", "+"),
            listOf("C")
        )

        for (row in buttons) {
            Row(
                modifier = Modifier.fillMaxWidth(maxOf(24f, row.size.toFloat())),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                for (buttonText in row) {
                    Button(
                        onClick = {
                            when (buttonText) {
                                "=" -> {
                                    try {
                                        result = evaluateExpression(currentExpression).toString()
                                    } catch (e: Exception) {
                                        result = "Erro"
                                    }
                                    currentExpression = ""
                                }
                                "C" -> {
                                    currentExpression = ""
                                    result = ""
                                }
                                else -> {
                                    if (result.isNotEmpty()) {
                                        currentExpression = ""
                                        result = ""
                                    }
                                    currentExpression += buttonText
                                }
                            }
                        },
                        modifier = Modifier
                            .weight(4f)
                            .aspectRatio(1f)
                    ) {
                        Text(text = buttonText, fontSize = 20.sp)
                    }
                }
            }
        }
        // Personalizando o botão "C" (Limpar)
        Row(
            modifier = Modifier.fillMaxWidth(5f),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    currentExpression = ""
                    result = ""
                },
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .height(30.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColorFor(backgroundColor = MaterialTheme.colorScheme.onSecondary)
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = "C", fontSize = 50.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

fun evaluateExpression(expression: String): Double {
    return object : Any() {
        var pos = -1
        var ch = 0

        fun nextChar() {
            ch = if (++pos < expression.length) expression[pos].code else -1
        }

        fun eat(charToEat: Int): Boolean {
            while (ch == ' '.code) nextChar()
            if (ch == charToEat) {
                nextChar()
                return true
            }
            return false
        }

        fun parse(): Double {
            nextChar()
            val x = parseExpression()
            if (pos < expression.length) throw RuntimeException("Unexpected: " + ch.toChar())
            return x
        }

        fun parseExpression(): Double {
            var x = parseTerm()
            while (true) {
                when {
                    eat('+'.code) -> x += parseTerm()
                    eat('-'.code) -> x -= parseTerm()
                    else -> return x
                }
            }
        }

        fun parseTerm(): Double {
            var x = parseFactor()
            while (true) {
                when {
                    eat('*'.code) -> x *= parseFactor()
                    eat('/'.code) -> x /= parseFactor()
                    else -> return x
                }
            }
        }

        fun parseFactor(): Double {
            if (eat('+'.code)) return parseFactor()
            if (eat('-'.code)) return -parseFactor()

            var x: Double
            val startPos = this.pos
            if (eat('('.code)) {
                x = parseExpression()
                eat(')'.code)
            } else if (ch in '0'.code..'9'.code || ch == '.'.code) {
                while (ch in '0'.code..'9'.code || ch == '.'.code) nextChar()
                x = expression.substring(startPos, this.pos).toDouble()
            } else {
                throw RuntimeException("Unexpected: " + ch.toChar())
            }

            return x
        }
    }.parse()
}
