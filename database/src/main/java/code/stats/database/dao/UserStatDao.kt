package code.stats.database.dao

import code.stats.domain.models.UserStatData

interface UserStatDao {
    suspend fun save(userStat: UserStatData)
    suspend fun getStatByUser(username: String): UserStatData?
}