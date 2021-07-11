package kek.enxy.plantwriter.presentation.main.scan

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnStart
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.orhanobut.logger.Logger
import kek.enxy.plantwriter.R
import kek.enxy.plantwriter.databinding.FragmentScanBinding
import kek.enxy.plantwriter.presentation.common.extensions.getParentAsListener
import kek.enxy.plantwriter.presentation.main.MainRoute
import kek.enxy.plantwriter.presentation.main.ScanContract
import kek.enxy.plantwriter.presentation.main.model.DumpState
import kek.enxy.plantwriter.presentation.settings.SettingsActivity
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScanFragment : Fragment() {

    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ScanViewModel by viewModel()
    private val contract: ScanContract by lazy { getParentAsListener() }
    private val router: MainRoute by lazy { getParentAsListener() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentScanBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setObservers()
    }

    private fun setObservers() {
        contract.resolveIntentFlow
            .flowWithLifecycle(lifecycle)
            .onEach { event ->
                event.getContentIfNotHandled()?.let { intent ->
                    viewModel.setNfcIntent(intent)
                }
            }
            .launchIn(lifecycleScope)

        viewModel.dumpStateFlow
            .flowWithLifecycle(lifecycle)
            .onEach { dumpState ->
                Logger.d("dumpState = ${dumpState.javaClass.simpleName}")
                when (dumpState) {
                    is DumpState.Content -> {
                        showContentWithAnimation()
                        binding.tagDetails.setDetails(dumpState.tagId, dumpState.dump)
                    }
                    is DumpState.Loading -> {
                        showContentWithAnimation()
                        binding.tagDetails.setLoading()
                    }
                    is DumpState.Error -> {
                        binding.tagDetails.setError(dumpState.exception)
                    }
                    else -> Unit
                }
            }
            .launchIn(lifecycleScope)

        viewModel.logStateFlow
            .flowWithLifecycle(lifecycle)
            .onEach { log ->
                binding.textLog.text = log
            }
            .launchIn(lifecycleScope)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() = with(binding) {
        btnWrite.setOnClickListener { viewModel.writeDumpData() }
        toolbar.onEndBtnClicked { SettingsActivity.start(requireContext()) }
        tagDetails.setOnClickListener {
            val dump = viewModel.dump ?: return@setOnClickListener
            router.openTagDetails(dump)
        }
    }
}
