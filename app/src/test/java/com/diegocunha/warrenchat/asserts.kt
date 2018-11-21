package com.diegocunha.warrenchat

import androidx.lifecycle.LiveData
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Assert


fun <T> assertLiveDataEquals(expected: T, liveData: LiveData<T>, doAfterSubscribe: (() -> Unit)? = null) {
    var value: T? = null
    liveData.observeForever { value = it }
    doAfterSubscribe?.invoke()
    Assert.assertEquals(expected, value)
}

fun <T> assertLiveDataNull(liveData: LiveData<T>, doAfterSubscribe: (() -> Unit)? = null) {
    var value: T? = null
    liveData.observeForever { value = it }
    doAfterSubscribe?.invoke()
    Assert.assertEquals(null, value)
}

fun <T> assertObservableEquals(expected: T, observable: Observable<T>, doAfterSubscribe: (() -> Unit)? = null) {
    var value: T? = null
    observable.subscribe { value = it }
    doAfterSubscribe?.invoke()
    Assert.assertEquals(expected, value)
}

fun <T> assertObservableError(observable: Observable<T>, doAfterSubscribe: (() -> Unit)? = null) {
    var error: Throwable? = null
    observable.subscribe({

    }, {
        error = it
    })
    doAfterSubscribe?.invoke()
    Assert.assertNotNull(error)
}

fun <T> assertSingleEquals(expected: T, single: Single<T>) {
    var value: T? = null
    single.subscribe { result -> value = result }
    Assert.assertEquals(expected, value)
}