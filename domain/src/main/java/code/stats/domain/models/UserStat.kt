package code.stats.domain.models

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

data class UserStatData(
    val lastChange: LocalDateTime? = null,
    val dates: Map<LocalDate, Int>? = null,
    val languages: Map<String, StatItemData>? = null,
    val machines: Map<String, StatItemData>? = null,
    val newXp: Int? = null,
    val totalXp: Int? = null,
    val user: String? = null,
)

