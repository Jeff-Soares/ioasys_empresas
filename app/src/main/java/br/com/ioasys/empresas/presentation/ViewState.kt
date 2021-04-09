package br.com.ioasys.empresas.presentation

class ViewState<T>(
    val data: T? = null,
    val state: State,
    val isLoading: Boolean = false,
    val error: Throwable? = null
) {
    companion object {
        fun <T> loading(isLoading: Boolean) =
            ViewState<T>(isLoading = isLoading, state = State.LOADING)
        fun <T> success(data: T) =
            ViewState<T>(data = data, state = State.SUCCESS)
        fun <T> error(error: Throwable) =
            ViewState<T>(error = error, state = State.ERROR)
    }

    enum class State {
        LOADING, SUCCESS, ERROR
    }
}