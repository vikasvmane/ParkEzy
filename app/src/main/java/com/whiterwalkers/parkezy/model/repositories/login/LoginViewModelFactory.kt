package com.whiterwalkers.parkezy.model.repositories.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.whiterwalkers.parkezy.model.pojos.LoginDataSource
import com.whiterwalkers.parkezy.model.pojos.LoginRepository

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                loginRepository = LoginRepository(
                    dataSource = LoginDataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}