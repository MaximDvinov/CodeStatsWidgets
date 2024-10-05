package code.stats.network.models


import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserStat(
    @SerialName("dates")
    val dates: Map<LocalDate, Int>? = null,
    @SerialName("languages")
    val languages: Map<String, StatItem>? = null,
    @SerialName("machines")
    val machines: Map<String, StatItem>? = null,
    @SerialName("new_xp")
    val newXp: Int? = null,
    @SerialName("total_xp")
    val totalXp: Int? = null,
    @SerialName("user")
    val user: String? = null,
)

