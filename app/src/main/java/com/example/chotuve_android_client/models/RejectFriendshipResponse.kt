/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: A swagger API
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package com.example.chotuve_android_client.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property messageResult
 * @property status
 */
@JsonClass(generateAdapter = true)
data class RejectFriendshipResponse(
    @Json(name = "message_result") @field:Json(name = "message_result") var messageResult: Map<String, Any?>? = null,
    @Json(name = "status") @field:Json(name = "status") var status: Int? = null
)
