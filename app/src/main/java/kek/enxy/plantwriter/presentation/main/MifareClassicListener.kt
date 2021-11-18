package kek.enxy.plantwriter.presentation.main

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.tech.MifareClassic
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

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
            object : DefaultLifecycleObserver {
                override fun onResume(owner: LifecycleOwner) {
                    super.onResume(owner)
                    nfcAdapter?.enableForegroundDispatch(
                        activity,
                        createPendingIntent(activity),
                        nfcFilters,
                        nfcTechLists
                    )
                }

                override fun onPause(owner: LifecycleOwner) {
                    super.onPause(owner)
                    nfcAdapter?.disableForegroundDispatch(activity)
                }
            }
        )
    }

    private fun createPendingIntent(activity: AppCompatActivity): PendingIntent {
        val intent = Intent(activity, activity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntentFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        return PendingIntent.getActivity(activity, 0, intent, pendingIntentFlags)
    }
}
