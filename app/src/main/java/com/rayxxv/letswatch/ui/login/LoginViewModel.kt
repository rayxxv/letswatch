package com.rayxxv.letswatch.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rayxxv.letswatch.data.Repository
import com.rayxxv.letswatch.data.local.User
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository): ViewModel() {

    private val _loginData : MutableLiveData<User> = MutableLiveData()
    val loginData: LiveData<User> get() = _loginData

    private val _userPref : MutableLiveData<User> = MutableLiveData()
    val userPref : LiveData<User> get() = _userPref

    fun setUserPref(userEntity: User){
        viewModelScope.launch {
            repository.setUserPref(userEntity)
        }
    }

    fun getUserPref(){
        viewModelScope.launch {
            repository.getUserPref().collect{
                _userPref.postValue(it)
            }
        }
    }


    fun loginUser(username: String, password: String){
        viewModelScope.launch {
            _loginData.value = repository.loginUser(username, password)
        }
    }
}