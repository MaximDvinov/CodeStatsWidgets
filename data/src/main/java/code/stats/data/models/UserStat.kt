package code.stats.data.models

import code.stats.network.models.UserStat
import kotlinx.datetime.LocalDate

data class UserStatData(
    val dates: Map<LocalDate, Int>? = null,
    val languages: Map<String, StatItemData>? = null,
    val machines: Map<String, StatItemData>? = null,
    val newXp: Int? = null,
    val totalXp: Int? = null,
    val user: String? = null,
)

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
