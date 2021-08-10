package kek.enxy.domain.dumps

import kek.enxy.data.readwrite.model.Dump
import kek.enxy.domain.FlowUseCase

interface RemoveDumpUseCase : FlowUseCase<Dump, Unit>
