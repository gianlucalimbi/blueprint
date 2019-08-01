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

sealed class Resource<T>(
  val status: Status,
  open val data: T? = null,
  open val error: Throwable? = null
) {

  companion object {

    @JvmStatic
    fun <T> success(data: T): Resource<T> = SuccessResource(data)

    @JvmStatic
    fun <T> error(error: Throwable): Resource<T> = ErrorResource(error)

    @JvmStatic
    fun <T> loading(data: T? = null): Resource<T> = LoadingResource(data)

  }

  override fun equals(other: Any?): Boolean {
    if (this === other) {
      return true
    }

    if (other == null || this::class != other::class) {
      return false
    }

    val resource = other as Resource<*>

    return status == resource.status &&
           data == resource.data &&
           error == resource.error
  }

  override fun toString() = "Resource { status=$status, data=$data, error=$error }"

  override fun hashCode(): Int {
    var result = 31 * status.hashCode()
    result = 31 * result + (data?.hashCode() ?: 0)
    result = 31 * result + (error?.hashCode() ?: 0)

    return result
  }

  enum class Status {
    SUCCESS,
    ERROR,
    LOADING,
  }

}

class SuccessResource<T>(
  override val data: T
) : Resource<T>(Status.SUCCESS, data = data, error = null)

class ErrorResource<T>(
  override val error: Throwable
) : Resource<T>(Status.ERROR, data = null, error = error)

class LoadingResource<T>(
  override val data: T?
) : Resource<T>(Status.LOADING, data = data, error = null)
