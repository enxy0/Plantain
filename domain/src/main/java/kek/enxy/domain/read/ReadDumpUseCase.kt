package kek.enxy.domain.read

import android.nfc.Tag
import kek.enxy.domain.FlowUseCase
import kek.enxy.data.readwrite.model.Dump

interface ReadDumpUseCase : FlowUseCase<Tag, Dump>
