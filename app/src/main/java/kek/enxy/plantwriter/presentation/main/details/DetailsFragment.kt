package kek.enxy.plantwriter.presentation.main.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import kek.enxy.plantwriter.R
import kek.enxy.plantwriter.databinding.FragmentDetailsBinding
import kek.enxy.plantwriter.presentation.common.extensions.getParentAsListener
import kek.enxy.plantwriter.presentation.common.extensions.toast
import kek.enxy.plantwriter.presentation.main.DetailsContract
import kek.enxy.plantwriter.presentation.main.details.edit.EditDumpBottomSheet
import kek.enxy.plantwriter.presentation.main.details.edit.EditDumpType
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val contract: DetailsContract by lazy { getParentAsListener() }
    private val navArgs: DetailsFragmentArgs by navArgs()
    private val viewModel: DetailsViewModel by viewModel {
        parametersOf(navArgs.dump)
    }

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
        setFragmentResultListener(EditDumpBottomSheet.KEY_REQUEST) { _, bundle ->
            val type: EditDumpType? = bundle.getParcelable(EditDumpBottomSheet.KEY_EDIT_TYPE)
            if (type != null) {
                viewModel.handleDumpUpdate(type)
            }
        }
    }

    private fun initViews() = with(binding) {
        toolbar.onStartBtnClicked { contract.onReturn() }
        btnSaveDump.setOnClickListener { viewModel.save() }
    }

    private fun setObservers() = with(binding) {
        viewModel.saveResultLiveData.observe(viewLifecycleOwner) { isSuccessful ->
            toast(if (isSuccessful) R.string.details_save_dump_ok else R.string.details_save_dump_error)
        }
        viewModel.writeResultLiveData.observe(viewLifecycleOwner) { isSuccessful ->
            toast(if (isSuccessful) R.string.details_write_dump_ok else R.string.details_write_dump_error)
        }
        viewModel.dumpStateFlow
            .flowWithLifecycle(lifecycle)
            .onEach { dump ->
                with(textBalance) {
                    text = getString(R.string.details_rub, dump.balance.value)
                    setOnClickListener {
                        val action = DetailsFragmentDirections.actionDetailsToEditDump(
                            EditDumpType.Balance(dump.balance)
                        )
                        findNavController().navigate(action)
                    }
                }
                with(viewLastUseAmount) {
                    setDetails(getString(R.string.details_rub, dump.lastUseAmount.value))
                    setOnClickListener {
                        val action = DetailsFragmentDirections.actionDetailsToEditDump(
                            EditDumpType.LastUseAmount(dump.lastUseAmount)
                        )
                        findNavController().navigate(action)
                    }
                }
                with(viewLastUseDate) {
                    setDetails(dump.lastUseDate.toString())
                    setOnClickListener {
                        val action = DetailsFragmentDirections.actionDetailsToEditDump(
                            EditDumpType.LastUseDate(dump.lastUseDate)
                        )
                        findNavController().navigate(action)
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
                        findNavController().navigate(action)
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
                        findNavController().navigate(action)
                    }
                }
                with(viewLastPaymentAmount) {
                    setDetails(getString(R.string.details_rub, dump.lastPaymentAmount.value))
                    setOnClickListener {
                        val action = DetailsFragmentDirections.actionDetailsToEditDump(
                            EditDumpType.LastPaymentAmount(dump.lastPaymentAmount)
                        )
                        findNavController().navigate(action)
                    }
                }
                with(viewLastPaymentDate) {
                    setDetails(dump.lastPaymentDate.toString())
                    setOnClickListener {
                        val action = DetailsFragmentDirections.actionDetailsToEditDump(
                            EditDumpType.LastPaymentDate(dump.lastPaymentDate)
                        )
                        findNavController().navigate(action)
                    }
                }
            }
            .launchIn(lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
