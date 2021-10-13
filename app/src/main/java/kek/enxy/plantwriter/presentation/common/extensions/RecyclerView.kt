package kek.enxy.plantwriter.presentation.common.extensions

import android.content.Context
import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView

val RecyclerView.ViewHolder.context: Context
    get() = itemView.context

val RecyclerView.ViewHolder.resources: Resources
    get() = itemView.resources
