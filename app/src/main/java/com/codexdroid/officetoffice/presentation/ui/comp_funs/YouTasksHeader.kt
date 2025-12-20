package com.codexdroid.officetoffice.presentation.ui.comp_funs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codexdroid.officetoffice.utils.AppConstants


@Composable
fun YourTasksHeader() {
    val density = LocalDensity.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
                .drawBehind {
                    drawLine(
                        color = Color.Black,
                        start = Offset(0f, size.height / 2),
                        end = Offset(size.width, size.height / 2),
                        strokeWidth = size.height
                    )
                }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Row {
            val dotRadius = with(density) { 2.dp.toPx() }
            repeat(3) {
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .drawBehind {
                            drawCircle(
                                Color.Black,
                                radius = dotRadius,
                                center = Offset(size.width / 2, size.height / 2)
                            )
                        }
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Your Tasks",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = AppConstants.getUbuntuFont(true),
                color = Color.Black
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Row {
            val dotRadius = with(density) { 2.dp.toPx() }
            repeat(3) {
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .drawBehind {
                            drawCircle(
                                Color.Black,
                                radius = dotRadius,
                                center = Offset(size.width / 2, size.height / 2)
                            )
                        }
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
                .drawBehind {
                    drawLine(
                        color = Color.Black,
                        start = Offset(0f, size.height / 2),
                        end = Offset(size.width, size.height / 2),
                        strokeWidth = size.height
                    )
                }
        )
    }
}

@Preview
@Composable
fun YouTasksHeaderPreview() {
    YourTasksHeader()
}
