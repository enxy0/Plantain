package kek.enxy.data.dumps

import kek.enxy.data.db.dumps.model.DumpEntity
import kek.enxy.data.readwrite.model.Dump

fun Dump.toEntity() = DumpEntity(
    uid = uid,
    name = name,
    balance = balance,
    lastUseAmount = lastUseAmount,
    lastUseDate = lastUseDate,
    lastPaymentAmount = lastPaymentAmount,
    lastPaymentDate = lastPaymentDate,
    groundTravelTotal = groundTravelTotal,
    undergroundTravelTotal = undergroundTravelTotal
)

fun DumpEntity.toDomain() = Dump(
    id = id,
    uid = uid,
    name = name,
    balance = balance,
    lastUseAmount = lastUseAmount,
    lastUseDate = lastUseDate,
    lastPaymentAmount = lastPaymentAmount,
    lastPaymentDate = lastPaymentDate,
    groundTravelTotal = groundTravelTotal,
    undergroundTravelTotal = undergroundTravelTotal
)
