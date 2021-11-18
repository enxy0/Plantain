package kek.enxy.plantwriter.presentation.common

import android.content.Context
import kek.enxy.domain.write.model.WrongSectorKeyException
import kek.enxy.plantwriter.R

fun Throwable.getTextForUser(context: Context): String = when (cause) {
    is WrongSectorKeyException ->
        context.resources.getString(R.string.main_tag_error_auth, cause?.message)
    else ->
        context.resources.getString(R.string.main_tag_error_connection_lost)
}
