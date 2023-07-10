package com.schools.nycschools.domain.usecase

import com.schools.nycschools.domain.model.NYCSchoolResponseItem
import com.schools.nycschools.domain.model.NYCSchoolSATScoreResponseItem
import com.schools.nycschools.domain.model.Output
import com.schools.nycschools.domain.repository.SchoolsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Implementation of Schools UseCase
 * @param schoolsRepository the schools repository object
 */
internal class SchoolsUseCaseImpl @Inject constructor(private val schoolsRepository: SchoolsRepository) :
    SchoolsUseCase {

    override suspend fun executeHomePage(): Flow<Output<List<NYCSchoolResponseItem>>> {
        return schoolsRepository.fetchHomePage()
    }

    override suspend fun executeSchoolDetail(id: String): Flow<Output<List<NYCSchoolSATScoreResponseItem>>> {
        return schoolsRepository.fetchSchoolDetail(id)
    }
}
