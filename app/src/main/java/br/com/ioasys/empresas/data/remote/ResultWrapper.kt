package br.com.ioasys.empresas.data.remote

sealed class ResultWrapper<out T>(
    val data: T? = null,
    val error: Throwable? = null
) {
    class Success<T>(data: T) : ResultWrapper<T>(data)
    class Failure(error: Throwable) : ResultWrapper<Nothing>(error = error)

    suspend infix fun <R> then(f: suspend (ResultWrapper<T>) -> ResultWrapper<R>): ResultWrapper<R> {
        return when (this) {
            is Success -> f(this)
            is Failure -> this
        }
    }

    companion object {
        fun <T> put(data: T?) =
            if (data != null) Success(data) else Failure(Throwable("Null value"))
    }
}
