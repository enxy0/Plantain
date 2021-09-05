package kek.enxy.plantwriter.presentation.main.scan

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kek.enxy.plantwriter.presentation.common.extensions.dp

class ScanDecoration : RecyclerView.ItemDecoration() {

    private val marginDouble by lazy { 32.dp.toInt() }
    private val margin by lazy { 16.dp.toInt() }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val itemCount = parent.adapter?.itemCount ?: 0
        parent.getChildAdapterPosition(view)
            .takeIf { position -> position in 0 until itemCount }
            ?.let { position ->
                outRect.set(
                    margin,
                    if (position == 0) marginDouble else margin,
                    margin,
                    0
                )
            }
    }
}
