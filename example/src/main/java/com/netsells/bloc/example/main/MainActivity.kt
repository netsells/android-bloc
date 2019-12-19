package com.netsells.bloc.example.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.netsells.bloc.example.R
import com.netsells.bloc.example.databinding.MainActivityBinding
import com.netsells.bloc.example.main.bloc.FetchEvent
import com.netsells.bloc.example.main.bloc.MainBloc

class MainActivity : AppCompatActivity() {

    private val bloc by lazy { ViewModelProviders.of(this).get<MainBloc>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<MainActivityBinding>(
            this,
            R.layout.main_activity
        )
        binding.lifecycleOwner = this
        binding.activity = this

        bloc.currentState.observe(this, Observer {
            binding.state = it
        })
    }

    fun fetch() = bloc.dispatch(FetchEvent)
}