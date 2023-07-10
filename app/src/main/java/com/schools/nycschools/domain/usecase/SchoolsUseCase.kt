package com.schools.nycschools.domain.usecase

import com.schools.nycschools.domain.model.NYCSchoolResponseItem
import com.schools.nycschools.domain.model.NYCSchoolSATScoreResponseItem
import com.schools.nycschools.domain.model.Output
import kotlinx.coroutines.flow.Flow

/**
 * Interface of Schools UseCase to expose to ui module
 *  @constructor Create empty Schools use case
 */

interface SchoolsUseCase {
    /**
     * UseCase Method to fetch the schools from Data Layer
     */

    suspend fun executeHomePage(): Flow<Output<List<NYCSchoolResponseItem>>>

    suspend fun executeSchoolDetail(id: String): Flow<Output<List<NYCSchoolSATScoreResponseItem>>>
}
