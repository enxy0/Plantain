package kek.enxy.plantwriter.presentation.main.details.name

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kek.enxy.plantwriter.databinding.BottomSheetNameDumpBinding
import kek.enxy.plantwriter.presentation.common.KeyboardUtils
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class NameDumpBottomSheet : BottomSheetDialogFragment() {

    companion object {
        const val KEY_REQUEST = "name_dump_request"
        const val KEY_DUMP_NAME = "dump_name"
    }

    private var _binding: BottomSheetNameDumpBinding? = null
    private val binding get() = _binding!!
    private val navArgs by navArgs<NameDumpBottomSheetArgs>()
    private val viewModel by viewModel<NameDumpViewModel> {
        parametersOf(navArgs.placeholderText)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = BottomSheetNameDumpBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = with(binding) {
        inputLayout.placeholderText = viewModel.placeHolderText
        editText.requestFocus()
        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                dismiss()
                true
            } else {
                false
            }
        }
        KeyboardUtils.show(editText)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        val inputText = binding.editText.text?.toString()
        setResult(if (inputText.isNullOrBlank()) viewModel.placeHolderText else inputText)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setResult(name: String) {
        setFragmentResult(KEY_REQUEST, bundleOf(KEY_DUMP_NAME to name))
    }
}
