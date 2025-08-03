package code.stats.data.mappers

import code.stats.domain.models.StatItemData
import code.stats.domain.models.UserStatData
import code.stats.network.models.StatItem
import code.stats.network.models.UserStat

fun UserStat.toData(): UserStatData {
    return UserStatData(
        dates = dates,
        languages = languages?.mapValues { it.value.toData() },
        machines = machines?.mapValues { it.value.toData() },
        newXp = newXp,
        totalXp = totalXp,
        user = user
    )
}

fun StatItem.toData() = StatItemData(
    newXps = newXps,
    xps = xps,
)