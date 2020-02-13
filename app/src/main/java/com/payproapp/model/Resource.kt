package com.payproapp.model

class Resource<T> private constructor(val status: Status, val data: T?, val message: String?) {

    val isSuccessful: Boolean
        get() = status === Status.SUCCESS

    val isLoading: Boolean
        get() = status === Status.LOADING


    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return loading(data, null)
        }

        fun <T> loading(data: T?, message: String?): Resource<T> {
            return Resource(Status.LOADING, data, message)
        }
    }
}
