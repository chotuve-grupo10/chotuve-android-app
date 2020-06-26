/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: A swagger API
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package com.example.chotuve_android_client.apis

import com.example.chotuve_android_client.models.LoginResponse
import com.example.chotuve_android_client.models.PingResponse
import com.example.chotuve_android_client.models.RegisterResponse
import com.example.chotuve_android_client.models.UploadVideoResponse
import com.example.chotuve_android_client.models.UserLogin
import com.example.chotuve_android_client.models.UserRegister
import com.example.chotuve_android_client.models.VideoList
import com.example.chotuve_android_client.models.VideoToUpload
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.DELETE
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
     * Este servicio permitirá dar de baja un video en el sistema
     * The endpoint is owned by defaultname service owner
     * @param videoId video id (required)
     */
    @DELETE("/api/delete_video/{video_id}")
    fun apiDeleteVideoVideoIdDelete(
        @retrofit2.http.Path("video_id") videoId: Map<String, Any?>
    ): Completable
    /**
     * Este es un método para listar los videos en pantalla principal
     * The endpoint is owned by defaultname service owner
     */
    @GET("/api/home/")
    fun apiHomeGet(): Completable
    /**
     * Este servicio permitirá listar videos en el sistema
     * The endpoint is owned by defaultname service owner
     */
    @GET("/api/list_videos/")
    fun apiListVideosGet(): Single<VideoList>
    /**
     * Este servicio permitirá listar videos en el sistema
     * The endpoint is owned by defaultname service owner
     * @param userId user id (required)
     */
    @GET("/api/list_videos/{user_id}")
    fun apiListVideosUserIdGet(
        @retrofit2.http.Path("user_id") userId: Map<String, Any?>
    ): Completable
    /**
     * Este servicio permitirá a los usuarios poder ingresar al sistema
     * The endpoint is owned by defaultname service owner
     * @param user User to login. (optional)
     */
    @POST("/api/login/")
    fun apiLoginPost(
        @retrofit2.http.Body user: UserLogin
    ): Single<LoginResponse>
    /**
     * Recibe una solicitud de login utilizando Firebase.
     * Si el login es exitoso pero el usuario no está registrado en la plataforma, se<br/>lo registra automáticamente<br/>
     * The endpoint is owned by defaultname service owner
     * @param authorization id token (required)
     */
    @POST("/api/login_with_firebase/")
    fun apiLoginWithFirebasePost(
        @retrofit2.http.Header("authorization") authorization: String
    ): Single<LoginResponse>
    /**
     * Este es un método para recibir información del estado de los servers
     * The endpoint is owned by defaultname service owner
     */
    @GET("/api/ping/")
    fun apiPingGet(): Single<PingResponse>
    /**
     * Este servicio permitirá a los usuarios darse de alta en el sistema
     * The endpoint is owned by defaultname service owner
     * @param user The user to create. (optional)
     */
    @POST("/api/register/")
    fun apiRegisterPost(
        @retrofit2.http.Body user: UserRegister
    ): Single<RegisterResponse>
    /**
     * Recibe una solicitud de registro utilizando Firebase
     * The endpoint is owned by defaultname service owner
     * @param authorization id token (required)
     */
    @POST("/api/register_with_firebase/")
    fun apiRegisterWithFirebasePost(
        @retrofit2.http.Header("authorization") authorization: String
    ): Completable
    /**
     * Este servicio permitirá dar de alta un video en el sistema
     * The endpoint is owned by defaultname service owner
     * @param video The video to upload. (optional)
     */
    @POST("/api/upload_video/")
    fun apiUploadVideoPost(
        @retrofit2.http.Body video: VideoToUpload
    ): Single<UploadVideoResponse>
    /**
     * Este es un método para recibir un token del auth server y validarlo
     * The endpoint is owned by defaultname service owner
     * @param authorization bearer token (required)
     */
    @GET("/api/validate_auth_token/")
    fun apiValidateAuthTokenGet(
        @retrofit2.http.Header("authorization") authorization: String
    ): Completable
    /**
     * Este es un método para recibir un token y validarlo
     * The endpoint is owned by defaultname service owner
     * @param authorization bearer token (required)
     */
    @GET("/api/validate_token/")
    fun apiValidateTokenGet(
        @retrofit2.http.Header("authorization") authorization: String
    ): Completable
}
