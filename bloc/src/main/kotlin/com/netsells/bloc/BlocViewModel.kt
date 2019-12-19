/* Copyright © 2019 Netsells */
package com.netsells.bloc

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * A special [ViewModel] which can be used to implement the BLoC pattern.
 *
 * Extend this class by providing an initial state and an implementation of [mapEventToState].
 * You will then be able to [dispatch] [Event]s to the BLoC, and observe the [currentState].
 *
 * @author Peter Bryant
 * @param initialState The initial [State] of the BLoC.
 */
abstract class BlocViewModel<State, Event>(initialState: State) : ViewModel() {

    private val _currentState = MutableLiveData<State>(initialState)

    /**
     * Called when a new [Event] is [dispatch]ed to the BLoC.
     *
     * In most cases, you will want to [yield] at least one [State] in response to this [Event].
     *
     * This is where you should implement the business logic of the BLoC.
     *
     * @param event The [Event] which has been dispatched to the BloC.
     * @return The [State] of the BLoC after the [event] has been handled.
     */
    abstract suspend fun mapEventToState(event: Event)

    /**
     * The current [State] of the BLoC.
     */
    val currentState: LiveData<State> = _currentState

    /**
     * Sends a new [Event] to the BLoC, triggering the [mapEventToState] method.
     */
    fun dispatch(event: Event) {
        viewModelScope.launch {
            mapEventToState(event)
        }
    }

    /**
     * Posts a new [State] to any observers.
     *
     * This can only be called from within the BLoC class.
     */
    protected fun yield(state: State) {
        _currentState.postValue(state)
    }
}
