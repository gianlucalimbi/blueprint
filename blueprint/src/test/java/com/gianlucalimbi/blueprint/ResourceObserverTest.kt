package com.gianlucalimbi.blueprint

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ResourceObserverTest {

  @Mock private lateinit var onSuccess: (data: Any) -> Unit
  @Mock private lateinit var onError: (error: Exception) -> Unit
  @Mock private lateinit var onLoading: (data: Any?) -> Unit
  @Mock private lateinit var onChanged: (resource: Resource<Any>?) -> Unit

  private lateinit var observer: ResourceObserver<Any>

  @Before
  fun setUp() {
    observer = ResourceObserver(onSuccess, onError, onLoading, onChanged)
  }

  @Test
  fun `When a success Resource is set, the onSuccess callback is invoked`() {
    val data = Any()
    observer.onChanged(Resource.success(data))

    verify(onSuccess).invoke(data)
  }

  @Test
  fun `When an error Resource is set, the onError callback is invoked`() {
    val error = Exception("error")
    observer.onChanged(Resource.error(error))

    verify(onError).invoke(error)
  }

  @Test
  fun `When a loading Resource is set, the onLoading callback is invoked`() {
    val data = null
    observer.onChanged(Resource.loading(data))

    verify(onLoading).invoke(data)
  }

  @Test
  fun `When a Resource is set, the onChanged callback is invoked`() {
    val resource = Resource.error<Any>(Exception("error"))
    observer.onChanged(resource)

    verify(onChanged).invoke(resource)
  }

  @Test
  fun `When a null Resource is set, the onChanged callback is invoked`() {
    val resource = null
    observer.onChanged(resource)

    verify(onChanged).invoke(resource)
  }

}
