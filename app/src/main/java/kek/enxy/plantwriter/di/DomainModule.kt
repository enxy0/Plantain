package kek.enxy.plantwriter.di

import kek.enxy.domain.dumps.GetDumpsUseCase
import kek.enxy.domain.dumps.RemoveDumpUseCase
import kek.enxy.domain.dumps.SaveDumpUseCase
import kek.enxy.domain.dumps.implementation.GetDumpsUseCaseImpl
import kek.enxy.domain.dumps.implementation.RemoveDumpUseCaseImpl
import kek.enxy.domain.dumps.implementation.SaveDumpUseCaseImpl
import kek.enxy.domain.read.ReadDumpUseCase
import kek.enxy.domain.read.ReadDumpUseCaseImpl
import kek.enxy.domain.write.WriteDumpUseCase
import kek.enxy.domain.write.WriteDumpUseCaseImpl
import org.koin.dsl.module

internal val useCaseModule = module {
    factory<WriteDumpUseCase> { WriteDumpUseCaseImpl(get(), get()) }
    factory<ReadDumpUseCase> { ReadDumpUseCaseImpl(get()) }
    factory<SaveDumpUseCase> { SaveDumpUseCaseImpl(get()) }
    factory<RemoveDumpUseCase> { RemoveDumpUseCaseImpl(get()) }
    factory<GetDumpsUseCase> { GetDumpsUseCaseImpl(get()) }
}

internal val domainModules = listOf(useCaseModule)
