package code.stats.data.repositories

import android.util.Log
import code.stats.data.mappers.toData
import code.stats.database.dao.UserStatDao
import code.stats.domain.models.UserStatData
import code.stats.domain.repositories.CodeStatsRepository
import code.stats.network.CodeStatsApi
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.time.Duration.Companion.minutes

class CodeStatsRepositoryImpl(
    private val api: CodeStatsApi,
    private val dao: UserStatDao,
) : CodeStatsRepository {
    override suspend fun getStatByUser(
        username: String,
        forceUpdate: Boolean,
    ): Result<UserStatData> {
        val stat = dao.getStatByUser(username)

        val duration =
            stat?.lastChange?.toInstant(TimeZone.currentSystemDefault())
                ?.let { Clock.System.now().minus(it) }

        if (duration == null || duration >= 30.minutes || forceUpdate) {
            val networkResult = api.getStatByUser(username).map { it.toData() }

            if (networkResult.isFailure) {
                Log.i("CodeStatsRepository", "from cache")
                return if (stat != null) Result.success(stat) else networkResult
            } else {
                Log.i("CodeStatsRepository", "from network")
                networkResult.getOrNull()?.let { dao.save(it) }
                return networkResult
            }
        } else {
            Log.i("CodeStatsRepository", "from cache")
            return Result.success(stat)
        }
    }
}