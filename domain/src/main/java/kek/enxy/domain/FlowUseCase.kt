package kek.enxy.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

/**
 * Executes business logic in its execute method and keep posting updates to the result as
 * [Result<R>].
 * Handling an exception (emit [Result.failure] to the result) is the subclasses's responsibility.
 */
interface FlowUseCase<in P, R> {
    operator fun invoke(parameter: P): Flow<Result<R>> = execute(parameter)
        .catch { e -> emit(Result.failure(Exception(e))) }
        .flowOn(dispatcher())

    fun execute(parameter: P): Flow<Result<R>>

    fun dispatcher(): CoroutineDispatcher = Dispatchers.IO
}
