package kek.enxy.plantwriter.presentation.main.scan.view

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.material.card.MaterialCardView
import kek.enxy.data.readwrite.model.Dump
import kek.enxy.plantwriter.R
import kek.enxy.plantwriter.databinding.ViewTagDetailsBinding
import kek.enxy.plantwriter.presentation.common.extensions.fillWhenHasData
import kek.enxy.plantwriter.presentation.common.extensions.getColorFromAttr
import kek.enxy.plantwriter.presentation.common.getTextForUser
import java.util.Locale

class ScanDumpView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    companion object {
        private const val DURATION = 100L
    }

    private val binding = ViewTagDetailsBinding.inflate(LayoutInflater.from(context), this)
    private val grayColor by lazy {
        context.getColorFromAttr(R.attr.colorControlNormal)
    }
    private val redColor by lazy {
        ContextCompat.getColor(context, R.color.red_500)
    }

    fun setDetails(dump: Dump) = with(binding) {
        textError.fadeOut()
        textBalance.fadeIn()
        textBalance.fillWhenHasData(dump.balance) { balance ->
            text = resources.getString(R.string.main_tag_balance, balance.value)
        }
        textUID.fadeIn()
        textUID.fillWhenHasData(dump.uid) { uid ->
            text = resources.getString(
                R.string.main_tag_uid,
                uid.uppercase(Locale.ENGLISH)
            )
        }
        imgInfo.fadeIn()
        imgInfo.imageTintList = ColorStateList.valueOf(grayColor)
        imgNext.fadeIn()
        progress.fadeOut()
    }

    fun setError(throwable: Throwable) = with(binding) {
        with(textError) {
            text = throwable.getTextForUser(context)
            fadeIn()
        }
        textBalance.fadeOut()
        textUID.fadeOut()
        imgInfo.fadeIn()
        imgInfo.imageTintList = ColorStateList.valueOf(redColor)
        imgNext.fadeOut()
        progress.fadeOut()
    }

    fun setLoading() = with(binding) {
        textBalance.fadeOut()
        textUID.fadeOut()
        textError.fadeOut()
        imgInfo.fadeOut()
        imgNext.fadeOut()
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
