package code.stats.widget.totalxp

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver

class TotalXpWidgetReciver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = TotalXpWidget()
}