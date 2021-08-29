package kek.enxy.domain.dumps.implementation

import kek.enxy.data.dumps.DumpsDataSource
import kek.enxy.domain.dumps.GetLastDumpNumberUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetLastDumpNumberUseCaseImpl(
    private val dataSource: DumpsDataSource
) : GetLastDumpNumberUseCase {
    override fun execute(parameter: Unit): Flow<Result<Int>> = flow {
        emit(Result.success(dataSource.getLastDumpId()))
    }
}