package com.gianlucalimbi.blueprint

import androidx.lifecycle.Observer

class ResourceObserver<T> internal constructor(
    private val onSuccess: (data: T?) -> Unit = { },
    private val onError: (error: Throwable?) -> Unit = { },
    private val onLoading: (data: T?) -> Unit = { },
    private val onChanged: (resource: Resource<T>?) -> Unit = { }
) : Observer<Resource<T>> {

  override fun onChanged(resource: Resource<T>?) {
    resource?.let {
      when (it.status) {
        Resource.Status.SUCCESS -> onSuccess.invoke(it.data)
        Resource.Status.ERROR -> onError.invoke(it.error)
        Resource.Status.LOADING -> onLoading.invoke(it.data)
      }
    }

    onChanged.invoke(resource)
  }

}
