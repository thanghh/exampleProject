package com.example.domain.model

data class Resource<out T>(val status: Status, val data: T?, val error: ErrorObject?) {
    val state: State =
        State(status, error)
    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }

        val resource: Resource<*>? = other as Resource<*>?

        return if (resource?.state == this.state && data != null) data == resource.data else resource?.data == null
    }

    override fun hashCode(): Int {
        var result = state.hashCode()
        result = 31 * result + (data?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Resource{" +
                "status=" + state.status +
                ", status='" + state.error?.getStatus() + '\''.toString() +
                ", message='" + state.error?.getMessage() + '\''.toString() +
                '}'.toString()
    }

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(errorObject: ErrorObject?, data: T? = null): Resource<T> {
            return Resource(
                Status.ERROR,
                data,
                errorObject
            )
        }

        fun <T> loading(): Resource<T> {
            return Resource(
                Status.LOADING,
                null,
                null
            )
        }
    }
}