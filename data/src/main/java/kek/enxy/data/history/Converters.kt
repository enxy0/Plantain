package kek.enxy.data.history

import kek.enxy.data.db.history.model.HistoryEntity
import kek.enxy.data.dumps.toDomain
import kek.enxy.data.dumps.toEntity
import kek.enxy.data.history.model.History

fun HistoryEntity.toDomain() = History(
    timestamp = timestamp,
    action = action,
    dump = dump.toDomain()
)

fun History.toEntity() = HistoryEntity(
    timestamp = timestamp,
    action = action,
    dump = dump.toEntity()
)
