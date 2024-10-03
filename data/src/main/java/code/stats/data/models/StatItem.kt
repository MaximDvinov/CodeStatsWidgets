package code.stats.data.models

import code.stats.network.models.StatItem

data class StatItemData(
    val newXps: Int? = null,
    val xps: Int? = null,
)


fun StatItem.toData() = StatItemData(
    newXps = newXps,
    xps = xps,
)