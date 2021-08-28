package kek.enxy.plantwriter.di

import kek.enxy.data.readwrite.model.Dump
import kek.enxy.plantwriter.R
import kek.enxy.plantwriter.presentation.common.validators.CountValidator
import kek.enxy.plantwriter.presentation.common.validators.DateValidator
import kek.enxy.plantwriter.presentation.common.validators.RublesValidator
import kek.enxy.plantwriter.presentation.common.validators.ValidatorWrapper
import kek.enxy.plantwriter.presentation.main.MainViewModel
import kek.enxy.plantwriter.presentation.main.details.DetailsViewModel
import kek.enxy.plantwriter.presentation.main.details.edit.EditDumpType
import kek.enxy.plantwriter.presentation.main.details.edit.EditDumpViewModel
import kek.enxy.plantwriter.presentation.main.dumps.DumpsViewModel
import kek.enxy.plantwriter.presentation.main.scan.ScanViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val VALIDATOR_COUNT = "validator_count"
const val VALIDATOR_RUBLES = "validator_rubles"
const val VALIDATOR_DATE = "validator_date"

internal val mainModule = module {
    viewModel { MainViewModel() }
}

internal val scanModule = module {
    viewModel { ScanViewModel(androidApplication(), get()) }
}

internal val detailsModule = module {
    viewModel { (dump: Dump) -> DetailsViewModel(dump, get(), get()) }
    viewModel { (type: EditDumpType) ->
        val validatorWrapper: ValidatorWrapper = when (type) {
            is EditDumpType.Balance,
            is EditDumpType.LastUseAmount,
            is EditDumpType.LastPaymentAmount -> get(named(VALIDATOR_RUBLES))
            is EditDumpType.LastPaymentDate,
            is EditDumpType.LastUseDate -> get(named(VALIDATOR_DATE))
            is EditDumpType.GroundTravelTotal,
            is EditDumpType.UndergroundTravelTotal -> get(named(VALIDATOR_COUNT))
        }
        EditDumpViewModel(validatorWrapper, type)
    }
}

internal val dumpsModule = module {
    viewModel { DumpsViewModel() }
}

internal val validatorsModule = module {
    factory(named(VALIDATOR_COUNT)) {
        ValidatorWrapper(
            validator = CountValidator(),
            message = androidContext().getString(R.string.validator_count)
        )
    }
    factory(named(VALIDATOR_DATE)) {
        ValidatorWrapper(
            validator = DateValidator(),
            message = androidContext().getString(R.string.validator_date)
        )
    }
    factory(named(VALIDATOR_RUBLES)) {
        ValidatorWrapper(
            validator = RublesValidator(),
            message = androidContext().getString(R.string.validator_rubles)
        )
    }
}

internal val presentationModules = listOf(
    validatorsModule,
    mainModule,
    scanModule,
    detailsModule,
    dumpsModule,
)
