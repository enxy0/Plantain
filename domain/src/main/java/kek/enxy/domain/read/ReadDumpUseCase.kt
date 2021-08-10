package kek.enxy.domain.read

import kek.enxy.domain.FlowUseCase
import kek.enxy.data.readwrite.model.Dump

interface ReadDumpUseCase : FlowUseCase<ReadTagParams, Dump>
