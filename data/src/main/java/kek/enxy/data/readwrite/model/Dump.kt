package kek.enxy.data.readwrite.model

data class Dump(
    val balance: Rubles,           // Баланс
    val lastUseAmount: Rubles,     // Сумма за последнее использование
    val lastUseDate: AppDate,      // Дата последнего использования
    val lastPaymentAmount: Rubles, // Сумма последнего пополнения
    val lastPaymentDate: AppDate,  // Дата последнего пополнения
    val groundTravelTotal: Count,  // Количество поездок на наземном транспорте
    val subwayTravelTotal: Count   // Количество поездок в метро
)
