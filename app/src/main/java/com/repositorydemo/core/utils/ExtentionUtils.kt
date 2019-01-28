package com.repositorydemo.core.utils

import android.app.Activity
import android.arch.lifecycle.MutableLiveData
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.repositorydemo.core.base.api.DataResult

/**
 * Function to push the loading status to the observing dataResult
 * */
fun <T> MutableLiveData<DataResult<T>>.loading(isLoading: Boolean) {
    this.value = DataResult.loading(isLoading)
}

/**
 * Function to handle loading logic and push the value forward
 * */
fun <T> MutableLiveData<DataResult<T>>.failure(e: Throwable) {
    this.value = DataResult.failure(e)
}

/**
 * Function to handle loading logic and push the value forward
 * */
fun <T> MutableLiveData<DataResult<T>>.success(t: T) {
    this.value = DataResult.success(t)
}

fun ProgressBar.visibilityToggle(visible: Boolean) {
    this.visibility = if (visible)
        View.VISIBLE
    else
        View.GONE
}

fun Activity.showTost(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}