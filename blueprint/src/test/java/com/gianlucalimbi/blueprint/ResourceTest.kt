package com.gianlucalimbi.blueprint

import org.hamcrest.Matchers.*
import org.junit.Assert.assertThat
import org.junit.Test

class ResourceTest {

  @Test
  fun `When creating a success Resource, an instance of SuccessResource is provided`() {
    val resource = Resource.success(Any())

    assertThat(resource, `is`(instanceOf(SuccessResource::class.java)))
    assertThat(resource.status, `is`(Resource.Status.SUCCESS))
  }

  @Test
  fun `When creating an error Resource, an instance of ErrorResource is provided`() {
    val resource = Resource.error<Any>(Exception("error"))

    assertThat(resource, `is`(instanceOf(ErrorResource::class.java)))
    assertThat(resource.status, `is`(Resource.Status.ERROR))
  }

  @Test
  fun `When creating a loading Resource, an instance of LoadingResource is provided`() {
    val resource = Resource.loading<Any>()

    assertThat(resource, `is`(instanceOf(LoadingResource::class.java)))
    assertThat(resource.status, `is`(Resource.Status.LOADING))
  }

  @Test
  fun `When comparing a Resource with null, they are not equals`() {
    val resource = Resource.success(Any())
    val other: Any? = null

    assertThat(resource, `is`(not(other)))
  }

  @Test
  fun `When comparing a Resource with another object, they are not equals`() {
    val resource = Resource.success(Any())
    val other: Any? = "foo"

    assertThat(resource, `is`(not(other)))
  }

  @Test
  fun `When comparing a Resource with another Resource with different status, they are not equals`() {
    val resource = Resource.success(Any())
    val other = Resource.error<Any>(Exception("error"))

    assertThat(resource, `is`(not(other)))
  }

  @Test
  fun `When comparing a Resource with another Resource with different data, they are not equals`() {
    val resource = Resource.success(Any())
    val other = Resource.success<Any>("foo")

    assertThat(resource, `is`(not(other)))
  }

  @Test
  fun `When comparing a Resource with another Resource with different errors, they are not equals`() {
    val resource = Resource.error<Any>(Exception("error"))
    val other = Resource.error<Any>(Exception("another error"))

    assertThat(resource, `is`(not(other)))
  }

  @Test
  fun `When comparing a Resource with another Resource with the same data, they are equals`() {
    val data = Any()

    val resource = Resource.success(data)
    val other = Resource.success(data)

    assertThat(resource, `is`(other))
  }

  @Test
  fun `When comparing a Resource with another Resource with the same error, they are equals`() {
    val error = Exception("error")

    val resource = Resource.error<Any>(error)
    val other = Resource.error<Any>(error)

    assertThat(resource, `is`(other))
  }

}
