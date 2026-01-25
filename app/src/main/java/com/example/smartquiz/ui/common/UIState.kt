package com.example.smartquiz.ui.common

/**
 * Generic UI state holder for screens that load data.
 */
data class UiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)