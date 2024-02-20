package com.salanevich.githubapp.data.network

import retrofit2.Response

fun <T> Response<T>.proceed(): T {
    if (this.isSuccessful && this.body() != null) {
        return body()!!
    } else {
        val errorMessage = "${code()} ${errorBody()?.string()}"
        throw Exception(errorMessage)
    }
}