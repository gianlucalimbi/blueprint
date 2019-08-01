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

import com.gianlucalimbi.blueprint.Resource

@DslMarker
internal annotation class ResourceObserverDsl

@ResourceObserverDsl
class ResourceObserverBuilder<T> {

  private var onSuccess: ((T) -> Unit)? = null
  private var onError: ((Throwable) -> Unit)? = null
  private var onLoading: ((T?) -> Unit)? = null
  private var onChanged: ((Resource<T>?) -> Unit)? = null

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

  fun build() = ResourceObserver(onSuccess, onError, onLoading, onChanged)

}

fun <T> resourceObserver(block: ResourceObserverBuilder<T>.() -> Unit): ResourceObserver<T> {
  return ResourceObserverBuilder<T>().apply(block).build()
}
