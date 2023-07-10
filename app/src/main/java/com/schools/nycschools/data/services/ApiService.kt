package com.schools.nycschools.data.services

import com.schools.nycschools.data.AuthenticatedHeaders
import com.schools.nycschools.domain.model.NYCSchoolResponseItem
import com.schools.nycschools.domain.model.NYCSchoolSATScoreResponseItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Query

/**
 * Api service
 *
 * @constructor Create empty Api service
 */
interface ApiService {

    @GET("resource/s3k6-pzi2.json")
    suspend fun getHomePage(
        @HeaderMap authedHeaders: AuthenticatedHeaders
    ): Response<List<NYCSchoolResponseItem>>

    @GET("resource/f9bf-2cp4.json")
    suspend fun getSchoolDetail(
        @HeaderMap authedHeaders: AuthenticatedHeaders,
        @Query("dbn") id: String
    ): Response<List<NYCSchoolSATScoreResponseItem>>
}
