package kek.enxy.domain.history

import kek.enxy.domain.FlowUseCase
import kek.enxy.domain.history.model.CardProfit

interface GetCardProfitUseCase : FlowUseCase<String, CardProfit>
