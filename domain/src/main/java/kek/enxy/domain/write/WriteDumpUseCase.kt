package kek.enxy.domain.write

import android.nfc.Tag
import kek.enxy.domain.FlowUseCase

interface WriteDumpUseCase : FlowUseCase<Tag, Unit>
