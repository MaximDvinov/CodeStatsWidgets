package code.stats.network

import code.stats.network.models.UserStat
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class CodeStatsApi(private val httpClient: HttpClient) {
    suspend fun getStatByUser(username: String): Result<UserStat> {
        return try {
            val response: UserStat =
                httpClient.get("https://codestats.net/api/users/$username").body()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}