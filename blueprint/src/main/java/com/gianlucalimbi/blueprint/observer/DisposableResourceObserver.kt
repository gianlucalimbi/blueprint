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

open class DisposableResourceObserver<T>(
  private val liveData: LiveData<Resource<T>>,
  private val disposeWhen: ((Resource<T>?) -> Boolean)?,
  private val disposeAction: ((LiveData<Resource<T>>, ResourceObserver<T>) -> Unit)?,
  onSuccess: ((T) -> Unit)?,
  onError: ((Throwable) -> Unit)?,
  onLoading: ((T?) -> Unit)?,
  onChanged: ((Resource<T>?) -> Unit)?
) : ResourceObserver<T>(onSuccess, onError, onLoading, onChanged) {

  override fun onChanged(resource: Resource<T>?) {
    super.onChanged(resource)

    if (disposeWhen?.invoke(resource) == true) {
      disposeAction?.invoke(liveData, this)
    }
  }

}
