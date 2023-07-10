package com.schools.nycschools.data

/**
 * Api main headers provider
 *
 * @constructor Create empty Api main headers provider
 */
class ApiMainHeadersProvider {

    /**
     * Returns both the default headers and the headers that are mandatory for authenticated users.
     */
    fun getAuthenticatedHeaders(): AuthenticatedHeaders =
        AuthenticatedHeaders().apply {}
}

open class ApiMainHeaders : HashMap<String, String>()
class AuthenticatedHeaders : ApiMainHeaders()
