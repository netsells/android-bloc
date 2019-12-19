/* Copyright © 2019 Netsells */
package com.netsells.bloc.testutil

import com.netsells.bloc.BlocViewModel

open class FakeBlocViewModel : BlocViewModel<FakeState, FakeEvent>(FakeState.INITIAL) {
    override suspend fun mapEventToState(event: FakeEvent) {
        when (event) {
            FakeEvent.FETCH -> yield(FakeState.LOADED)
        }
    }
}
