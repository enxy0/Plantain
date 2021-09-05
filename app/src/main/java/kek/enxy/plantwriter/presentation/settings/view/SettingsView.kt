package kek.enxy.plantwriter.presentation.settings.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import kek.enxy.plantwriter.R
import kek.enxy.plantwriter.databinding.ViewSettingsBinding
import kek.enxy.plantwriter.presentation.common.extensions.fillWhenHasData
import kek.enxy.plantwriter.presentation.common.extensions.getResourceFromAttr

class SettingsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding = ViewSettingsBinding.inflate(LayoutInflater.from(context), this)
    private var listener: OnSettingsChangeListener? = null

    var title: String = ""
        set(value) {
            field = value
            binding.textTitle.text = value
        }

    var description: String = ""
        set(value) {
            field = value
            binding.textDescription.fillWhenHasData(value) {
                text = value
            }
        }

    var isChecked: Boolean = false
        set(value) {
            field = value
            binding.viewSwitch.isChecked = value
        }

    init {
        with(binding) {
            viewSwitch.setOnCheckedChangeListener { _, isChecked -> listener?.onChange(isChecked) }
            setOnClickListener { isChecked = !isChecked }
        }
        setBackgroundResource(context.getResourceFromAttr(android.R.attr.selectableItemBackground))
        initAttrs(context, attrs)
    }

    fun init(onSettingsChangeListener: OnSettingsChangeListener) {
        listener = onSettingsChangeListener
    }

    private fun initAttrs(
        context: Context,
        attrs: AttributeSet?
    ) = context.withStyledAttributes(attrs, R.styleable.SettingsView) {
        title = getString(R.styleable.SettingsView_sv_title) ?: title
        description = getString(R.styleable.SettingsView_sv_description) ?: description
    }

    fun interface OnSettingsChangeListener {
        fun onChange(isChecked: Boolean)
    }
}
