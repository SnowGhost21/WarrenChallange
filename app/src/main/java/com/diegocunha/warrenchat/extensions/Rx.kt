package iwsbrazil.io.artmuseum.extensions

import android.util.Log
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Single

fun <T> Single<T>.asLiveData() = createLiveData<T> {
    this@asLiveData.subscribe({
        postValue(it)
    }, {
        Log.e("LIVEDATA_ERROR", "$it")
        postValue(null)
    })
}

fun <T> Observable<T>.asLiveData(strategy: BackpressureStrategy = BackpressureStrategy.LATEST) =
        createLiveData<T> {
            this@asLiveData.toFlowable(strategy).subscribe({
                postValue(it)
            }, {
                Log.e("LIVEDATA_ERROR", "$it")
                postValue(null)
            })
        }

fun <T> Single<T>.subscribe(observer: Observer<T>) = this.subscribe({
    observer.onNext(it)
}, {
    observer.onError(it)
})