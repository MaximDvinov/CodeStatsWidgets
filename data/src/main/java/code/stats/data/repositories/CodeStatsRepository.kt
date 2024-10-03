package code.stats.data.repositories

import code.stats.data.models.UserStatData

interface CodeStatsRepository {
    suspend fun getStatByUser(username: String): Result<UserStatData>
}