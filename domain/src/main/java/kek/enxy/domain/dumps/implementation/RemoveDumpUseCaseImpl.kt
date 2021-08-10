package kek.enxy.domain.dumps.implementation

import kek.enxy.data.dumps.DumpsDataSource
import kek.enxy.data.readwrite.model.Dump
import kek.enxy.domain.dumps.RemoveDumpUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoveDumpUseCaseImpl(
    private val dataSource: DumpsDataSource
) : RemoveDumpUseCase {
    override fun execute(parameter: Dump): Flow<Result<Unit>> = flow {
        dataSource.removeDump(parameter)
    }
}
