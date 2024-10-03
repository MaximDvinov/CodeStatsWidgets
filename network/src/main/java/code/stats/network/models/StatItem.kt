package code.stats.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatItem(
    @SerialName("new_xps")
    val newXps: Int? = null,
    @SerialName("xps")
    val xps: Int? = null,
)