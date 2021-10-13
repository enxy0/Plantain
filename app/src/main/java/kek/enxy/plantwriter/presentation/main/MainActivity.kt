package kek.enxy.plantwriter.presentation.main

import android.content.Intent
import android.os.Bundle
import kek.enxy.plantwriter.databinding.ActivityMainBinding
import kek.enxy.plantwriter.presentation.common.base.BaseActivity
import kotlinx.coroutines.flow.StateFlow
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity(), ScanContract {
    private val viewModel by viewModel<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    override val resolveIntentFlow: StateFlow<CardState>
        get() = viewModel.resolveIntentFlow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MifareClassicListener.listenIn(this)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }
        viewModel.createIntentEvent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        viewModel.createIntentEvent(intent)
    }
}
