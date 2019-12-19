/* Copyright © 2019 Netsells */
package com.netsells.bloc.example.main.bloc

sealed class MainState(val text: String? = null)

object InitialMainState : MainState("Push the button!")

object LoadingMainState : MainState()

class LoadedMainState(data: String) : MainState(data)

class ErrorMainState(error: String) : MainState(error)
