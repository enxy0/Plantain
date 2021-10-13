package kek.enxy.plantwriter.presentation.main.details.edit

import android.os.Parcelable
import androidx.annotation.Keep
import kek.enxy.data.readwrite.model.AppDate
import kek.enxy.data.readwrite.model.Count
import kek.enxy.data.readwrite.model.Rubles
import kotlinx.parcelize.Parcelize

@Keep
sealed class EditDumpType(
    val value: String
) : Parcelable {
    @Parcelize data class Balance(val rubles: Rubles) : EditDumpType(rubles.toString())
    @Parcelize data class LastUseAmount(val rubles: Rubles) : EditDumpType(rubles.toString())
    @Parcelize data class LastUseDate(val date: AppDate) : EditDumpType(date.toString())
    @Parcelize data class LastPaymentAmount(val rubles: Rubles) : EditDumpType(rubles.toString())
    @Parcelize data class LastPaymentDate(val date: AppDate) : EditDumpType(date.toString())
    @Parcelize data class GroundTravelTotal(val count: Count) : EditDumpType(count.toString())
    @Parcelize data class UndergroundTravelTotal(val count: Count) : EditDumpType(count.toString())
}
