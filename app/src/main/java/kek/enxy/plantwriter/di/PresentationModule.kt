package kek.enxy.plantwriter.di

import kek.enxy.plantwriter.presentation.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val presentationModule = module {
    viewModel { MainViewModel(get(), get()) }
}
