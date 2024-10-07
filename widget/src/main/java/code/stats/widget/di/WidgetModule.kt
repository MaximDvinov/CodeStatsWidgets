package code.stats.widget.di

import code.stats.data.dataModule
import code.stats.widget.calendar_widget.GetDatesWithXP
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val widgetModule = module {
    includes(dataModule)

    singleOf(::GetDatesWithXP)
}