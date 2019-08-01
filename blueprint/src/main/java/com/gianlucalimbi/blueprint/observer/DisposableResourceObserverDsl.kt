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

package com.gianlucalimbi.blueprint.observer

import androidx.lifecycle.LiveData
import com.gianlucalimbi.blueprint.Resource

@DslMarker
internal annotation class DisposableResourceObserverDsl

@DisposableResourceObserverDsl
class DisposableResourceObserverBuilder<T>(
  private val liveData: LiveData<Resource<T>>
) {

  private var disposeWhen: ((Resource<T>?) -> Boolean)? = { resource ->
    resource != null && resource.status != Resource.Status.LOADING
  }

  private var disposeAction: ((LiveData<Resource<T>>, ResourceObserver<T>) -> Unit)? = { liveData, observer ->
    liveData.removeObserver(observer)
  }

  private var onSuccess: ((T) -> Unit)? = null
  private var onError: ((Throwable) -> Unit)? = null
  private var onLoading: ((T?) -> Unit)? = null
  private var onChanged: ((Resource<T>?) -> Unit)? = null

  fun disposeWhen(block: (Resource<T>?) -> Boolean) {
    disposeWhen = block
  }

  fun disposeAction(block: (LiveData<Resource<T>>, ResourceObserver<T>) -> Unit) {
    disposeAction = block
  }

  fun onSuccess(block: (T) -> Unit) {
    onSuccess = block
  }

  fun onError(block: (Throwable) -> Unit) {
    onError = block
  }

  fun onLoading(block: (T?) -> Unit) {
    onLoading = block
  }

  fun onChanged(block: (Resource<T>?) -> Unit) {
    onChanged = block
  }

  fun build() = DisposableResourceObserver(
      liveData,
      disposeWhen,
      disposeAction,
      onSuccess,
      onError,
      onLoading,
      onChanged
  )

}

fun <T> disposableResourceObserver(
  liveData: LiveData<Resource<T>>,
  block: DisposableResourceObserverBuilder<T>.() -> Unit
): DisposableResourceObserver<T> {
  return DisposableResourceObserverBuilder(liveData).apply(block).build()
}

fun <T> disposableResourceObserver(
  liveData: LiveData<Resource<T>>,
  observer: ResourceObserver<T>
): DisposableResourceObserver<T> {
  return DisposableResourceObserverBuilder(liveData).apply {
    onChanged { observer.onChanged(it) }
  }.build()
}
