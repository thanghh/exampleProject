package com.example.domain.model

data class State(var status: Status, var error: ErrorObject? =null) {
    companion object {
        fun loading() = State(Status.LOADING, null)
        fun success() = State(Status.SUCCESS, null)
        fun error(throwable: Throwable) = State(Status.ERROR, ErrorObject.getError(throwable))
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }

        val state = other as State?

        if (status != state?.status) {
            return false
        }

        return if (error != null) error == state.error else state.error == null
    }

    override fun hashCode(): Int {
        val result = status.hashCode()
        return 31 * result + if (error != null) error!!.hashCode() else 0
    }

    override fun toString(): String {
        return "status: $status, message: ${error?.getMessage()}"
    }
}