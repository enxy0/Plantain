package kek.enxy.domain.dumps.implementation

import kek.enxy.data.dumps.DumpsDataSource
import kek.enxy.data.readwrite.model.Dump
import kek.enxy.domain.dumps.SaveDumpUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveDumpUseCaseImpl(
    private val dataSource: DumpsDataSource
) : SaveDumpUseCase {
    override fun execute(parameter: Dump): Flow<Result<Unit>> = flow {
        dataSource.saveDump(parameter)
        emit(Result.success(Unit))
    }
}
