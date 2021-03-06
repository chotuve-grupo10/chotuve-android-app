/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: A swagger API
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package com.example.chotuve_android_client.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property AppToken App server token
 * @property AuthToken Auth server token
 */
@JsonClass(generateAdapter = true)
data class LoginResponse(
    @Json(name = "App token") @field:Json(name = "App token") var AppToken: String? = null,
    @Json(name = "Auth token") @field:Json(name = "Auth token") var AuthToken: String? = null
)
