package kek.enxy.plantwriter.presentation.main.dumps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kek.enxy.plantwriter.databinding.FragmentDumpsBinding
import kek.enxy.plantwriter.presentation.common.extensions.getParentAsListener
import kek.enxy.plantwriter.presentation.main.DumpsContract
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class DumpsFragment : Fragment() {

    private var _binding: FragmentDumpsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DumpsViewModel by viewModel()
    private val contract: DumpsContract by lazy { getParentAsListener() }
    private val adapter by lazy { DumpAdapter(dumpListener) }

    private val dumpListener by lazy {
        DumpAdapter.DumpListener { dump -> contract.openDumpDetails(dump) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentDumpsBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setObservers()
    }

    private fun setObservers() = with(viewModel) {
        dumpSharedFlow
            .flowWithLifecycle(lifecycle)
            .onEach { dumps ->
                adapter.submitList(dumps)
            }
            .launchIn(lifecycleScope)
    }

    private fun initViews() = with(binding) {
        recyclerDumps.adapter = adapter
    }
}
