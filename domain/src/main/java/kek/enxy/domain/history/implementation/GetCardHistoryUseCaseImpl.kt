package kek.enxy.domain.history.implementation

import kek.enxy.data.history.HistoryDataSource
import kek.enxy.data.history.model.History
import kek.enxy.domain.history.GetCardHistoryUseCase
import kek.enxy.domain.history.model.CardHistory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCardHistoryUseCaseImpl(
    private val historyDataSource: HistoryDataSource
) : GetCardHistoryUseCase {
    override fun execute(parameter: String): Flow<Result<CardHistory>> =
        historyDataSource
            .getHistoryByUidFlow(parameter)
            .map { list -> Result.success(list.toCardHistory(parameter)) }

    private fun List<History>.toCardHistory(uid: String) =
        this.groupBy { history -> history.dump.uid }
            .map { (uid, histories) -> CardHistory(uid, histories) }
            .firstOrNull() ?: CardHistory(uid, emptyList())
}
