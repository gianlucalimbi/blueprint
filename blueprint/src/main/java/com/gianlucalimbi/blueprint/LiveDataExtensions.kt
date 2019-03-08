package com.gianlucalimbi.blueprint

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

/**
 * Intercepts the changes of this LiveData and forwards changes to the provided interceptor [Observer].
 * Note that a new LiveData will be returned and must be used in order for the interceptor observer to receive events.
 * The interceptor observer will receive events only if the returned LiveData will be observed.
 *
 * @param observer The observer that will receive the events.
 *
 * @return The LiveData that wraps the interceptor observer.
 */
fun <T> LiveData<T>.intercept(observer: Observer<T>): LiveData<T> {
  val liveData = this
  return MediatorLiveData<T>().apply {
    addSource(liveData) {
      value = it // Send the changes to the new MediatorLiveData result.
      observer.onChanged(it) // Update the interceptor observer.
    }
  }
}

/**
 * Intercepts the changes of this LiveData and forwards changes to the provided interceptor callback.
 * Note that a new LiveData will be returned and must be used in order for the interceptor callback to receive events.
 * The interceptor callback will receive events only if the returned LiveData will be observed.
 *
 * @param block The callback that will receive the events.
 *
 * @return The LiveData that wraps the interceptor callback.
 */
fun <T> LiveData<T>.intercept(block: (T) -> Unit): LiveData<T> {
  return intercept(Observer(block))
}

/**
 * Intercepts the changes of this Resource LiveData and forwards changes to the provided interceptor [ResourceObserver].
 * Note that a new LiveData will be returned and must be used in order for the interceptor observer to receive events.
 * The interceptor observer will receive events only if the returned LiveData will be observed.
 *
 * @param observer The resource observer that will receive the events.
 *
 * @return The LiveData that wraps the interceptor observer.
 */
fun <T> LiveData<Resource<T>>.interceptResource(observer: ResourceObserver<T>): LiveData<Resource<T>> {
  return intercept(observer)
}

/**
 * Intercepts the changes of this Resource LiveData and forwards changes to the provided interceptor [ResourceObserver].
 * Note that a new LiveData will be returned and must be used in order for the interceptor observer to receive events.
 * The interceptor observer will receive events only if the returned LiveData will be observed.
 *
 * @param block The resource observer that will receive the events.
 *
 * @return The LiveData that wraps the interceptor observer.
 */
fun <T> LiveData<Resource<T>>.interceptResource(block: ResourceObserverBuilder<T>.() -> Unit): LiveData<Resource<T>> {
  return interceptResource(resourceObserver(block))
}
