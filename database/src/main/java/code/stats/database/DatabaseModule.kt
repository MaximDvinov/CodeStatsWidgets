package code.stats.database

import code.stats.database.dao.UserStatDao
import code.stats.database.dao.UserStatDaoImpl
import code.stats.database.entity.StatItemDbo
import code.stats.database.entity.UserStatDbo
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val databaseModule = module {
    single {
        val config =
            RealmConfiguration.create(schema = setOf(UserStatDbo::class, StatItemDbo::class))
        Realm.open(config)
    }

    singleOf(::UserStatDaoImpl) bind UserStatDao::class
}