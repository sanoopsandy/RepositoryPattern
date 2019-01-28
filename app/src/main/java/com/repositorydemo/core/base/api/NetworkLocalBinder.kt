package com.repositorydemo.core.base.api

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import com.repositorydemo.core.utils.failure
import com.repositorydemo.core.utils.loading
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class NetworkLocalBinder<T, V> {
    // Always load the data from DB initially
    // Fetch the data from network and add it to the resource

    private val result = MediatorLiveData<DataResult<T>>()

    //Primary constructor
    init {
        result.loading(true)
        val dbSource = this.loadFromDb()
        result.addSource(dbSource) {
            result.removeSource(dbSource)
            fetchFromNetwork(dbSource)
        }
    }

    /**
     * This method fetches the data from remote service and saves it to local db
     *
     * @param dbSource - Database source
     */
    private fun fetchFromNetwork(dbSource: LiveData<T>) {
        result.addSource(dbSource) { newData ->
            newData?.let { result.value = DataResult.success(it) }
        }
        createCall().enqueue(object : Callback<V> {
            override fun onResponse(call: Call<V>, response: Response<V>) {
                result.removeSource(dbSource)
                saveResultToLocal(response.body())
            }

            override fun onFailure(call: Call<V>, t: Throwable) {
                result.removeSource(dbSource)
                result.addSource(
                    dbSource
                ) { newData ->
                    result.value = DataResult.failure(t)
                }
            }
        })
    }

    //Save the api result to local db
    private fun saveResultToLocal(response: V?) {
        GlobalScope.launch {
            response?.let {
                saveCallResult(it)
                result.addSource(loadFromDb()) { newData ->
                    newData?.let { data ->
                        result.postValue(DataResult.success(data))
                    }
                }
            } ?: result.failure(Throwable("Could not fetch result"))
        }
    }


    @WorkerThread
    protected abstract fun saveCallResult(item: V)

    @MainThread
    private fun shouldFetch(): Boolean {
        return true
    }

    @MainThread
    protected abstract fun loadFromDb(): LiveData<T>

    @MainThread
    protected abstract fun createCall(): Call<V>

    fun getAsLiveData(): LiveData<DataResult<T>> {
        return result
    }

}