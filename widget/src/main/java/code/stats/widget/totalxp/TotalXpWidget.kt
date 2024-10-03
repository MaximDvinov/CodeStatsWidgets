package code.stats.widget.totalxp

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.LocalSize
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.components.Scaffold
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.updateAll
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import code.stats.data.repositories.CodeStatsRepository
import code.stats.widget.utils.formatter
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TotalXpWidget : GlanceAppWidget(), KoinComponent {
    override val sizeMode: SizeMode = SizeMode.Exact

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val repository: CodeStatsRepository by inject()

        provideContent {
            var totalXp by remember {
                mutableStateOf("Loading...")
            }

            var newXp by remember {
                mutableStateOf("+0")
            }

            LaunchedEffect(Unit) {
                repository.getStatByUser("MaximDvinov").onSuccess {
                    totalXp = it.totalXp?.formatter() ?: "Not data"
                    newXp = "+${it.newXp?.formatter() ?: "0"}"
                }
                    .onFailure {
                        totalXp = "Error"
                        Log.e("TotalXpWidget", "getStat", it)
                    }
            }

            GlanceTheme {
                Scaffold(
                    modifier = GlanceModifier
                        .background(GlanceTheme.colors.background)
                        .appWidgetBackground()
                        .cornerRadius(28.dp),
                    horizontalPadding = 0.dp
                ) {
                    TotalXpContent(totalXp, newXp)
                }

            }
        }
    }

    @Composable
    private fun TotalXpContent(totalXp: String, newXp: String) {
        val size = LocalSize.current

        val fontSize = when {
            size.width < 100.dp -> 12.sp
            size.width < 150.dp -> 20.sp
            else -> 30.sp
        }

        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (size.height > 130.dp) {
                Text(
                    text = "total XP",
                    style = TextStyle(
                        color = GlanceTheme.colors.tertiary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

            Text(
                text = totalXp,
                style = TextStyle(
                    color = GlanceTheme.colors.onBackground,
                    fontSize = fontSize,
                    fontWeight = FontWeight.Medium
                ),
            )

            Box(
                modifier = GlanceModifier
                    .background(GlanceTheme.colors.primary)
                    .padding(horizontal = 16.dp, vertical = 2.dp)
                    .cornerRadius(16.dp)
            ) {
                Text(
                    text = newXp,
                    style = TextStyle(
                        color = GlanceTheme.colors.onPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
    }
}
