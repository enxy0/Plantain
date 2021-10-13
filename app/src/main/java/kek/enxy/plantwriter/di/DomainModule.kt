package kek.enxy.plantwriter.di

import kek.enxy.domain.dumps.GetDumpsUseCase
import kek.enxy.domain.dumps.GetLastDumpNumberUseCase
import kek.enxy.domain.dumps.RemoveDumpUseCase
import kek.enxy.domain.dumps.SaveDumpUseCase
import kek.enxy.domain.dumps.implementation.GetDumpsUseCaseImpl
import kek.enxy.domain.dumps.implementation.GetLastDumpNumberUseCaseImpl
import kek.enxy.domain.dumps.implementation.RemoveDumpUseCaseImpl
import kek.enxy.domain.dumps.implementation.SaveDumpUseCaseImpl
import kek.enxy.domain.history.GetFullHistoryUseCase
import kek.enxy.domain.history.implementation.GetFullFullHistoryUseCaseImpl
import kek.enxy.domain.read.ReadDumpUseCase
import kek.enxy.domain.read.ReadDumpUseCaseImpl
import kek.enxy.domain.settings.AppSettings
import kek.enxy.domain.settings.AppSettingsImpl
import kek.enxy.domain.write.WriteDumpUseCase
import kek.enxy.domain.write.WriteDumpUseCaseImpl
import org.koin.dsl.module

internal val useCaseModule = module {
    factory<WriteDumpUseCase> { WriteDumpUseCaseImpl(get(), get()) }
    factory<ReadDumpUseCase> { ReadDumpUseCaseImpl(get(), get()) }
    factory<SaveDumpUseCase> { SaveDumpUseCaseImpl(get()) }
    factory<RemoveDumpUseCase> { RemoveDumpUseCaseImpl(get()) }
    factory<GetDumpsUseCase> { GetDumpsUseCaseImpl(get()) }
    factory<GetLastDumpNumberUseCase> { GetLastDumpNumberUseCaseImpl(get()) }
    factory<GetFullHistoryUseCase> { GetFullFullHistoryUseCaseImpl(get()) }
}

internal val interactorModules = module {
    factory<AppSettings> { AppSettingsImpl(get()) }
}

internal val domainModules = listOf(useCaseModule, interactorModules)
