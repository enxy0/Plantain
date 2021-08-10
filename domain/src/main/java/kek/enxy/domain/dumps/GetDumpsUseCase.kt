package kek.enxy.domain.dumps

import kek.enxy.data.readwrite.model.Dump
import kek.enxy.domain.FlowUseCase

interface GetDumpsUseCase : FlowUseCase<Unit, List<Dump>>
