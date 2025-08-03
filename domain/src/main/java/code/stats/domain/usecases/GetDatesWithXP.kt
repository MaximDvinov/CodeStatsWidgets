package code.stats.domain.usecases

import android.util.Log
import code.stats.domain.repositories.CodeStatsRepository
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.plus

class GetDatesWithXP(private val repository: CodeStatsRepository) {
    suspend operator fun invoke(username: String): Result<List<Pair<LocalDate, Int>>> {
        return repository.getStatByUser(username)
            .map { data ->
                val dates = data.dates ?: return@map emptyList()

                val minDate = dates.minOf { it.key }
                val maxDate = dates.maxOf { it.key }.run {
                    plus(7 - this.dayOfWeek.isoDayNumber.toLong(), DateTimeUnit.DAY)
                }

                (minDate..maxDate).map { date ->
                    Pair(date, dates[date] ?: 0)
                }.reversed().apply {
                    Log.i("GetDatesWithXP", "$this")
                }
            }
    }

    operator fun LocalDate.rangeTo(other: LocalDate) =
        generateSequence(this) { it.plus(1, DateTimeUnit.DAY) }.takeWhile { it <= other }.toList()
}