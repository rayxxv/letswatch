package com.rayxxv.letswatch.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rayxxv.letswatch.data.Repository
import com.rayxxv.letswatch.data.local.User

import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: Repository): ViewModel() {
    private val _userData = MutableLiveData<User>()
    val userData : LiveData<User> get() = _userData


    fun insertUser(user: User){
        viewModelScope.launch {
            repository.insertUser(user)
        }
    }


}