package code.stats.widget.calendar_widget


import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.LocalContext
import androidx.glance.LocalSize
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxHeight
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import code.stats.widget.utils.formatter
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.roundToInt

@Composable
fun CalendarWidgetContent(days: List<Int>) {
    val context = LocalContext.current
    val size = LocalSize.current
    val padding by remember(days, size) {
        derivedStateOf { if (size.height < 100.dp) 6.dp else 12.dp }
    }

    val daySize by remember(days, size) {
        derivedStateOf { (size.height - (padding * 2) - 2.dp * 6) / 7 }
    }
    val count by remember(days, size) {
        derivedStateOf { floor((size.width - (padding * 2)).value / daySize.value) * 7 }
    }

    val daysTake by remember(days, size) {
        derivedStateOf { days.take(count.toInt()) }
    }
    val daysChunked by remember(days, size) {
        derivedStateOf {
            val maxValue = daysTake.maxOf { it }
            daysTake.reversed().chunked(7).map { list -> list.map { it / maxValue.toFloat() } }
        }
    }

    if (daysTake.isEmpty()) return

    Column(
        modifier = GlanceModifier.padding(padding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = GlanceModifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically
        ) {
            daysChunked.forEachIndexed { index, chunk ->
                Column(
                    modifier = GlanceModifier
                        .fillMaxHeight()
                        .defaultWeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    chunk.forEachIndexed { indexRow, it ->
                        Box(
                            modifier = GlanceModifier.defaultWeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = GlanceModifier
                                    .cornerRadius(daySize)
                                    .size(daySize)
                                    .background(
                                        if (it != 0f) GlanceTheme.colors.primary.getColor(context)
                                            .copy(alpha = (it)) else Color.Gray.copy(alpha = 0.05f)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
//                                Text(
//                                    text = it.formatter(),
//                                    style = TextStyle(
//                                        fontSize = 10.sp,
//                                        color = GlanceTheme.colors.onPrimary
//                                    )
//                                )
                            }
                        }

                    }
                }
            }
        }
    }
}
