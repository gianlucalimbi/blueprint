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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.gianlucalimbi.blueprint.Resource.Status.SUCCESS
import com.gianlucalimbi.blueprint.observer.DisposableObserver
import com.gianlucalimbi.blueprint.observer.DisposableObserverBuilder
import com.gianlucalimbi.blueprint.observer.DisposableResourceObserver
import com.gianlucalimbi.blueprint.observer.DisposableResourceObserverBuilder
import com.gianlucalimbi.blueprint.observer.ResourceObserver
import com.gianlucalimbi.blueprint.observer.ResourceObserverBuilder
import com.gianlucalimbi.blueprint.observer.disposableObserver
import com.gianlucalimbi.blueprint.observer.disposableResourceObserver
import com.gianlucalimbi.blueprint.observer.resourceObserver

/**
 * Adds the given Resource LiveData as a source and observes its changed when this [MediatorLiveData] is active.
 *
 * @param source The Resource LiveData that will be added as a source.
 * @param observer The ResourceObserver that will receive the events.
 *
 * @see MediatorLiveData.addSource
 */
fun <T, S> MediatorLiveData<Resource<T>>.addResourceSource(
  source: LiveData<Resource<S>>,
  observer: ResourceObserver<S>
) {
  addSource(source, observer)
}

/**
 * Adds the given Resource LiveData as a source and observes its changed when this [MediatorLiveData] is active.
 *
 * @param source The Resource LiveData that will be added as a source.
 * @param block The builder for the ResourceObserver that will receive the events.
 *
 * @see MediatorLiveData.addSource
 */
fun <T, S> MediatorLiveData<Resource<T>>.addResourceSource(
  source: LiveData<Resource<S>>,
  block: ResourceObserverBuilder<S>.() -> Unit
) {
  addSource(source, resourceObserver(block))
}

/**
 * Adds the given LiveData as a source and observes its changed when this [MediatorLiveData] is active.
 * The source will be removed the first time a non-null value is received.
 *
 * You can change the behaviour by passing a [DisposableObserver] instead of a regular [Observer] and overriding
 * [DisposableObserver.disposeWhen] and [DisposableObserver.disposeAction].
 *
 * @param source The LiveData that will be added as a source.
 * @param observer The Observer that will receive the events.
 *
 * @see LiveData.observeOnce
 */
fun <T, S> MediatorLiveData<T>.addSourceOnce(source: LiveData<S>, observer: Observer<S>) {
  val disposableObserver = if (observer is DisposableObserver<S>) {
    observer
  } else {
    disposableObserver(source) {

      onChanged {
        observer.onChanged(it)
      }

      disposeAction { source, _ ->
        removeSource(source)
      }

    }
  }

  addSource(source, disposableObserver)
}

/**
 * Adds the given LiveData as a source and observes its changed when this [MediatorLiveData] is active.
 * The source will be removed the first time a non-null value is received.
 *
 * You can change the behaviour by overriding [DisposableObserver.disposeWhen] and [DisposableObserver.disposeAction].
 *
 * @param source The LiveData that will be added as a source.
 * @param block The builder for the Observer that will receive the events.
 *
 * @see LiveData.observeOnce
 */
fun <T, S> MediatorLiveData<T>.addSourceOnce(source: LiveData<S>, block: DisposableObserverBuilder<S>.() -> Unit) {
  addSourceOnce(source, disposableObserver(source, block))
}

/**
 * Adds the given Resource LiveData as a source and observes its changed when this [MediatorLiveData] is active.
 * The source will be removed the first time a [SUCCESS] value is received.
 *
 * You can change the behaviour by passing a [DisposableResourceObserver] instead of a regular [ResourceObserver]
 * and overriding [DisposableResourceObserver.disposeWhen] and [DisposableResourceObserver.disposeAction].
 *
 * @param source The Resource LiveData that will be added as a source.
 * @param observer The ResourceObserver that will receive the events.
 *
 * @see LiveData<Resource>.observeResourceOnce
 */
fun <T, S> MediatorLiveData<Resource<T>>.addResourceSourceOnce(
  source: LiveData<Resource<S>>,
  observer: ResourceObserver<S>
) {
  val disposableResourceObserver = if (observer is DisposableResourceObserver<S>) {
    observer
  } else {
    disposableResourceObserver(source) {

      onChanged {
        observer.onChanged(it)
      }

      disposeAction { source, _ ->
        removeSource(source)
      }

    }
  }

  addSource(source, disposableResourceObserver)
}

/**
 * Adds the given Resource LiveData as a source and observes its changed when this [MediatorLiveData] is active.
 * The source will be removed the first time a [SUCCESS] value is received.
 *
 * You can change the behaviour by overriding [DisposableResourceObserver.disposeWhen]
 * and [DisposableResourceObserver.disposeAction].
 *
 * @param source The Resource LiveData that will be added as a source.
 * @param block The builder for the ResourceObserver that will receive the events.
 *
 * @see LiveData<Resource>.observeResourceOnce
 */
fun <T, S> MediatorLiveData<Resource<T>>.addResourceSourceOnce(
  source: LiveData<Resource<S>>,
  block: DisposableResourceObserverBuilder<S>.() -> Unit
) {
  addResourceSourceOnce(source, disposableResourceObserver(source, block))
}

/**
 * Shorthand function that creates a new [MediatorLiveData] with the provided [source] already attached.
 *
 * @param source The LiveData that will be added as a source.
 * @param observer The Observer that will receive the events.
 *
 * @return The newly created MediatorLiveData with the source attached.
 */
fun <T, S> mediatorLiveDataOf(
  source: LiveData<S>,
  observer: Observer<S>
): MediatorLiveData<T> = MediatorLiveData<T>().apply {
  addSource(source, observer)
}

/**
 * Shorthand function that creates a new [MediatorLiveData] with the provided [source] already attached.
 *
 * @param source The LiveData that will be added as a source.
 * @param block The callback that will receive the events.
 *
 * @return The newly created MediatorLiveData with the source attached.
 */
fun <T, S> mediatorLiveDataOf(
  source: LiveData<S>,
  block: (S) -> Unit
): MediatorLiveData<T> = MediatorLiveData<T>().apply {
  addSource(source, block)
}

/**
 * Shorthand function that creates a new Resource [MediatorLiveData] with the provided [source] already attached.
 *
 * @param source The Resource LiveData that will be added as a source.
 * @param observer The ResourceObserver that will receive the events.
 *
 * @return The newly created MediatorLiveData with the source attached.
 */
fun <T, S> resourceMediatorLiveDataOf(
  source: LiveData<Resource<S>>,
  observer: ResourceObserver<S>
): MediatorLiveData<T> = MediatorLiveData<T>().apply {
  addSource(source, observer)
}

/**
 * Shorthand function that creates a new Resource [MediatorLiveData] with the provided [source] already attached.
 *
 * @param source The Resource LiveData that will be added as a source.
 * @param block The builder for the ResourceObserver that will receive the events.
 *
 * @return The newly created MediatorLiveData with the source attached.
 */
fun <T, S> resourceMediatorLiveDataOf(
  source: LiveData<Resource<S>>,
  block: ResourceObserverBuilder<S>.() -> Unit
): MediatorLiveData<T> = MediatorLiveData<T>().apply {
  addSource(source, resourceObserver(block))
}
