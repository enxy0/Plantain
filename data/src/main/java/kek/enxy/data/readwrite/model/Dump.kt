package kek.enxy.data.readwrite.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Dump(
    val id: Int = 0,
    val uid: String,
    val name: String = "", // Название для сохраненного дампа
    val balance: Rubles, // Баланс
    val lastUseAmount: Rubles, // Сумма за последнее использование
    val lastUseDate: AppDate, // Дата последнего использования
    val lastPaymentAmount: Rubles, // Сумма последнего пополнения
    val lastPaymentDate: AppDate, // Дата последнего пополнения
    val groundTravelTotal: Count, // Количество поездок на наземном транспорте
    val undergroundTravelTotal: Count // Количество поездок в метро
) : Parcelable
