/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Swagger Petstore
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package com.example.chotuve_android_client.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property code
 * @property type
 * @property message
 */
@JsonClass(generateAdapter = true)
data class ApiResponse(
    @Json(name = "code") @field:Json(name = "code") var code: Int? = null,
    @Json(name = "type") @field:Json(name = "type") var type: String? = null,
    @Json(name = "message") @field:Json(name = "message") var message: String? = null
)
