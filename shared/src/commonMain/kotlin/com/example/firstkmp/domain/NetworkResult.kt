package com.example.firstkmp.domain

sealed class NetworkResult<out T>{

    data class Success<out T>(val data : T) : NetworkResult<T>()

    data class Error(val message : String,val exception : Exception? = null) : NetworkResult<Nothing>()

    data object Loading : NetworkResult<Nothing>()

}