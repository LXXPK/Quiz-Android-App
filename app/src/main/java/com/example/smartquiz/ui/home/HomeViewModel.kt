package com.example.smartquiz.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartquiz.data.local.dao.UserDao
import com.example.smartquiz.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userDao: UserDao,
    private val userRepository: UserRepository
) : ViewModel() {

    fun fetchUsers() {
        viewModelScope.launch {
            val users = userDao.getAll()
            val usersFromRepository = userRepository.getAllUsers()
        }
    }
}
