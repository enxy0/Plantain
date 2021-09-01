package kek.enxy.plantwriter.presentation.settings.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import kek.enxy.plantwriter.R
import kek.enxy.plantwriter.databinding.ViewSettingsBinding
import kek.enxy.plantwriter.presentation.common.extensions.getResourceFromAttr

class SettingsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding = ViewSettingsBinding.inflate(LayoutInflater.from(context), this)

    var title: String = ""
        set(value) {
            field = value
            binding.textTitle.text = value
        }

    var description: String = ""
        set(value) {
            field = value
            binding.textDescription.text = value
        }

    init {
        setBackgroundResource(context.getResourceFromAttr(android.R.attr.selectableItemBackground))
        initAttrs(context, attrs)
    }

    fun init(action: (isChecked: Boolean) -> Unit) = with(binding) {
        viewSwitch.setOnCheckedChangeListener { _, isChecked -> action(isChecked) }
        setOnClickListener { viewSwitch.isChecked = !viewSwitch.isChecked }
    }

    private fun initAttrs(
        context: Context,
        attrs: AttributeSet?
    ) = context.withStyledAttributes(attrs, R.styleable.SettingsView) {
        title = getString(R.styleable.SettingsView_sv_title) ?: title
        description = getString(R.styleable.SettingsView_sv_description) ?: description
    }
}
