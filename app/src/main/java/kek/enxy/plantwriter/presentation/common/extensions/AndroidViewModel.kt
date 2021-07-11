package kek.enxy.plantwriter.presentation.common.extensions

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel

fun AndroidViewModel.getString(@StringRes resId: Int) =
    (getApplication() as Application).getString(resId)