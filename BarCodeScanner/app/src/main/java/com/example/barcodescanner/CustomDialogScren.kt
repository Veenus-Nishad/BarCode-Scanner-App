package com.example.barcodescanner


import android.widget.Toast
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Minimize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun CustomDialogScreen(
    onDismissRequest: () -> Unit,
    onConfirmationRequest: () -> Unit,
    productId: String,

) {
    var productQuantity by remember { mutableStateOf(1) }

    val density = LocalDensity.current
    var offsetX by remember {
        mutableStateOf(0f)
    }
    var offsetY by remember {
        mutableStateOf(0f)
    }

    val context = LocalContext.current
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Box(
            modifier = Modifier.fillMaxSize()
                .offset(
                    (offsetX/density.density).dp,
                    (offsetY/density.density).dp
                )
                .clip(RoundedCornerShape(10.dp))
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .height(200.dp)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Product Id")
                        Text(text = "Quantity")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = productId)
                        Row {
                            TextButton(onClick = {
                                if (productQuantity > 1) {
                                    productQuantity--
                                }
                            }) { Icon(imageVector = Icons.Default.Minimize, contentDescription = null) }
                        }
                        TextButton(onClick = {}) {
                            Text(text = "$productQuantity")
                        }
                        TextButton(onClick = {
                            productQuantity++
                        }) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = null)
                        }
                    }
                    Button(onClick = {
                        Toast.makeText(context, "Product added to cart", Toast.LENGTH_SHORT).show()
                        onConfirmationRequest()

                    }) { Text(text="Add to Cart")
                    }
                }
            }
        }

    }
}
