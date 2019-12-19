package com.netsells.bloc.testutil

import com.netsells.bloc.BlocViewModel

open class FakeBlocViewModel : BlocViewModel<FakeState, FakeEvent>(FakeState.INITIAL) {
    override suspend fun mapEventToState(event: FakeEvent): FakeState {
        return when (event) {
            FakeEvent.FETCH -> FakeState.LOADED
        }
    }
}