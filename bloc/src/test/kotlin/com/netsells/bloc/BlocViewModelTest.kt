/* Copyright © 2019 Netsells */
package com.netsells.bloc

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.netsells.bloc.testutil.FakeBlocViewModel
import com.netsells.bloc.testutil.FakeEvent
import com.netsells.bloc.testutil.FakeState
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verifyBlocking
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(sdk = [28])
class BlocViewModelTest {

    private lateinit var viewModel: FakeBlocViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = spy(FakeBlocViewModel())
    }

    @Test
    fun `initial state`() {
        viewModel.currentState.observeForever {}
        assertEquals(FakeState.INITIAL, viewModel.currentState.value)
    }

    @Test
    fun `mapEventToState is called when new event is dispatched`() = runBlockingTest {
        viewModel.currentState.observeForever {}

        viewModel.dispatch(FakeEvent.FETCH)

        verifyBlocking(viewModel) {
            mapEventToState(FakeEvent.FETCH)
        }
    }
}
