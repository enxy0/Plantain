package kek.enxy.plantwriter.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kek.enxy.data.settings.model.AppTheme
import kek.enxy.plantwriter.databinding.FragmentSettingsBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<SettingsViewModel>()

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
        setObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() = with(binding) {
        settingsTheme.init { isDark ->
            viewModel.setDarkTheme(isDark)
        }
        toolbar.onStartBtnClicked {
            findNavController().navigateUp()
        }
        textAbout.setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionSettingsToAbout())
        }
    }

    private fun setObservers() = with(viewModel) {
        darkThemeFlow
            .flowWithLifecycle(lifecycle)
            .onEach { theme ->
                binding.settingsTheme.isChecked = theme == AppTheme.DARK
            }
            .launchIn(lifecycleScope)
    }
}
