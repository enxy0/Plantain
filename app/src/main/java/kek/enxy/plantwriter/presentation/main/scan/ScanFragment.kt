package kek.enxy.plantwriter.presentation.main.scan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.SimpleItemAnimator
import kek.enxy.plantwriter.databinding.FragmentScanBinding
import kek.enxy.plantwriter.presentation.common.extensions.getParentAsListener
import kek.enxy.plantwriter.presentation.main.ScanContract
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScanFragment : Fragment() {
    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<ScanViewModel>()
    private val contract by lazy<ScanContract> { getParentAsListener() }
    private val plantainAdapter by lazy { PlantainAdapter() }
    private val dumpsAdapter by lazy {
        DumpsAdapter { findNavController().navigate(ScanFragmentDirections.actionScanToDumps()) }
    }
    private val historiesAdapter by lazy {
        HistoriesAdapter {  }
    }
    private val currentDumpAdapter by lazy {
        CurrentDumpAdapter { dump ->
            findNavController().navigate(ScanFragmentDirections.actionScanToDetails(dump))
        }
    }
    private val concatAdapter: ConcatAdapter by lazy {
        ConcatAdapter(plantainAdapter, dumpsAdapter, historiesAdapter, currentDumpAdapter)
    }

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
                currentDumpAdapter.submitList(listOf(dumpState))
            }
            .launchIn(lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() = with(binding) {
        toolbar.onEndBtnClicked {
            findNavController().navigate(ScanFragmentDirections.actionScanToSettings())
        }
        plantainAdapter.submitList(listOf(Unit))
        dumpsAdapter.submitList(listOf(Unit))
        historiesAdapter.submitList(listOf(Unit))
        with(recyclerMain) {
            adapter = concatAdapter
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            addItemDecoration(ScanDecoration())
        }
    }
}
