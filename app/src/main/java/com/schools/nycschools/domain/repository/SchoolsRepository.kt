package com.schools.nycschools.domain.repository

import com.schools.nycschools.domain.model.NYCSchoolResponseItem
import com.schools.nycschools.domain.model.NYCSchoolSATScoreResponseItem
import com.schools.nycschools.domain.model.Output
import kotlinx.coroutines.flow.Flow

/**
 * Interface of Schools Repository to expose to other module
 *  @constructor Create empty Schools repository
 */
interface SchoolsRepository {

    suspend fun fetchHomePage(): Flow<Output<List<NYCSchoolResponseItem>>>

    suspend fun fetchSchoolDetail(id: String): Flow<Output<List<NYCSchoolSATScoreResponseItem>>>
}
