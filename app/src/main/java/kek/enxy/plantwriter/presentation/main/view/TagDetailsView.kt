package kek.enxy.plantwriter.presentation.main.view

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.material.card.MaterialCardView
import kek.enxy.data.readwrite.model.Dump
import kek.enxy.domain.write.model.WrongSectorKeyException
import kek.enxy.plantwriter.R
import kek.enxy.plantwriter.databinding.ViewTagDetailsBinding
import kek.enxy.plantwriter.presentation.common.extensions.fillWhenHasData
import kek.enxy.plantwriter.presentation.common.extensions.getColorFromAttr
import java.util.Locale

class TagDetailsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    companion object {
        private const val DURATION = 100L
    }

    private val binding = ViewTagDetailsBinding.inflate(LayoutInflater.from(context), this)
    private val grayColor by lazy {
        context.getColorFromAttr(android.R.attr.textColorSecondary)
    }
    private val redColor by lazy {
        ContextCompat.getColor(context, R.color.red_500)
    }

    fun setDetails(tagUID: String, dump: Dump) = with(binding) {
        textError.fadeOut()
        textBalance.fadeIn()
        textBalance.fillWhenHasData(dump.balance) { balance ->
            text = resources.getString(R.string.main_tag_balance, balance.value)
        }
        textUID.fadeIn()
        textUID.fillWhenHasData(tagUID) { uid ->
            text = resources.getString(
                R.string.main_tag_uid,
                uid.toUpperCase(Locale.getDefault())
            )
        }
        imgInfo.fadeIn()
        imgInfo.imageTintList = ColorStateList.valueOf(grayColor)
        progress.fadeOut()
    }

    fun setError(throwable: Throwable) = with(binding) {
        with(textError) {
            val message = when (throwable.cause) {
                is WrongSectorKeyException ->
                    resources.getString(R.string.main_tag_error_auth, throwable.cause?.message)
                else ->
                    resources.getString(R.string.main_tag_error_connection_lost)
            }
            text = resources.getString(R.string.main_tag_error, message)
            fadeIn()
        }
        textBalance.fadeOut()
        textUID.fadeOut()
        imgInfo.fadeIn()
        imgInfo.imageTintList = ColorStateList.valueOf(redColor)
        progress.fadeOut()
    }

    fun setLoading() = with(binding) {
        textBalance.fadeOut()
        textUID.fadeOut()
        textError.fadeOut()
        imgInfo.fadeOut()
        progress.fadeIn()
    }

    private fun <T : View> T.fadeIn() {
        animate()
            .alpha(1.0f)
            .setDuration(DURATION)
            .withStartAction { isVisible = true }
            .start()
    }

    private fun <T : View> T.fadeOut() {
        animate()
            .alpha(0.0f)
            .setDuration(DURATION)
            .withEndAction { isVisible = false }
            .start()
    }
}
