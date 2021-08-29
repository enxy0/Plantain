package kek.enxy.plantwriter.presentation.common.extensions

import android.app.Application
import android.content.Context
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel

val AndroidViewModel.context: Context get() = getApplication()

fun AndroidViewModel.getString(@StringRes resId: Int) =
    (getApplication() as Application).getString(resId)
