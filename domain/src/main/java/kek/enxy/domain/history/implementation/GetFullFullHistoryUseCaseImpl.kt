package kek.enxy.domain.history.implementation

import kek.enxy.data.history.HistoryDataSource
import kek.enxy.data.history.model.History
import kek.enxy.domain.history.GetFullHistoryUseCase
import kek.enxy.domain.history.model.CardHistory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFullFullHistoryUseCaseImpl(
    private val historyDataSource: HistoryDataSource
) : GetFullHistoryUseCase {
    override fun execute(parameter: Unit): Flow<Result<List<CardHistory>>> =
        historyDataSource.getHistoryFlow().map { Result.success(groupByUid(it)) }

    private fun groupByUid(histories: List<History>) = histories
        .groupBy { it.dump.uid }
        .map { (uid, histories) -> CardHistory(uid, histories) }
}
