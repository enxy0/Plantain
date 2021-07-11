package kek.enxy.plantwriter.presentation.main.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import kek.enxy.plantwriter.R
import kek.enxy.plantwriter.databinding.FragmentDetailsBinding
import kek.enxy.plantwriter.presentation.common.extensions.getParentAsListener
import kek.enxy.plantwriter.presentation.main.MainRoute
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val router: MainRoute by lazy { getParentAsListener() }
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
    }

    private fun initViews() = with(binding) {
        toolbar.onStartBtnClicked { router.onReturn() }
        textBalance.text = getString(R.string.details_rub, viewModel.dump.balance.value)
        viewLastUseAmount.setDetails(
            value = getString(R.string.details_rub, viewModel.dump.lastUseAmount.value)
        )
        viewLastUseDate.setDetails(
            value = viewModel.dump.lastUseDate.toString()
        )
        viewGroundTravel.setDetails(
            value = viewModel.dump.groundTravelTotal.toString(),
            title = getString(R.string.detail_title_ground)
        )
        viewUndergroundTravel.setDetails(
            value = viewModel.dump.undergroundTravelTotal.toString(),
            title = getString(R.string.detail_title_underground)
        )
        viewLastPaymentAmount.setDetails(
            value = getString(R.string.details_rub, viewModel.dump.lastPaymentAmount.value)
        )
        viewLastPaymentDate.setDetails(
            value = viewModel.dump.lastPaymentDate.toString()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}