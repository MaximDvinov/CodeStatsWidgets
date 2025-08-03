package code.stats.database.dao

import code.stats.database.entity.StatItemDbo
import code.stats.database.entity.UserStatDbo
import code.stats.domain.models.StatItemData
import code.stats.domain.models.UserStatData
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmDictionaryOf
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime

@OptIn(FormatStringsInDatetimeFormats::class)
val dateFormat = LocalDate.Format {
    byUnicodePattern("yyyy-MM-dd")
}

@OptIn(FormatStringsInDatetimeFormats::class)
val dateTimeFormat = LocalDateTime.Format {
    byUnicodePattern("yyyy-MM-dd HH:mm:ss")
}

class UserStatDaoImpl(
    private val database: Realm,
) : UserStatDao {
    override suspend fun save(userStat: UserStatData) {
        database.write {
            val newUserStat = UserStatDbo().apply {
                user = userStat.user ?: ""
                totalXp = userStat.totalXp ?: 0
                newXp = userStat.newXp ?: 0
                machines = realmDictionaryOf(
                    userStat.machines?.mapValues {
                        StatItemDbo().apply {
                            newXps = it.value.newXps; xps = it.value.xps
                        }
                    }?.toList() ?: emptyList()
                )
                languages = realmDictionaryOf(
                    userStat.languages?.mapValues {
                        StatItemDbo().apply {
                            newXps = it.value.newXps; xps = it.value.xps
                        }
                    }?.toList() ?: emptyList()
                )
                dates = realmDictionaryOf(
                    userStat.dates?.mapKeys { it.key.format(dateFormat) }?.toList() ?: emptyList()
                )
                lastChange =
                    Clock.System.now()
                        .toLocalDateTime(TimeZone.currentSystemDefault())
                        .format(dateTimeFormat)
            }

            copyToRealm(newUserStat, updatePolicy = UpdatePolicy.ALL)
        }
    }

    override suspend fun getStatByUser(username: String): UserStatData? {
        return database.query<UserStatDbo>("user = $0", username).first().find()?.toData()
    }
}

fun UserStatDbo.toData(): UserStatData {
    return UserStatData(
        dates = dates.mapKeys { LocalDate.parse(it.key, dateFormat) },
        languages = languages.mapValues { StatItemData(it.value?.newXps, it.value?.xps) },
        machines = machines.mapValues { StatItemData(it.value?.newXps, it.value?.xps) },
        newXp = newXp,
        totalXp = totalXp,
        user = user,
        lastChange = LocalDateTime.parse(lastChange, dateTimeFormat)
    )
}