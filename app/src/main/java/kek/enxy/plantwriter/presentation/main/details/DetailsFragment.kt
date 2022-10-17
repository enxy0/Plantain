package kek.enxy.plantwriter.presentation.main.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import kek.enxy.plantwriter.R
import kek.enxy.plantwriter.databinding.FragmentDetailsBinding
import kek.enxy.plantwriter.presentation.common.extensions.getParcelableCompat
import kek.enxy.plantwriter.presentation.common.extensions.getParentAsListener
import kek.enxy.plantwriter.presentation.common.extensions.safeNavigate
import kek.enxy.plantwriter.presentation.main.ScanContract
import kek.enxy.plantwriter.presentation.main.details.edit.EditDumpBottomSheet
import kek.enxy.plantwriter.presentation.main.details.edit.EditDumpType
import kek.enxy.plantwriter.presentation.main.details.name.NameDumpBottomSheet
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val navArgs: DetailsFragmentArgs by navArgs()
    private val viewModel: DetailsViewModel by viewModel { parametersOf(navArgs.dump) }
    private val contract: ScanContract by lazy { getParentAsListener() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentDetailsBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setObservers()
        setFragmentListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() = with(binding) {
        toolbar.onStartBtnClicked { findNavController().navigateUp() }
        btnSaveDump.setOnClickListener {
            if (viewModel.dump.name.isBlank()) {
                val action = DetailsFragmentDirections.actionDetailsToNameDump(viewModel.generatedDumpName)
                findNavController().safeNavigate(action)
            } else {
                viewModel.save()
            }
        }
        btnWriteDump.setOnClickListener {
            viewModel.write(contract.resolveIntentFlow)
        }
    }

    private fun setObservers() = with(binding) {
        viewModel.saveResultLiveData.observe(viewLifecycleOwner) { message -> snackbar(message) }
        viewModel.writeResultLiveData.observe(viewLifecycleOwner) { message -> snackbar(message) }
        viewModel.dumpStateFlow
            .flowWithLifecycle(lifecycle)
            .onEach { dump ->
                with(textBalance) {
                    text = getString(R.string.details_rub, dump.balance.value)
                    setOnClickListener {
                        val action = DetailsFragmentDirections.actionDetailsToEditDump(
                            EditDumpType.Balance(dump.balance)
                        )
                        findNavController().safeNavigate(action)
                    }
                }
                textUidHint.isVisible = dump.isActualCard
                with(textUid) {
                    isVisible = dump.isActualCard
                    text = dump.uid.uppercase()
                    setOnClickListener(null)
                }
                with(viewLastUseAmount) {
                    setDetails(getString(R.string.details_rub, dump.lastUseAmount.value))
                    setOnClickListener {
                        val action = DetailsFragmentDirections.actionDetailsToEditDump(
                            EditDumpType.LastUseAmount(dump.lastUseAmount)
                        )
                        findNavController().safeNavigate(action)
                    }
                }
                with(viewLastUseDate) {
                    setDetails(dump.lastUseDate.toString())
                    setOnClickListener {
                        val action = DetailsFragmentDirections.actionDetailsToEditDump(
                            EditDumpType.LastUseDate(dump.lastUseDate)
                        )
                        findNavController().safeNavigate(action)
                    }
                }
                with(viewGroundTravel) {
                    setDetails(
                        value = dump.groundTravelTotal.toString(),
                        title = getString(R.string.detail_title_ground)
                    )
                    setOnClickListener {
                        val action = DetailsFragmentDirections.actionDetailsToEditDump(
                            EditDumpType.GroundTravelTotal(dump.groundTravelTotal)
                        )
                        findNavController().safeNavigate(action)
                    }
                }
                with(viewUndergroundTravel) {
                    setDetails(
                        value = dump.undergroundTravelTotal.toString(),
                        title = getString(R.string.detail_title_underground)
                    )
                    setOnClickListener {
                        val action = DetailsFragmentDirections.actionDetailsToEditDump(
                            EditDumpType.UndergroundTravelTotal(dump.undergroundTravelTotal)
                        )
                        findNavController().safeNavigate(action)
                    }
                }
                with(viewLastPaymentAmount) {
                    setDetails(getString(R.string.details_rub, dump.lastPaymentAmount.value))
                    setOnClickListener {
                        val action = DetailsFragmentDirections.actionDetailsToEditDump(
                            EditDumpType.LastPaymentAmount(dump.lastPaymentAmount)
                        )
                        findNavController().safeNavigate(action)
                    }
                }
                with(viewLastPaymentDate) {
                    setDetails(dump.lastPaymentDate.toString())
                    setOnClickListener {
                        val action = DetailsFragmentDirections.actionDetailsToEditDump(
                            EditDumpType.LastPaymentDate(dump.lastPaymentDate)
                        )
                        findNavController().safeNavigate(action)
                    }
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun setFragmentListeners() {
        setFragmentResultListener(EditDumpBottomSheet.KEY_REQUEST) { _, bundle ->
            val type: EditDumpType? = bundle.getParcelableCompat(EditDumpBottomSheet.KEY_EDIT_TYPE)
            if (type != null) {
                viewModel.handleDumpUpdate(type)
            }
        }
        setFragmentResultListener(NameDumpBottomSheet.KEY_REQUEST) { _, bundle ->
            val name = bundle.getString(NameDumpBottomSheet.KEY_DUMP_NAME).orEmpty()
            viewModel.save(name)
        }
    }

    private fun snackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setAnchorView(binding.layoutActions)
            .show()
    }
}
