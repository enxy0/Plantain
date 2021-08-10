package kek.enxy.domain.read

import android.nfc.Tag

data class ReadTagParams(
    val uid: String,
    val tag: Tag
)
