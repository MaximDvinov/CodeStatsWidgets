package code.stats.widget.calendar_widget

import android.content.Context
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.components.Scaffold
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import code.stats.analytics.AppLogger
import code.stats.data.repositories.CodeStatsRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CalendarWidget() : GlanceAppWidget(), KoinComponent {
    override val sizeMode: SizeMode = SizeMode.Exact
    private val repository: CodeStatsRepository by inject()
    private val getDatesWithXP: GetDatesWithXP by inject()


    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val sharedPreferences = context.getSharedPreferences("Prefs", Context.MODE_PRIVATE)

        provideContent {
            var days: List<Int> by remember { mutableStateOf(emptyList()) }

            LaunchedEffect(Unit) {
                val name = sharedPreferences.getString("username", "")
                if (name != null) {
                    launch {
                        getDatesWithXP(name).onSuccess { dates ->
                            days = dates.map { it.second }.take(70)
                            Log.i("get stat", "$days")

                        }.onFailure {
                            Log.e("CalendarWidget", "getStat", it)
                            AppLogger.log("get stat error: $it")
                        }
                    }
                }
            }

            GlanceTheme {
                Scaffold(
                    modifier = GlanceModifier.background(GlanceTheme.colors.background)
                        .appWidgetBackground().cornerRadius(28.dp), horizontalPadding = 0.dp
                ) {
                    CalendarWidgetContent(days)
                }

            }

        }
    }
}