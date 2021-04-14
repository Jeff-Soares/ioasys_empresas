package br.com.ioasys.empresas.presentation

class FavoriteState<T>(
    val data: T? = null,
    val error: Throwable? = null,
    val state: State
) {
    companion object {
        fun <T> successAdd(data: T) =
            FavoriteState<T>(data = data, state = State.SUCCESS_ADD)
        fun <T> successRemoved(data: T) =
            FavoriteState<T>(data = data, state = State.SUCCESS_REMOVED)
        fun <T> error(error: Throwable) =
            FavoriteState<T>(error = error, state = State.ERROR)
    }

    enum class State {
        SUCCESS_ADD, SUCCESS_REMOVED, ERROR
    }
}