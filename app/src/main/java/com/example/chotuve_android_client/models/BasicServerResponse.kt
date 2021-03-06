/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: A swagger API
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package com.example.chotuve_android_client.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property serverMessage
 * @property status
 */
@JsonClass(generateAdapter = true)
data class BasicServerResponse(
    @Json(name = "server_message") @field:Json(name = "server_message") var serverMessage: BasicServerResponseServerMessage? = null,
    @Json(name = "status") @field:Json(name = "status") var status: Int? = null
)
