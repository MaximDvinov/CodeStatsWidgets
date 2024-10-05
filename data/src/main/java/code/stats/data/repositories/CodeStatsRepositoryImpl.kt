package code.stats.data.repositories

import code.stats.data.models.UserStatData
import code.stats.data.models.toData
import code.stats.network.CodeStatsApi

class CodeStatsRepositoryImpl(private val api: CodeStatsApi) : CodeStatsRepository {
    override suspend fun getStatByUser(username: String): Result<UserStatData> {
        return api.getStatByUser(username)
            .map { it.toData() }
    }
}