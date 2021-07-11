package kek.enxy.plantwriter.di

object KoinModules {
    fun all() = listOf(
        dataModule,
        domainModule
    ) + presentationModules
}
