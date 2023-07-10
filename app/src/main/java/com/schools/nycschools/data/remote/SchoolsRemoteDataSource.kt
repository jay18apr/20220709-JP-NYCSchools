package com.schools.nycschools.data.remote

import com.schools.nycschools.data.ApiMainHeadersProvider
import com.schools.nycschools.data.BaseRemoteDataSource
import com.schools.nycschools.data.services.ApiService
import com.schools.nycschools.domain.model.NYCSchoolResponseItem
import com.schools.nycschools.domain.model.NYCSchoolSATScoreResponseItem
import com.schools.nycschools.domain.model.Output
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Schools remote data source
 *
 * @property apiService
 * @property headersProvider
 * @constructor
 *
 * @param retrofit
 */
class SchoolsRemoteDataSource @Inject constructor(
    private val apiService: ApiService,
    retrofit: Retrofit,
    private val headersProvider: ApiMainHeadersProvider
) : BaseRemoteDataSource(retrofit) {

    suspend fun fetchHomePage(): Output<List<NYCSchoolResponseItem>> {
        return getResponse(
            request = { apiService.getHomePage(headersProvider.getAuthenticatedHeaders()) },
            defaultErrorMessage = "Error fetching Schools List"
        )
    }

    suspend fun fetchSchoolDetail(id: String): Output<List<NYCSchoolSATScoreResponseItem>> {
        return getResponse(
            request = { apiService.getSchoolDetail(headersProvider.getAuthenticatedHeaders(), id) },
            defaultErrorMessage = "Error fetching School SAT Score"
        )
    }
}
