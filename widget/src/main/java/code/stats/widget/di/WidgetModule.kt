package code.stats.widget.di

import code.stats.data.dataModule
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val widgetModule = module {
    includes(dataModule)
}