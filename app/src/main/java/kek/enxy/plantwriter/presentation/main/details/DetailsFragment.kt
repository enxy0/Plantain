package kek.enxy.plantwriter.presentation.main.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kek.enxy.plantwriter.R
import kek.enxy.plantwriter.databinding.FragmentDetailsBinding
import kek.enxy.plantwriter.presentation.common.extensions.getParentAsListener
import kek.enxy.plantwriter.presentation.main.DetailsContract
import kek.enxy.plantwriter.presentation.main.details.edit.EditDumpType
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
    }

    private fun initViews() = with(binding) {
        toolbar.onStartBtnClicked { contract.onReturn() }
        btnSaveDump.setOnClickListener { viewModel.save() }
        with(textBalance) {
            text = getString(R.string.details_rub, viewModel.dump.balance.value)
            setOnClickListener {
                val action = DetailsFragmentDirections.actionDetailsToEditDump(
                    EditDumpType.Balance(viewModel.dump.balance)
                )
                findNavController().navigate(action)
            }
        }
        with(viewLastUseAmount) {
            setDetails(getString(R.string.details_rub, viewModel.dump.lastUseAmount.value))
            setOnClickListener {
                val action = DetailsFragmentDirections.actionDetailsToEditDump(
                    EditDumpType.LastUseAmount(viewModel.dump.lastUseAmount)
                )
                findNavController().navigate(action)
            }
        }
        with(viewLastUseDate) {
            setDetails(viewModel.dump.lastUseDate.toString())
            setOnClickListener {
                val action = DetailsFragmentDirections.actionDetailsToEditDump(
                    EditDumpType.LastUseDate(viewModel.dump.lastUseDate)
                )
                findNavController().navigate(action)
            }
        }
        with(viewGroundTravel) {
            setDetails(
                value = viewModel.dump.groundTravelTotal.toString(),
                title = getString(R.string.detail_title_ground)
            )
            setOnClickListener {
                val action = DetailsFragmentDirections.actionDetailsToEditDump(
                    EditDumpType.GroundTravelTotal(viewModel.dump.groundTravelTotal)
                )
                findNavController().navigate(action)
            }
        }
        with(viewUndergroundTravel) {
            setDetails(
                value = viewModel.dump.undergroundTravelTotal.toString(),
                title = getString(R.string.detail_title_underground)
            )
            setOnClickListener {
                val action = DetailsFragmentDirections.actionDetailsToEditDump(
                    EditDumpType.UndergroundTravelTotal(viewModel.dump.undergroundTravelTotal)
                )
                findNavController().navigate(action)
            }
        }
        with(viewLastPaymentAmount) {
            setDetails(getString(R.string.details_rub, viewModel.dump.lastPaymentAmount.value))
            setOnClickListener {
                val action = DetailsFragmentDirections.actionDetailsToEditDump(
                    EditDumpType.LastPaymentAmount(viewModel.dump.lastPaymentAmount)
                )
                findNavController().navigate(action)
            }
        }
        with(viewLastPaymentDate) {
            setDetails(viewModel.dump.lastPaymentDate.toString())
            setOnClickListener {
                val action = DetailsFragmentDirections.actionDetailsToEditDump(
                    EditDumpType.LastPaymentDate(viewModel.dump.lastPaymentDate)
                )
                findNavController().navigate(action)
            }
        }
    }

    private fun setObservers() = with(viewModel) {
        saveResultLiveData.observe(viewLifecycleOwner) { isSuccessfull ->
            Toast.makeText(
                requireContext(),
                "Чтение: ${if (isSuccessfull) "успешно!" else "ошибка!"}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
