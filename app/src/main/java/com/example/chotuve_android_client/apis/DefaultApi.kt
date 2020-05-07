/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: A swagger API
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package com.example.chotuve_android_client.apis

import com.example.chotuve_android_client.models.PingResponse
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST

@JvmSuppressWildcards
interface DefaultApi {
    /**
     * Este es un método para recibir información del Server
     * The endpoint is owned by defaultname service owner
     */
    @GET("/api/about/")
    fun apiAboutGet(): Completable
    /**
     * Este es un método para listar los videos en pantalla principal
     * The endpoint is owned by defaultname service owner
     */
    @GET("/api/home/")
    fun apiHomeGet(): Completable
    /**
     * Este es un método para recibir información del estado de los servers
     * The endpoint is owned by defaultname service owner
     */
    @GET("/api/ping/")
    fun apiPingGet(): Single<PingResponse>
    /**
     * Recibe una solicitud de registro
     * The endpoint is owned by defaultname service owner
     * @param email email of the user (required)
     * @param password secret password (required)
     */
    @POST("/api/register/")
    fun apiRegisterPost(
        @retrofit2.http.Header("email") email: String,
        @retrofit2.http.Header("password") password: String
    ): Completable
}
