package iwsbrazil.io.artmuseum.extensions

import androidx.lifecycle.*

fun <T, R> LiveData<T>.map(mapper: (T?) -> R?): LiveData<R> = Transformations.map(this, mapper)

fun <T, R> LiveData<T>.switchMap(mapper: (T?) -> LiveData<R>): LiveData<R> = Transformations.switchMap(this, mapper)

fun <T> LiveData<T>.filter(filter: (T) -> Boolean): LiveData<T> {
    val filtered = MediatorLiveData<T>()
    filtered.addSource(this) {
        if (filter(it)) {
            filtered.postValue(it)
        }
    }
    return filtered
}

fun <T, R, S> LiveData<T>.zipWith(other: LiveData<R>, zipper: (T?, R?) -> S?): LiveData<S> {
    val result = MediatorLiveData<S>()
    result.addSource(this) { first ->
        result.addSource(other) { second ->
            result.postValue(zipper(first, second))
            result.removeSource(other)
        }
    }
    return result
}

fun <T, R, S> LiveData<T>.combineWith(other: LiveData<R>, combiner: (T?, R?) -> S?): LiveData<S> {
    val result = MediatorLiveData<S>()
    result.addSource(this) {
        result.postValue(combiner(it, other.value))
    }
    result.addSource(other) {
        result.postValue(combiner(this.value, it))
    }
    return result
}

fun <T, R> LiveData<T>.eachCombineWith(other: LiveData<R>, combiner: (T?, R?) -> Unit): LiveData<T> {
    val result = MediatorLiveData<T>()
    result.addSource(this) {
        result.postValue(it)
        combiner(it, other.value)
    }
    result.addSource(other) {
        combiner(this.value, it)
    }
    return result
}

fun <T> mutableLiveDataOf(initialValue: T) = MutableLiveData<T>().apply {
    value = initialValue
}

fun <T> createLiveData(block: MutableLiveData<T>.() -> Unit): LiveData<T> = MutableLiveData<T>().apply(block)

fun <T> LiveData<T>.observeOnce(owner: LifecycleOwner, observer: Observer<T>) {
    val internalObserver = object : Observer<T> {
        override fun onChanged(t: T) {
            observer.onChanged(t)
            this@observeOnce.removeObserver(this)
        }

    }
    this.observe(owner, internalObserver)
}

fun <T: Any?> MutableLiveData<T>.default(initialValue: T) = apply { value = initialValue }