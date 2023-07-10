package com.schools.nycschools.ui.schoolDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schools.nycschools.domain.model.NYCSchoolSATScoreResponseItem
import com.schools.nycschools.domain.model.Output
import com.schools.nycschools.domain.usecase.SchoolsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * School detail view model
 *
 * @property schoolsUseCase
 * @constructor Create empty School detail view model
 */
@HiltViewModel
class SchoolDetailViewModel @Inject constructor(private val schoolsUseCase: SchoolsUseCase) : ViewModel() {

    val schoolsState = MutableStateFlow<Output<List<NYCSchoolSATScoreResponseItem>>>(Output.loading(null))

    fun fetchSchoolDetail(id: String) {
        viewModelScope.launch {
            schoolsUseCase.executeSchoolDetail(id).collect {
                schoolsState.value = it
            }
        }
    }
}
