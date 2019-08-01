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

import androidx.lifecycle.Observer
import com.gianlucalimbi.blueprint.ErrorResource
import com.gianlucalimbi.blueprint.LoadingResource
import com.gianlucalimbi.blueprint.Resource
import com.gianlucalimbi.blueprint.SuccessResource

open class ResourceObserver<T>(
  private val onSuccess: ((T) -> Unit)?,
  private val onError: ((Throwable) -> Unit)?,
  private val onLoading: ((T?) -> Unit)?,
  private val onChanged: ((Resource<T>?) -> Unit)?
) : Observer<Resource<T>> {

  override fun onChanged(resource: Resource<T>?) {
    onChanged?.invoke(resource)

    resource?.let {
      when (it) {
        is SuccessResource -> onSuccess?.invoke(it.data)
        is ErrorResource -> onError?.invoke(it.error)
        is LoadingResource -> onLoading?.invoke(it.data)
      }
    }
  }

}
