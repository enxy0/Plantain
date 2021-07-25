package kek.enxy.plantwriter.presentation.main.scan.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.google.android.material.card.MaterialCardView
import kek.enxy.plantwriter.databinding.ViewDumpsBinding

class DumpsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    private val binding = ViewDumpsBinding.inflate(LayoutInflater.from(context), this)
}
