package kek.enxy.domain.history.implementation

import kek.enxy.data.history.HistoryDataSource
import kek.enxy.data.history.model.HistoryAction
import kek.enxy.data.readwrite.model.AppDate
import kek.enxy.data.readwrite.model.Rubles
import kek.enxy.domain.history.GetCardProfitUseCase
import kek.enxy.domain.history.model.CardProfit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCardProfitUseCaseImpl(
    private val historyDataSource: HistoryDataSource
) : GetCardProfitUseCase {
    override fun execute(parameter: String): Flow<Result<CardProfit>> =
        historyDataSource
            .getHistoryByUidFlow(parameter)
            .map { list ->
                // Prepare values to calculate savedTotal
                var lastWrite = Rubles(-1)
                var lastRead = Rubles(-1)
                var savedTotal = Rubles(0)
                var nextSavedTotal = Rubles(-1)

                // Write/Read
                var writesTotal = 0
                var readsTotal = 0

                // Dates
                val first = list.first()
                var toDate: AppDate = first.timestamp
                var fromDate: AppDate = first.timestamp
                list.forEach { history ->
                    val isNextWrite = if (history.action == HistoryAction.READ) {
                        readsTotal++
                        lastRead = history.dump.balance
                        false
                    } else {
                        writesTotal++
                        lastWrite = history.dump.balance
                        true
                    }
                    if (lastWrite.isNotEmpty && lastRead.isNotEmpty) {
                        if (isNextWrite && nextSavedTotal.isNotEmpty) {
                            savedTotal += nextSavedTotal
                            nextSavedTotal = Rubles(-1)
                        }
                        if (lastWrite > lastRead) {
                            nextSavedTotal = lastWrite - lastRead
                        }
                    }
                    if (nextSavedTotal.isNotEmpty) {
                        savedTotal += nextSavedTotal
                    }

                    when {
                        history.timestamp < fromDate -> fromDate = history.timestamp
                        history.timestamp < toDate -> toDate = history.timestamp
                    }
                }
                Result.success(CardProfit(savedTotal, writesTotal, readsTotal, toDate, fromDate))
            }
}
