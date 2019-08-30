/*
 * Copyright 2019 S24 S.p.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gianlucalimbi.blueprint

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import com.gianlucalimbi.blueprint.Resource.Status.LOADING
import com.gianlucalimbi.blueprint.Resource.Status.SUCCESS
import com.gianlucalimbi.blueprint.observer.DisposableEventObserver
import com.gianlucalimbi.blueprint.observer.DisposableEventObserverBuilder
import com.gianlucalimbi.blueprint.observer.DisposableObserver
import com.gianlucalimbi.blueprint.observer.DisposableObserverBuilder
import com.gianlucalimbi.blueprint.observer.DisposableResourceObserver
import com.gianlucalimbi.blueprint.observer.DisposableResourceObserverBuilder
import com.gianlucalimbi.blueprint.observer.EventObserver
import com.gianlucalimbi.blueprint.observer.ResourceObserver
import com.gianlucalimbi.blueprint.observer.ResourceObserverBuilder
import com.gianlucalimbi.blueprint.observer.disposableEventObserver
import com.gianlucalimbi.blueprint.observer.disposableObserver
import com.gianlucalimbi.blueprint.observer.disposableResourceObserver
import com.gianlucalimbi.blueprint.observer.resourceObserver

/**
 * Attach the given [Observer] to this LiveData.
 * The Observer will be removed the first time a non-null value is received.
 *
 * You can change the behaviour by passing a [DisposableObserver] instead of a regular [Observer] and overriding
 * [DisposableObserver.disposeWhen] and [DisposableObserver.disposeAction].
 *
 * @param lifecycleOwner The lifecycle owner that will be used to observe the changes, or null.
 * @param observer The Observer that will be attached.
 *
 * @see DisposableObserver
 */
fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner? = null, observer: Observer<T>) {
  val disposableObserver = if (observer is DisposableObserver<T>) {
    observer
  } else {
    disposableObserver(this, observer)
  }

  if (lifecycleOwner == null) {
    observeForever(disposableObserver)
  } else {
    observe(lifecycleOwner, disposableObserver)
  }
}

/**
 * Attach the given [DisposableObserver] to this LiveData.
 * The Observer will be removed the first time a non-null value is received.
 *
 * You can change the behaviour by overriding [DisposableObserver.disposeWhen] and [DisposableObserver.disposeAction].
 *
 * @param lifecycleOwner The lifecycle owner that will be used to observe the changes, or null.
 * @param block The builder for the Observer that will be attached.
 *
 * @see DisposableObserver
 */
fun <T> LiveData<T>.observeOnce(
  lifecycleOwner: LifecycleOwner? = null,
  block: DisposableObserverBuilder<T>.() -> Unit
) {
  observeOnce(lifecycleOwner, disposableObserver(this, block))
}

/**
 * Attach the given [ResourceObserver] to this LiveData.
 * The Observer will be removed the first time a [SUCCESS] value is received.
 *
 * You can change the behaviour by passing a [DisposableResourceObserver] instead of a regular [ResourceObserver]
 * and overriding [DisposableResourceObserver.disposeWhen] and [DisposableResourceObserver.disposeAction].
 *
 * @param lifecycleOwner The lifecycle owner that will be used to observe the changes, or null.
 * @param observer The ResourceObserver that will be attached.
 *
 * @see DisposableResourceObserver
 */
fun <T> LiveData<Resource<T>>.observeResourceOnce(
  lifecycleOwner: LifecycleOwner? = null,
  observer: ResourceObserver<T>
) {
  val disposableResourceObserver = if (observer is DisposableResourceObserver<T>) {
    observer
  } else {
    disposableResourceObserver(this, observer)
  }

  if (lifecycleOwner == null) {
    observeForever(disposableResourceObserver)
  } else {
    observe(lifecycleOwner, disposableResourceObserver)
  }
}

/**
 * Attach the given [DisposableResourceObserver] to this LiveData.
 * The Observer will be removed the first time a [SUCCESS] value is received.
 *
 * You can change the behaviour by overriding [DisposableResourceObserver.disposeWhen]
 * and [DisposableResourceObserver.disposeAction].
 *
 * @param lifecycleOwner The lifecycle owner that will be used to observe the changes, or null.
 * @param block The builder for the ResourceObserver that will be attached.
 *
 * @see DisposableObserver
 */
fun <T> LiveData<Resource<T>>.observeResourceOnce(
  lifecycleOwner: LifecycleOwner? = null,
  block: DisposableResourceObserverBuilder<T>.() -> Unit
) {
  observeResourceOnce(lifecycleOwner, disposableResourceObserver(this, block))
}

/**
 * Attach the given [EventObserver] to this LiveData.
 * The Observer will be removed the first time a not-consumed [Event] is received.
 *
 * You can change the behaviour by passing a [DisposableEventObserver] instead of a regular [EventObserver]
 * and overriding [DisposableEventObserver.disposeWhen] and [DisposableEventObserver.disposeAction].
 *
 * @param lifecycleOwner The lifecycle owner that will be used to observe the changes, or null.
 * @param observer The EventObserver that will be attached.
 *
 * @see DisposableEventObserver
 */
fun <T> LiveData<Event<T>>.observeEventOnce(
  lifecycleOwner: LifecycleOwner? = null,
  observer: EventObserver<T>
) {
  val disposableEventObserver = if (observer is DisposableEventObserver<T>) {
    observer
  } else {
    disposableEventObserver(this, observer)
  }

  if (lifecycleOwner == null) {
    observeForever(disposableEventObserver)
  } else {
    observe(lifecycleOwner, disposableEventObserver)
  }
}

/**
 * Attach the given [DisposableEventObserver] to this LiveData.
 * The Observer will be removed the first time a not-consumed [Event] is received.
 *
 * You can change the behaviour by overriding [DisposableEventObserver.disposeWhen]
 * and [DisposableEventObserver.disposeAction].
 *
 * @param lifecycleOwner The lifecycle owner that will be used to observe the changes, or null.
 * @param block The builder for the EventObserver that will be attached.
 *
 * @see DisposableObserver
 */
fun <T> LiveData<Event<T>>.observeEventOnce(
  lifecycleOwner: LifecycleOwner? = null,
  block: DisposableEventObserverBuilder<T>.() -> Unit
) {
  observeEventOnce(lifecycleOwner, disposableEventObserver(this, block))
}

/**
 * Forces this LiveData to update, attaching an Observer to it. Immediately removes the Observer afterwards.
 * This is needed because MediatorLiveData does not get triggered without an active Observer.
 * Note that the Observer is actually removed only when the LiveData receives an update.
 */
fun <T> LiveData<T>.compute() {
  observeOnce { disposeWhen { true } }
}

/**
 * Transforms this LiveData using the provided transformation function.
 * Shorthand for [Transformations.map].
 *
 * @param block The transformation function that will be applied to the data.
 *
 * @return The transformed LiveData.
 *
 * @see Transformations.map
 */
fun <T, R> LiveData<T>.map(block: (T) -> R): LiveData<R> {
  return Transformations.map(this, block)
}

/**
 * Transforms the Resource contained in this LiveData using the provided transformation function.
 * The transformation function will be applied only to the data in the [SUCCESS] and [LOADING] state, if present.
 *
 * @param block The transformation function that will be applied to the resource data.
 *
 * @return The transformed LiveData.
 *
 * @see LiveData.map
 */
fun <T, R> LiveData<Resource<T>>.mapResource(block: (T) -> R): LiveData<Resource<R>> {
  return map { resource ->
    when (resource) {
      is SuccessResource -> Resource.success(block.invoke(resource.data))
      is ErrorResource -> Resource.error(resource.error)
      is LoadingResource -> Resource.loading(resource.data?.let { block.invoke(it) })
    }
  }
}

/**
 * Intercepts the changes of this LiveData and forwards changes to the provided interceptor [Observer].
 * Note that a new LiveData will be returned and must be used in order for the interceptor Observer to receive events.
 * The interceptor Observer will receive events only if the returned LiveData will be observed.
 *
 * @param observer The Observer that will receive the events.
 *
 * @return The LiveData that wraps the interceptor Observer.
 */
fun <T> LiveData<T>.intercept(observer: Observer<T>): MediatorLiveData<T> {
  val liveData = this
  return MediatorLiveData<T>().apply {
    addSource(liveData) {
      observer.onChanged(it) // Update the interceptor observer.
      value = it // Send the changes to the new MediatorLiveData result.
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
fun <T> LiveData<T>.intercept(block: (T?) -> Unit): MediatorLiveData<T> {
  return intercept(Observer { block.invoke(it) })
}

/**
 * Intercepts the changes of this Resource LiveData and forwards changes to the provided interceptor [ResourceObserver].
 * Note that a new LiveData will be returned and must be used in order for the interceptor Observer to receive events.
 * The interceptor Observer will receive events only if the returned LiveData will be observed.
 *
 * @param observer The ResourceObserver that will receive the events.
 *
 * @return The LiveData that wraps the interceptor Observer.
 */
fun <T> LiveData<Resource<T>>.interceptResource(observer: ResourceObserver<T>): MediatorLiveData<Resource<T>> {
  return intercept(observer)
}

/**
 * Intercepts the changes of this Resource LiveData and forwards changes to the provided interceptor [ResourceObserver].
 * Note that a new LiveData will be returned and must be used in order for the interceptor observer to receive events.
 * The interceptor Observer will receive events only if the returned LiveData will be observed.
 *
 * @param block The builder for the ResourceObserver that will receive the events.
 *
 * @return The LiveData that wraps the interceptor Observer.
 */
fun <T> LiveData<Resource<T>>.interceptResource(
  block: ResourceObserverBuilder<T>.() -> Unit
): MediatorLiveData<Resource<T>> {
  return intercept(resourceObserver(block))
}

/**
 * Shorthand function that creates a new [LiveData] with the provided [data].
 *
 * @param data The data that will be set in the [LiveData].
 *
 * @return The newly created LiveData that contains the information.
 */
fun <T> liveDataOf(data: T): LiveData<T> = mutableLiveDataOf(data)

/**
 * Shorthand function that creates a new [MutableLiveData] with the provided [data].
 *
 * @param data The data that will be set in the [MutableLiveData].
 *
 * @return The newly created LiveData that contains the information.
 */
fun <T> mutableLiveDataOf(data: T): MutableLiveData<T> = MutableLiveData<T>().apply {
  postValue(data)
}
