/* Copyright © 2019 Netsells */
package com.netsells.bloc.example.main.bloc

import com.netsells.bloc.BlocViewModel
import kotlin.random.Random
import kotlinx.coroutines.delay

class MainBloc : BlocViewModel<MainState, MainEvent>(initialState = InitialMainState) {

    override suspend fun mapEventToState(event: MainEvent) {
        when (event) {
            FetchEvent -> fetch()
        }
    }

    private suspend fun fetch() {
        yield(LoadingMainState)
        delay(500) // Adds an artificial delay so we can see the loading spinner
        if (Random.nextBoolean()) { // Randomly pick between success and failure for demonstration
            yield(LoadedMainState("Hello, this is the result."))
        } else {
            yield(ErrorMainState("Uh oh"))
        }
    }
}
