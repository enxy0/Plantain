package kek.enxy.plantwriter.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kek.enxy.plantwriter.databinding.FragmentSettingsBinding
import kek.enxy.plantwriter.presentation.common.ThemeUtils

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSettingsBinding.inflate(inflater, container, false).also {
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
        toolbar.onStartBtnClicked { findNavController().navigateUp() }
        settingsTheme.init { isDark -> ThemeUtils.setNightMode(isDark) }
        textAbout.setOnClickListener { findNavController().navigate(SettingsFragmentDirections.actionSettingsToAbout()) }
    }
}
