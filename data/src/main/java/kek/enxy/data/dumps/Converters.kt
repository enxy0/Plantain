package kek.enxy.data.dumps

import kek.enxy.data.db.dumps.model.DumpEntity
import kek.enxy.data.readwrite.model.Dump

fun Dump.toEntity() = DumpEntity(
    id = id,
    uid = uid,
    name = name,
    sector4 = sector4,
    sector5 = sector5
)

fun DumpEntity.toDomain() = Dump(
    id = id,
    uid = uid,
    name = name,
    sector4 = sector4,
    sector5 = sector5
)
