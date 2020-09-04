package com.accenture.lidlpoc.ui.common

sealed class LidlViewResult<out T, out K> {
    object Loading : LidlViewResult<Nothing, Nothing>()
    class Error<K>(val error: K) : LidlViewResult<Nothing, K>()
    class Success<T>(val data: T?) : LidlViewResult<T, Nothing>()


}