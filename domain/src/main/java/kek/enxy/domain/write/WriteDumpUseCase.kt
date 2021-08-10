package kek.enxy.domain.write

import kek.enxy.domain.FlowUseCase
import kek.enxy.domain.write.model.WriteDumpParams

interface WriteDumpUseCase : FlowUseCase<WriteDumpParams, Unit>
