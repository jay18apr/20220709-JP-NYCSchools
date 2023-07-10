package com.schools.nycschools.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schools.nycschools.domain.model.NYCSchoolResponseItem
import com.schools.nycschools.domain.model.Output
import com.schools.nycschools.domain.usecase.SchoolsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Home view model
 *
 * @property schoolsUseCase
 * @constructor Create empty Home view model
 */
@HiltViewModel
class HomeViewModel @Inject constructor(private val schoolsUseCase: SchoolsUseCase) : ViewModel() {

    val homePageState = MutableStateFlow<Output<List<NYCSchoolResponseItem>>>(Output.loading(null))

    init {
        fetchSchoolsPage()
    }

    private fun fetchSchoolsPage() {
        viewModelScope.launch {
            schoolsUseCase.executeHomePage().collect {
                homePageState.value = it
            }
        }
    }
}
