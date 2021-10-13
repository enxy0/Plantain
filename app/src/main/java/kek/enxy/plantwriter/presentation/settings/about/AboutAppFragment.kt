package kek.enxy.plantwriter.presentation.settings.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kek.enxy.plantwriter.BuildConfig
import kek.enxy.plantwriter.R
import kek.enxy.plantwriter.databinding.FragmentAboutBinding
import kek.enxy.plantwriter.presentation.common.Constants
import kek.enxy.plantwriter.presentation.common.IntentUtils

class AboutAppFragment : Fragment() {
    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAboutBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() = with(binding) {
        textAppVersion.text = getString(
            R.string.about_version,
            BuildConfig.VERSION_NAME,
            BuildConfig.VERSION_CODE
        )
        textAuthor.setOnClickListener {
            IntentUtils.openLink(requireContext(), Constants.GITHUB_AUTHOR_URL)
        }
        toolbar.onStartBtnClicked { findNavController().navigateUp() }
    }
}
