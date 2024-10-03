package code.stats.data.models

import code.stats.network.models.UserStat

data class UserStatData(
    val dates: Map<String, Int>? = null,
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
