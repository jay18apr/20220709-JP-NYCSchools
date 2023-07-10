package com.schools.nycschools.domain.model

import java.io.Serializable

data class NYCSchoolResponseItem(
    val bus: String? = null,
    val campus_name: String? = null,
    val city: String? = null,
    val dbn: String? = null,
    val fax_number: String? = null,
    val latitude: String? = null,
    val longitude: String? = null,
    val overview_paragraph: String? = null,
    val phone_number: String? = null,
    val primary_address_line_1: String? = null,
    val school_email: String? = null,
    val school_name: String? = null,
    val state_code: String? = null,
    val subway: String? = null,
    val total_students: String? = null,
    val website: String? = null,
    val zip: String? = null
) : Serializable
