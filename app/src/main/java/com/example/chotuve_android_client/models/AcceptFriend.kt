/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: A swagger API
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package com.example.chotuve_android_client.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property response
 */
@JsonClass(generateAdapter = true)
data class AcceptFriend(
    @Json(name = "response") @field:Json(name = "response") var response: Boolean? = null
)
