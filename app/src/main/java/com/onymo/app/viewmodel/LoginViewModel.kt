package com.onymo.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onymo.app.data.model.User
import com.onymo.app.data.repository.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    private val _loginResult = MutableStateFlow<Boolean?>(null)
    val loginResult: StateFlow<Boolean?> get() = _loginResult.asStateFlow()

    private val _loggedInUser = MutableStateFlow<User?>(null)
    val loggedInUser: StateFlow<User?> get() = _loggedInUser.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val user = repository.authenticate(email, password)
            if (user != null) {
                _loginResult.value = true
                _loggedInUser.value = user
            } else {
                _loginResult.value = false
            }
        }
    }
}