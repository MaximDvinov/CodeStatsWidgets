package code.stats.data

import code.stats.data.repositories.CodeStatsRepository
import code.stats.data.repositories.CodeStatsRepositoryImpl
import code.stats.network.networkModule
import org.koin.dsl.module

val dataModule = module {
    includes(networkModule)
    single<CodeStatsRepository> { CodeStatsRepositoryImpl(get()) }
}