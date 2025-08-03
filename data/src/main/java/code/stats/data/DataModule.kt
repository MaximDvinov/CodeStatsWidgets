package code.stats.data

import code.stats.domain.repositories.CodeStatsRepository
import code.stats.data.repositories.CodeStatsRepositoryImpl
import code.stats.database.databaseModule
import code.stats.domain.usecases.GetDatesWithXP
import code.stats.network.networkModule
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    includes(networkModule, databaseModule)
    singleOf(::CodeStatsRepositoryImpl) bind CodeStatsRepository::class
    singleOf(::GetDatesWithXP)
}