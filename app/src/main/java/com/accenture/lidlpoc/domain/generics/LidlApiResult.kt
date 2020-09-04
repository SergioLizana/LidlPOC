package com.accenture.lidlpoc.domain.generics

sealed class LidlApiResult<out T, out K> {
    class Error<K>(val error: K) : LidlApiResult<Nothing, K>()
    class Success<T>(val success: T) : LidlApiResult<T, Nothing>()
}