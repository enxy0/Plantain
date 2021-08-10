package kek.enxy.domain.dumps

import kek.enxy.data.readwrite.model.Dump
import kek.enxy.domain.FlowUseCase

interface SaveDumpUseCase : FlowUseCase<Dump, Unit>
