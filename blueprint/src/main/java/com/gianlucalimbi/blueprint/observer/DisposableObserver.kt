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
import androidx.lifecycle.Observer

open class DisposableObserver<T>(
  private val liveData: LiveData<T>,
  private val disposeWhen: ((T?) -> Boolean)?,
  private val disposeAction: ((LiveData<T>, Observer<T>) -> Unit)?,
  private val onChanged: ((T?) -> Unit)?
) : Observer<T> {

  override fun onChanged(data: T?) {
    onChanged?.invoke(data)

    if (disposeWhen?.invoke(data) == true) {
      disposeAction?.invoke(liveData, this)
    }
  }

}
