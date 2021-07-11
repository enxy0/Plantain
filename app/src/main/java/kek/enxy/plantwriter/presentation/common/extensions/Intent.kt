package kek.enxy.plantwriter.presentation.common.extensions

import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag

val Intent.nfcTag: Tag?
    get() = this.getParcelableExtra(NfcAdapter.EXTRA_TAG)

val Intent.nfcTagId: String?
    get() = this.getByteArrayExtra(NfcAdapter.EXTRA_ID)?.toHexString()