package com.example.myapplication3.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication3.data.entity.Payment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

class HomeViewModel : ViewModel() {
    private val _state = MutableStateFlow(HomeViewState())

    val state : StateFlow<HomeViewState>
        get() = _state
    
    init {
        val list = mutableListOf<Payment>()
        for(x in 1..20){
            list.add(
                Payment(
                    paymentId = x.toLong(),
                    paymentTitle = "$x payment",
                    paymentCategory = "Food",
                    paymentDate = Date()
                )
            )
        }
        viewModelScope.launch {
            _state.value = HomeViewState(
                payments = list
            )
        }
    }
}

data class HomeViewState(
    val payments : List<Payment> = emptyList()
)