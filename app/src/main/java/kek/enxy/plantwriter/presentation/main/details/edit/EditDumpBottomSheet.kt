package kek.enxy.plantwriter.presentation.main.details.edit

import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.core.view.postDelayed
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.orhanobut.logger.Logger
import kek.enxy.plantwriter.R
import kek.enxy.plantwriter.databinding.BottomSheetEditDumpBinding
import kek.enxy.plantwriter.presentation.common.KeyboardUtils
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class EditDumpBottomSheet : BottomSheetDialogFragment() {

    companion object {
        private const val KEYBOARD_FOCUS_DELAY = 300L
    }

    private var _binding: BottomSheetEditDumpBinding? = null
    private val binding get() = _binding!!

    private val navArgs by navArgs<EditDumpBottomSheetArgs>()
    private val viewModel by viewModel<EditDumpViewModel> {
        parametersOf(navArgs.type)
    }

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
        setDetailType()
    }

    private fun setDetailType() = with(binding) {
        when (viewModel.type) {
            is EditDumpType.Balance,
            is EditDumpType.LastPaymentAmount,
            is EditDumpType.LastUseAmount -> {
                textTitle.setText(R.string.edit_dump_title_money)
                textDescription.setText(R.string.edit_dump_placeholder_money)
                chip1.setText(R.string.edit_dump_money_0)
                chip2.setText(R.string.edit_dump_money_100)
                chip3.setText(R.string.edit_dump_money_500)
                chip4.setText(R.string.edit_dump_money_1000)
            }
            is EditDumpType.UndergroundTravelTotal,
            is EditDumpType.GroundTravelTotal -> {
                textTitle.setText(R.string.edit_dump_title_count)
                textDescription.setText(R.string.edit_dump_placeholder_count)
                chip1.setText(R.string.edit_dump_count_0)
                chip2.setText(R.string.edit_dump_count_10)
                chip3.setText(R.string.edit_dump_count_20)
                chip4.setText(R.string.edit_dump_count_50)
            }
            is EditDumpType.LastPaymentDate,
            is EditDumpType.LastUseDate -> {
                textTitle.setText(R.string.edit_dump_title_date)
                textDescription.setText(R.string.edit_dump_placeholder_date)
                chip1.setText(R.string.edit_dump_date_initial)
                chip1.setOnClickListener { editText.setText(R.string.edit_dump_date_initial_value) }
                chip2.setText(R.string.edit_dump_date_today)
//                chip2.setOnClickListener { editText.setText(AppDate.now().toString()) }
                chip3.setText(R.string.edit_dump_date_yesterday)
//                chip2.setOnClickListener { editText.setText(AppDate.yesterday().toString()) }
                chip4.setText(R.string.edit_dump_date_week_ago)
//                chip2.setOnClickListener { editText.setText(AppDate.weekAgo().toString()) }
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            binding.editText.windowInsetsController?.show(WindowInsets.Type.ime())
        } else {
            KeyboardUtils.hide(requireActivity())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getTheme(): Int = R.style.AppBottomSheetDialog_ConstantCorners

    private fun initViews() = with(binding) {
        inputLayout.placeholderText = viewModel.type.value
        editText.requestFocus()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            binding.editText.windowInsetsController?.show(WindowInsets.Type.ime())
        } else {
            editText.postDelayed(KEYBOARD_FOCUS_DELAY) { // TODO: workaround, should be replaced
                KeyboardUtils.show(editText)
            }
        }

        editText.addTextChangedListener {
            Logger.d("text = $it")
        }
    }
}
