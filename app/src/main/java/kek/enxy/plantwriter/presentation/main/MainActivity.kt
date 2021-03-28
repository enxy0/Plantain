package kek.enxy.plantwriter.presentation.main

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.MifareClassic
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnStart
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kek.enxy.plantwriter.R
import kek.enxy.plantwriter.databinding.ActivityMainBinding
import kek.enxy.plantwriter.presentation.common.extensions.logText
import kek.enxy.plantwriter.presentation.common.extensions.toHexString
import kek.enxy.plantwriter.presentation.settings.SettingsActivity
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    private val nfcAdapter by lazy { NfcAdapter.getDefaultAdapter(this) }
    private val nfcTechLists by lazy { arrayOf(arrayOf(MifareClassic::class.java.name)) }
    private val nfcFilters by lazy {
        val filter = IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED).apply { addDataType("*/*") }
        arrayOf(filter)
    }
    private var nfcIntent: Intent? = null
    private val nfcTag: Tag?
        get() = nfcIntent?.getParcelableExtra(NfcAdapter.EXTRA_TAG)
    private val nfcTagId: String
        get() = nfcIntent?.getByteArrayExtra(NfcAdapter.EXTRA_ID)?.toHexString().orEmpty()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }
        resolveIntent(intent)
        initView()
        setObservers()
    }

    private fun initView() {
        binding.btnWrite.setOnClickListener {
            nfcTag?.let { tag ->
                viewModel.writeDumpData(tag)
            }
        }
        binding.toolbar.onEndBtnClicked {
            SettingsActivity.start(this)
        }
    }

    private fun setObservers() {
        viewModel.writeResultFlow
            .flowWithLifecycle(lifecycle)
            .onEach { event ->
                event.getContentIfNotHandled()?.let { isSuccessful ->
                    val text = if (isSuccessful) {
                        getString(R.string.main_write_success)
                    } else {
                        getString(R.string.main_write_failure)
                    }
                    binding.textLog.logText(text)
                }
            }
            .launchIn(lifecycleScope)

        viewModel.dumpStateFlow
            .flowWithLifecycle(lifecycle)
            .onEach { dumpState ->
                when (dumpState) {
                    is DumpState.Content -> {
                        binding.textLog.logText(getString(R.string.main_read_success))
                        binding.tagDetails.setDetails(nfcTagId, dumpState.dump)
                    }
                    is DumpState.Loading -> {
                        binding.tagDetails.setLoading()
                    }
                    is DumpState.Error -> {
                        binding.textLog.logText(getString(R.string.main_read_failure))
                        binding.tagDetails.setError(dumpState.exception)
                    }
                    else -> Unit
                }
            }
            .launchIn(lifecycleScope)
    }

    // WTF Android SDK?? There should be a mutable flag (by official docs), but there isn't...
    // So ignoring it until it appears
    @SuppressLint("UnspecifiedImmutableFlag")
    private fun createPendingIntent(): PendingIntent {
        val intent = Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntentFlags = PendingIntent.FLAG_UPDATE_CURRENT
        return PendingIntent.getActivity(this, 0, intent, pendingIntentFlags)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            resolveIntent(intent)
        }
    }

    private fun resolveIntent(intent: Intent) {
        if (intent.action == NfcAdapter.ACTION_TECH_DISCOVERED) {
            nfcIntent = intent
            binding.textLog.logText(getString(R.string.main_tag_found))
            nfcTag?.let { tag -> viewModel.readDumpData(tag) }
            showContentWithAnimation()
        }
    }

    private fun showContentWithAnimation() = with(binding) {
        if (textLog.isGone && btnWrite.isGone && tagDetails.isGone) {
            val logAnimator = ObjectAnimator.ofFloat(textLog, View.ALPHA, 0.0f, 1.0f)
            val buttonAnimator = ObjectAnimator.ofFloat(btnWrite, View.ALPHA, 0.0f, 1.0f)
            val tagDetailsAnimator = ObjectAnimator.ofFloat(tagDetails, View.ALPHA, 0.0f, 1.0f)
            val animatorSet = AnimatorSet().apply {
                duration = 300L
                playTogether(logAnimator, buttonAnimator, tagDetailsAnimator)
                doOnStart {
                    textLog.isVisible = true
                    btnWrite.isVisible = true
                    tagDetails.isVisible = true
                }
            }
            animatorSet.start()
        }
    }

    override fun onResume() {
        super.onResume()
        nfcAdapter.enableForegroundDispatch(this, createPendingIntent(), nfcFilters, nfcTechLists)
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter.disableForegroundDispatch(this)
    }
}
