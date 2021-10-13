package kek.enxy.domain.history

import kek.enxy.domain.FlowUseCase
import kek.enxy.domain.history.model.CardHistory

interface GetFullHistoryUseCase : FlowUseCase<Unit, List<CardHistory>>
