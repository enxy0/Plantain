package kek.enxy.plantwriter.di

import kek.enxy.data.readwrite.model.Dump
import kek.enxy.plantwriter.presentation.main.MainViewModel
import kek.enxy.plantwriter.presentation.main.details.DetailsViewModel
import kek.enxy.plantwriter.presentation.main.scan.ScanViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val mainModule = module {
    viewModel { MainViewModel() }
}

internal val scanModule = module {
    viewModel { ScanViewModel(androidApplication(), get(), get()) }
}

internal val detailsModule = module {
    viewModel { (dump: Dump) -> DetailsViewModel(dump) }
}

internal val presentationModules = listOf(
    mainModule,
    scanModule,
    detailsModule
)
