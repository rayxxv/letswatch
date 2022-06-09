package com.rayxxv.letswatch.ui.profil

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rayxxv.letswatch.data.Repository
import com.rayxxv.letswatch.data.local.User
import kotlinx.coroutines.launch

class ProfileFragmentViewModel ( private val repository: Repository): ViewModel(){
    private val _dataUserPref = MutableLiveData<User>()
    val dataUserPref : LiveData<User> get() = _dataUserPref

    fun getUserPref(){
        viewModelScope.launch {
            repository.getUserPref().collect{
                _dataUserPref.postValue(it)
            }
        }
    }

    fun updateUser(userEntity: User){
        viewModelScope.launch {
            repository.updateUser(userEntity)
        }
    }

    fun setUserPref(userEntity: User){
        viewModelScope.launch {
            repository.setUserPref(userEntity)
        }
    }
}