package com.elevenine.artinstitute.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elevenine.artinstitute.domain.DomainState
import com.elevenine.artinstitute.domain.interactor.GetCategoriesInteractor
import com.elevenine.artinstitute.ui.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(private val getCategoriesInteractor: GetCategoriesInteractor) :
    ViewModel() {

    private val _uiState = MutableLiveData<CategoriesUiState>()
    val uiState: LiveData<CategoriesUiState>
        get() = _uiState

    init {
        viewModelScope.launch { getCategoriesInteractor.initInteractor() }

        viewModelScope.launch { getCategoriesInteractor.updateCategories() }

        getCategoriesInteractor.categoriesFlow.onEach { domainState ->
            when (domainState) {
                is DomainState.Error -> {
                    _uiState.postValue(
                        CategoriesUiState(
                            domainState.data ?: emptyList(),
                            isInitialLoading = false,
                            showErrorToast = true
                        )
                    )
                }
                is DomainState.Success -> {
                    _uiState.postValue(
                        CategoriesUiState(
                            domainState.data,
                            isInitialLoading = false,
                            showErrorToast = false
                        )
                    )
                }
                is DomainState.Loading -> {
                    val prevState = _uiState.value
                    val newValue = prevState?.copy(isInitialLoading = true) ?: CategoriesUiState(
                        listOf(), isInitialLoading = true, showErrorToast = false
                    )
                    _uiState.postValue(newValue)
                }
            }
        }.launchIn(viewModelScope)
    }
}

data class CategoriesUiState(
    val categories: List<Category>,
    val isInitialLoading: Boolean,
    val showErrorToast: Boolean
)
