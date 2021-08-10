package kek.enxy.domain.dumps.implementation

import kek.enxy.data.dumps.DumpsDataSource
import kek.enxy.data.readwrite.model.Dump
import kek.enxy.domain.dumps.GetDumpsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetDumpsUseCaseImpl(
    private val dataSource: DumpsDataSource
) : GetDumpsUseCase {
    override fun execute(parameter: Unit): Flow<Result<List<Dump>>> =
        dataSource.getDumpsFlow().map { dumps -> Result.success(dumps) }
}
