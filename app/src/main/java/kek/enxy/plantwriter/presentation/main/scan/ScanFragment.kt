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
import androidx.navigation.fragment.findNavController
import kek.enxy.plantwriter.databinding.FragmentScanBinding
import kek.enxy.plantwriter.presentation.common.extensions.getParentAsListener
import kek.enxy.plantwriter.presentation.main.ScanContract
import kek.enxy.plantwriter.presentation.main.model.DumpState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScanFragment : Fragment() {

    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ScanViewModel by viewModel()
    private val contract: ScanContract by lazy { getParentAsListener() }

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
                when (dumpState) {
                    is DumpState.Content -> {
                        showContentWithAnimation()
                        binding.viewCurrentDump.setDetails(dumpState.dump)
                    }
                    is DumpState.Loading -> {
                        showContentWithAnimation()
                        binding.viewCurrentDump.setLoading()
                    }
                    is DumpState.Error -> {
                        binding.viewCurrentDump.setError(dumpState.exception)
                    }
                    else -> Unit
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun showContentWithAnimation() = with(binding) {
        if (viewCurrentDump.isGone) {
            val viewCurrentDumpAnimator = ObjectAnimator.ofFloat(viewCurrentDump, View.ALPHA, 0.0f, 1.0f)
            val animatorSet = AnimatorSet().apply {
                duration = 300L
                playTogether(viewCurrentDumpAnimator)
                doOnStart {
                    viewCurrentDump.isVisible = true
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
        toolbar.onEndBtnClicked {
            findNavController().navigate(ScanFragmentDirections.actionScanToSettings())
        }
        viewCurrentDump.setOnClickListener {
            val dump = viewModel.dump ?: return@setOnClickListener
            findNavController().navigate(ScanFragmentDirections.actionScanToDetails(dump))
        }
        viewDumps.setOnClickListener {
            findNavController().navigate(ScanFragmentDirections.actionScanToDumps())
        }
    }
}
