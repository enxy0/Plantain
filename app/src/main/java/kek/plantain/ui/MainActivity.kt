package kek.plantain.ui

import android.app.PendingIntent
import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.ui.core.setContent
import kek.plantain.ui.home.HomeScreen
import kek.plantain.ui.theme.PlantainTheme

class MainActivity : AppCompatActivity() {
    private lateinit var nfcAdapter: NfcAdapter
    private val pendingIntent: PendingIntent by lazy {
        PendingIntent.getActivity(
            this, 0, Intent(this, ReadTagActivity::class.java), 0
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        setContent {
            PlantainTheme {
                HomeScreen()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (nfcAdapter.isEnabled) {
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null)
        }
    }
}