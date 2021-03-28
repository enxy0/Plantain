package kek.enxy.plantwriter.presentation.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import kek.enxy.plantwriter.R
import kek.enxy.plantwriter.databinding.ViewToolbarBinding
import kek.enxy.plantwriter.presentation.common.extensions.fillWhenHasData

class AppToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = ViewToolbarBinding.inflate(LayoutInflater.from(context), this)

    var title: String = ""
        set(value) {
            field = value
            binding.textTitle.text = title
        }

    init {
        minHeight = resources.getDimensionPixelOffset(R.dimen.toolbar_height)
        initAttrs(context, attrs)
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?) = with(binding) {
        context.withStyledAttributes(attrs, R.styleable.AppToolbar) {
            textTitle.text = getString(R.styleable.AppToolbar_at_title).orEmpty()
            imgStart.fillWhenHasData(getDrawable(R.styleable.AppToolbar_at_startIcon)) { icon ->
                setImageDrawable(icon)
            }
            imgEnd.fillWhenHasData(getDrawable(R.styleable.AppToolbar_at_endIcon)) { icon ->
                setImageDrawable(icon)
            }
        }
    }

    fun onStartBtnClicked(action: (view: View) -> Unit) {
        binding.imgStart.setOnClickListener(action)
    }

    fun onEndBtnClicked(action: (view: View) -> Unit) {
        binding.imgEnd.setOnClickListener(action)
    }
}
