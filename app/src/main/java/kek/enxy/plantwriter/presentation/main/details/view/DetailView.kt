package kek.enxy.plantwriter.presentation.main.details.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.setPadding
import kek.enxy.plantwriter.R
import kek.enxy.plantwriter.databinding.ViewDetailBinding

class DetailView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding = ViewDetailBinding.inflate(LayoutInflater.from(context), this)

    var type = DetailType.MONEY
        set(value) {
            field = value
            binding.textTitle.text = getTitleByType(value)
            binding.image.setImageDrawable(getIconByType(value))
        }

    init {
        orientation = VERTICAL
        background = ContextCompat.getDrawable(context, R.drawable.bg_detail)
        setPadding(resources.getDimensionPixelSize(R.dimen.margin_normal))
        initAttrs(context, attrs)
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?) = with(binding) {
        context.withStyledAttributes(attrs, R.styleable.DetailView) {
            type = DetailType.values()[getInt(R.styleable.DetailView_dv_type, DetailType.MONEY.ordinal)]
            getString(R.styleable.DetailView_dv_title)?.let { title ->
                textTitle.text = title
            }
            getString(R.styleable.DetailView_dv_value)?.let { value ->
                textValue.text = value
            }
            getDrawable(R.styleable.DetailView_dv_icon)?.let { drawable ->
                image.setImageDrawable(drawable)
            }
        }
    }

    fun setDetails(value: String? = null, title: String? = null) = with(binding) {
        title?.let { textTitle.text = it }
        value?.let { textValue.text = it }
    }

    private fun getTitleByType(type: DetailType) = when (type) {
        DetailType.MONEY -> resources.getString(R.string.detail_balance)
        DetailType.DATE -> resources.getString(R.string.detail_date)
        DetailType.GROUND_TOTAL,
        DetailType.UNDERGROUND_TOTAL -> resources.getString(R.string.detail_count)
    }

    private fun getIconByType(type: DetailType) = when (type) {
        DetailType.MONEY -> ContextCompat.getDrawable(context, R.drawable.ic_outline_info_24)
        DetailType.DATE -> ContextCompat.getDrawable(context, R.drawable.ic_outline_date_range_24)
        DetailType.GROUND_TOTAL -> ContextCompat.getDrawable(context, R.drawable.ic_bus_24)
        DetailType.UNDERGROUND_TOTAL -> ContextCompat.getDrawable(context, R.drawable.ic_metro_24)
    }

    enum class DetailType {
        MONEY, DATE, GROUND_TOTAL, UNDERGROUND_TOTAL
    }
}
