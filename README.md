# Android BLoC

[![Build Status](https://app.bitrise.io/app/0c552fce07215a12/status.svg?token=y4RhFBvFMv0opXwwLk8qDg&branch=master)](https://app.bitrise.io/app/0c552fce07215a12)
![JitPack](https://img.shields.io/jitpack/v/github/netsells/android-bloc)

An implementation of the BLoC (Business Logic Component) pattern for Android apps. Inspired by the [BLoC Library for Flutter](https://bloclibrary.dev/).

## Background

**Events** are the input for a BLoC. They are commonly UI events such as button presses. Events are dispatched to the BLoC and then converted into States.

**States** are the output of a BLoC. Presentation components can listen to the stream of states and redraw portions of themselves based on the given state.

## Installation

```groovy
implementation 'com.github.netsells:android-bloc:x.x.x'
```

## Implementing a BLoC

Consider a simple app which, when a button is pressed, loads a String from a data source and displays it.

Begin by defining the possible **States** of your UI, as well as any events which could be triggered.

States:
* Initial (before the button has been pressed)
* Loading (the button has been pressed, but the result hasn't loaded yet)
* Loaded (the result has been loaded)
* Error (an error occurred)

Create a states file containing representations of the states:

```kotlin
sealed class MainState(val text: String? = null)

object InitialMainState : MainState("Push the button!")

object LoadingMainState: MainState()

class LoadedMainState(data: String) : MainState(data)

class ErrorMainState(error: String) : MainState(error)
```

And another for the events:

```kotlin
sealed class MainEvent

object FetchEvent : MainEvent()
```

### Writing the BLoC

When you've defined your states and events, you can create your BLoC. Here's the skeleton:

```kotlin
class MainBloc : BlocViewModel<MainState, MainEvent>(initialState = InitialMainState) {
    override suspend fun mapEventToState(event: MainEvent) {
        // TODO Implement business logic
    }
}
```

The `mapEventToState` method is called whenever a new `Event` is dispatched to the BLoC. We can implement business logic accordingly:

```kotlin
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
```

### Using the BLoC

The astute among you will have realised that the BLoC class above extends `BlocViewModel`. So BLoCs are ViewModels! 

For that reason they are lifecycle-aware, which is super helpful. To use your BLoC in an Activity or Fragment, simply get it using your standard `ViewModelProvider`:

```kotlin
private val bloc by lazy { ViewModelProviders.of(this).get<MainBloc>() }
```

You can then observe the current state of the BLoC:

```kotlin
bloc.currentState.observe(viewLifecycleOwner, Observer { state ->
    // TODO Something cool with the state
})
```

... and dispatch events:

```kotlin
bloc.dispatch(FetchEvent)
```

A full example can be found in the `example` directory.
