package kek.enxy.plantwriter.presentation.main.details.edit

import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kek.enxy.data.readwrite.model.AppDate
import kek.enxy.plantwriter.R
import kek.enxy.plantwriter.databinding.BottomSheetEditDumpBinding
import kek.enxy.plantwriter.presentation.common.KeyboardUtils
import kek.enxy.plantwriter.presentation.common.extensions.setEndSelection
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import kotlin.time.Duration.Companion.milliseconds

class EditDumpBottomSheet : BottomSheetDialogFragment() {

    companion object {
        const val KEY_REQUEST = "edit_dump_request"
        const val KEY_EDIT_TYPE = "edit_type"
    }

    private var _binding: BottomSheetEditDumpBinding? = null
    private val binding get() = _binding!!

    private val navArgs by navArgs<EditDumpBottomSheetArgs>()
    private val viewModel by viewModel<EditDumpViewModel> { parametersOf(navArgs.type) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = BottomSheetEditDumpBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setObservers()
        setDetailType()
    }

    private fun setObservers() = with(binding) {
        viewModel.errorStateFlow
            .flowWithLifecycle(lifecycle)
            .onEach { error ->
                inputLayout.isErrorEnabled = error.isNotBlank()
                inputLayout.error = error
            }
            .launchIn(lifecycleScope)
    }

    private fun setDetailType() = with(binding) {
        when (viewModel.type) {
            is EditDumpType.Balance,
            is EditDumpType.LastPaymentAmount,
            is EditDumpType.LastUseAmount -> {
                editText.inputType =
                    InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_NORMAL
                textTitle.setText(R.string.edit_dump_title_money)
                inputLayout.helperText = getString(R.string.edit_dump_placeholder_money)
                chip1.setText(R.string.edit_dump_money_0)
                chip1.setOnClickListener {
                    editText.setText(R.string.edit_dump_money_0_value)
                    editText.setEndSelection()
                }
                chip2.setText(R.string.edit_dump_money_100)
                chip2.setOnClickListener {
                    editText.setText(R.string.edit_dump_money_100_value)
                    editText.setEndSelection()
                }
                chip3.setText(R.string.edit_dump_money_500)
                chip3.setOnClickListener {
                    editText.setText(R.string.edit_dump_money_500_value)
                    editText.setEndSelection()
                }
                chip4.setText(R.string.edit_dump_money_1000)
                chip4.setOnClickListener {
                    editText.setText(R.string.edit_dump_money_1000_value)
                    editText.setEndSelection()
                }
            }
            is EditDumpType.UndergroundTravelTotal,
            is EditDumpType.GroundTravelTotal -> {
                editText.inputType =
                    InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_NORMAL
                textTitle.setText(R.string.edit_dump_title_count)
                inputLayout.helperText = getString(R.string.edit_dump_placeholder_count)
                chip1.setText(R.string.edit_dump_count_0)
                chip1.setOnClickListener {
                    editText.setText(R.string.edit_dump_count_0)
                    editText.setEndSelection()
                }
                chip2.setText(R.string.edit_dump_count_10)
                chip2.setOnClickListener {
                    editText.setText(R.string.edit_dump_count_10)
                    editText.setEndSelection()
                }
                chip3.setText(R.string.edit_dump_count_20)
                chip3.setOnClickListener {
                    editText.setText(R.string.edit_dump_count_20)
                    editText.setEndSelection()
                }
                chip4.setText(R.string.edit_dump_count_50)
                chip4.setOnClickListener {
                    editText.setText(R.string.edit_dump_count_50)
                    editText.setEndSelection()
                }
            }
            is EditDumpType.LastPaymentDate,
            is EditDumpType.LastUseDate -> {
                editText.inputType = InputType.TYPE_CLASS_TEXT
                textTitle.setText(R.string.edit_dump_title_date)
                inputLayout.helperText = getString(R.string.edit_dump_placeholder_date)
                chip1.setText(R.string.edit_dump_date_initial)
                chip1.setOnClickListener {
                    editText.setText(R.string.edit_dump_date_initial_value)
                    editText.setEndSelection()
                }
                chip2.setText(R.string.edit_dump_date_today)
                chip2.setOnClickListener {
                    editText.setText(AppDate.now().toString())
                    editText.setEndSelection()
                }
                chip3.setText(R.string.edit_dump_date_yesterday)
                chip3.setOnClickListener {
                    editText.setText(AppDate.yesterday().toString())
                    editText.setEndSelection()
                }
                chip4.setText(R.string.edit_dump_date_week_ago)
                chip4.setOnClickListener {
                    editText.setText(AppDate.weekAgo().toString())
                    editText.setEndSelection()
                }
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (viewModel.checkValueChange()) {
            setResult(viewModel.type)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() = with(binding) {
        inputLayout.placeholderText = viewModel.type.value
        editText.requestFocus()
        editText.doAfterTextChanged { text ->
            viewModel.handleUserInput(text?.toString().orEmpty())
        }
        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                dismiss()
                true
            } else {
                false
            }
        }
        KeyboardUtils.show(editText, 300.milliseconds)
    }

    private fun setResult(type: EditDumpType) {
        setFragmentResult(KEY_REQUEST, bundleOf(KEY_EDIT_TYPE to type))
    }
}
