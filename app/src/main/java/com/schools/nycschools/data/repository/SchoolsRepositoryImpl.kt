package com.schools.nycschools.data.repository

import com.schools.nycschools.data.remote.SchoolsRemoteDataSource
import com.schools.nycschools.domain.model.NYCSchoolResponseItem
import com.schools.nycschools.domain.model.NYCSchoolSATScoreResponseItem
import com.schools.nycschools.domain.model.Output
import com.schools.nycschools.domain.repository.SchoolsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Schools repository impl
 *
 * @property schoolsRemoteDataSource
 * @constructor Create empty Schools repository impl
 */
internal class SchoolsRepositoryImpl @Inject constructor(
    private val schoolsRemoteDataSource: SchoolsRemoteDataSource
) : SchoolsRepository {

    override suspend fun fetchHomePage(): Flow<Output<List<NYCSchoolResponseItem>>> {
        return flow {
            emit(Output.loading())
            val result = schoolsRemoteDataSource.fetchHomePage()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun fetchSchoolDetail(id: String): Flow<Output<List<NYCSchoolSATScoreResponseItem>>> {
        return flow {
            emit(Output.loading())
            val result = schoolsRemoteDataSource.fetchSchoolDetail(id)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}
