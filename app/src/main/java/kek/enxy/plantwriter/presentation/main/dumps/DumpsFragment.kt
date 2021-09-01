package kek.enxy.plantwriter.presentation.main.dumps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import kek.enxy.data.readwrite.model.Dump
import kek.enxy.plantwriter.databinding.FragmentDumpsBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class DumpsFragment : Fragment() {
    private var _binding: FragmentDumpsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DumpsViewModel by viewModel()
    private val createDumpAdapter by lazy { CreateDumpAdapter(createDumpListener) }
    private val dumpsAdapter by lazy { DumpAdapter(dumpListener) }
    private val adapter by lazy { ConcatAdapter(dumpsAdapter, createDumpAdapter) }

    private val createDumpListener by lazy {
        CreateDumpAdapter.CreateDumpListener {
            val action = DumpsFragmentDirections.actionDumpsToDetails(viewModel.getEmptyDump())
            findNavController().navigate(action)
        }
    }

    private val dumpListener by lazy {
        object : DumpAdapter.DumpListener {
            override fun onDumpDetailsClicked(dump: Dump) {
                val action = DumpsFragmentDirections.actionDumpsToDetails(dump)
                findNavController().navigate(action)
            }

            override fun onDumpRemoveClicked(dump: Dump) {
                viewModel.removeDump(dump)
            }
        }
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
        dumpsStateFlow
            .flowWithLifecycle(lifecycle)
            .onEach { dumps -> dumpsAdapter.submitList(dumps) }
            .launchIn(lifecycleScope)

        createDumpStateFlow
            .flowWithLifecycle(lifecycle)
            .onEach { list -> createDumpAdapter.submitList(list) }
            .launchIn(lifecycleScope)
    }

    private fun initViews() = with(binding) {
        recyclerDumps.adapter = adapter
        toolbar.onStartBtnClicked { findNavController().navigateUp() }
    }
}
