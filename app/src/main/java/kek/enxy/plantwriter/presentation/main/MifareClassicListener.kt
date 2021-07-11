package kek.enxy.plantwriter.presentation.main

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.tech.MifareClassic
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class MifareClassicListener private constructor(activity: AppCompatActivity) {

    companion object {
        fun listenIn(activity: AppCompatActivity) = MifareClassicListener(activity)
    }

    private val nfcTechLists by lazy { arrayOf(arrayOf(MifareClassic::class.java.name)) }
    private val nfcFilters by lazy {
        val filter = IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED).apply { addDataType("*/*") }
        arrayOf(filter)
    }

    init {
        val nfcAdapter: NfcAdapter? = NfcAdapter.getDefaultAdapter(activity)
        activity.lifecycle.addObserver(
            object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
                fun enableForegroundDispatch() {
                    nfcAdapter?.enableForegroundDispatch(
                        activity,
                        createPendingIntent(activity),
                        nfcFilters,
                        nfcTechLists
                    )
                }

                @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
                fun disableForegroundDispatch() {
                    nfcAdapter?.disableForegroundDispatch(activity)
                }
            }
        )
    }

    // WTF Android SDK?? There should be a mutable flag (by official docs), but there isn't...
    // So ignoring it until it appears
    @SuppressLint("UnspecifiedImmutableFlag")
    private fun createPendingIntent(activity: AppCompatActivity): PendingIntent {
        val intent = Intent(activity, activity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntentFlags = PendingIntent.FLAG_UPDATE_CURRENT
        return PendingIntent.getActivity(activity, 0, intent, pendingIntentFlags)
    }
}
