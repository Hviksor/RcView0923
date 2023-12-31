package com.example.rcview.task

import android.os.Looper
import android.os.Handler
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future

private val executorService = Executors.newCachedThreadPool()
private val handle = Handler(Looper.getMainLooper())

class SimpleTask<T>(
    private val callable: Callable<T>
) : Task<T> {
    private val future: Future<*>
    private var result: Result<T> = PendingResult<T>()

    init {
        future = executorService.submit {
            result = try {
                SuccessResult(callable.call())
            } catch (e: Throwable) {
                ErrorResult(e)
            }
            notifyListeners()
        }
    }

    private var valueCallback: Callback<T>? = null
    private var errorCallback: Callback<Throwable>? = null

    override fun onSuccess(callback: Callback<T>): Task<T> {
        valueCallback = callback
        notifyListeners()
        return this
    }

    override fun onError(callback: Callback<Throwable>): Task<T> {
        errorCallback = callback
        notifyListeners()
        return this
    }

    override fun cancel() {
        clear()
        future.cancel(true)
    }

    override fun await(): T {
        future.get()
        val result = result
        if (result is SuccessResult) return result.data
        else throw (result as ErrorResult).error
    }

    private fun notifyListeners() {
        val result = result
        val valueCallback = valueCallback
        val errorCallback = errorCallback
        if (result is SuccessResult && valueCallback != null) {
            valueCallback.invoke(result.data)
            clear()
        }
        if (result is ErrorResult && errorCallback != null) {
            errorCallback.invoke(result.error)
            clear()
        }

    }

    private fun clear() {
        valueCallback = null
        errorCallback = null
    }
}