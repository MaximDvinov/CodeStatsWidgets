package code.stats.domain.repositories

import code.stats.domain.models.UserStatData

interface CodeStatsRepository {
    suspend fun getStatByUser(username: String, forceUpdate: Boolean = false): Result<UserStatData>
}