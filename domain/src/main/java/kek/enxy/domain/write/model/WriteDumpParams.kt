package kek.enxy.domain.write.model

import android.nfc.Tag
import kek.enxy.data.readwrite.model.Dump

data class WriteDumpParams(
    val tagId: String,
    val tag: Tag,
    val dump: Dump
)
