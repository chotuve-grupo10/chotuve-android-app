/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: A swagger API
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package com.example.chotuve_android_client.apis

import com.example.chotuve_android_client.models.AcceptFriend
import com.example.chotuve_android_client.models.BasicServerResponse
import com.example.chotuve_android_client.models.Comment
import com.example.chotuve_android_client.models.ErrorDeleteUserResponse
import com.example.chotuve_android_client.models.ErrorResponse
import com.example.chotuve_android_client.models.ForgotPasswordSuccessfulResponse
import com.example.chotuve_android_client.models.ListedUser
import com.example.chotuve_android_client.models.LoginResponse
import com.example.chotuve_android_client.models.PingResponse
import com.example.chotuve_android_client.models.RegisterResponse
import com.example.chotuve_android_client.models.RequestFriendshipResponse
import com.example.chotuve_android_client.models.ResetPasswordBody
import com.example.chotuve_android_client.models.ResetPasswordSuccessfulResponse
import com.example.chotuve_android_client.models.ServerTime
import com.example.chotuve_android_client.models.SuccessfulDeleteUserResponse
import com.example.chotuve_android_client.models.UploadVideoResponse
import com.example.chotuve_android_client.models.UserLogin
import com.example.chotuve_android_client.models.UserModificationSuccessfulResponse
import com.example.chotuve_android_client.models.UserRegister
import com.example.chotuve_android_client.models.UsersInformationList
import com.example.chotuve_android_client.models.Video
import com.example.chotuve_android_client.models.VideoList
import com.example.chotuve_android_client.models.VideoToUpload
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT

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
     * Si el login es exitoso pero el usuario no está registrado en la plataforma, se <br/>lo registra automáticamente <br/>
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
     * Este servicio permite obtener la hora del Server 
     * The endpoint is owned by defaultname service owner
     */
    @GET("/api/time")
    fun apiTimeGet(): Single<ServerTime>
    /**
     * Este servicio permitirá filtrar usuarios 
     * The endpoint is owned by defaultname service owner
     * @param userEmail users email making request (required)
     * @param filter filtering data (optional)
     */
    @GET("/api/users")
    fun apiUsersGet(
        @retrofit2.http.Header("User_email") userEmail: String,
        @retrofit2.http.Query("filter") filter: String?
    ): Single<UsersInformationList>
    /**
     * Este servicio permite eliminar a un usuario 
     * The endpoint is owned by defaultname service owner
     * @param userEmail my email (required)
     */
    @DELETE("/api/users/{user_email}")
    fun apiUsersUserEmailDelete(
        @retrofit2.http.Path("user_email") userEmail: String
    ): Single<SuccessfulDeleteUserResponse>
    /**
     * Este servicio permite aceptar una solicitud de contacto de usuario y crear una relación de amistad 
     * The endpoint is owned by defaultname service owner
     * @param userEmail my email (required)
     * @param friendsEmail potential new friends email (required)
     */
    @DELETE("/api/users/{user_email}/friends/{friends_email}")
    fun apiUsersUserEmailFriendsFriendsEmailDelete(
        @retrofit2.http.Path("user_email") userEmail: String,
        @retrofit2.http.Path("friends_email") friendsEmail: String
    ): Single<BasicServerResponse>
    /**
     * Este servicio permite obtener información del usuario (y sus amigos) 
     * The endpoint is owned by defaultname service owner
     * @param userEmail email del usuario (required)
     */
    @GET("/api/users/{user_email}/friends")
    fun apiUsersUserEmailFriendsGet(
        @retrofit2.http.Path("user_email") userEmail: String
    ): Single<UsersInformationList>
    /**
     * Este servicio permite aceptar una solicitud de contacto de usuario y crear una relación de amistad 
     * The endpoint is owned by defaultname service owner
     * @param userEmail my email (required)
     * @param newFriendsEmail potential new friends email (required)
     * @param responseBody Document containing true for accept, false for reject. (optional)
     */
    @PATCH("/api/users/{user_email}/friends/{new_friends_email}")
    fun apiUsersUserEmailFriendsNewFriendsEmailPatch(
        @retrofit2.http.Path("user_email") userEmail: String,
        @retrofit2.http.Path("new_friends_email") newFriendsEmail: String,
        @retrofit2.http.Body responseBody: AcceptFriend
    ): Single<BasicServerResponse>
    /**
     * Este servicio permitirá dar de alta una solicitud de contacto de usuario 
     * The endpoint is owned by defaultname service owner
     * @param userEmail my email (required)
     * @param newFriendsEmail potential new friends email (required)
     */
    @POST("/api/users/{user_email}/friends/{new_friends_email}")
    fun apiUsersUserEmailFriendsNewFriendsEmailPost(
        @retrofit2.http.Path("user_email") userEmail: String,
        @retrofit2.http.Path("new_friends_email") newFriendsEmail: String
    ): Single<RequestFriendshipResponse>
    /**
     * Este servicio permitirá consultar el perfil de un usuario 
     * The endpoint is owned by defaultname service owner
     * @param authorization token (required)
     * @param userEmail User&#39;s email (required)
     */
    @GET("/api/users/{user_email}")
    fun apiUsersUserEmailGet(
        @retrofit2.http.Header("Authorization") authorization: String,
        @retrofit2.http.Path("user_email") userEmail: String
    ): Single<ListedUser>
    /**
     * Este servicio permite vincular un Token único de notificaciones a un determinado usuario 
     * The endpoint is owned by defaultname service owner
     * @param userEmail email del usuario (required)
     * @param token token único (required)
     */
    @PUT("/api/users/{user_email}/notifications/{token}")
    fun apiUsersUserEmailNotificationsTokenPut(
        @retrofit2.http.Path("user_email") userEmail: String,
        @retrofit2.http.Path("token") token: String
    ): Single<BasicServerResponse>
    /**
     * Este servicio permitirá a un usuario definir una nueva contraseña 
     * The endpoint is owned by defaultname service owner
     * @param userEmail User&#39;s email (required)
     * @param resetPassword Token and new password. (required)
     */
    @PUT("/api/users/{user_email}/password")
    fun apiUsersUserEmailPasswordPut(
        @retrofit2.http.Path("user_email") userEmail: String,
        @retrofit2.http.Body resetPassword: ResetPasswordBody
    ): Single<ResetPasswordSuccessfulResponse>
    /**
     * Este servicio permitirá modificar el perfil de un usuario 
     * The endpoint is owned by defaultname service owner
     * @param authorization token (required)
     * @param userEmail User&#39;s email (required)
     * @param user The user to modify. (optional)
     */
    @PUT("/api/users/{user_email}")
    fun apiUsersUserEmailPut(
        @retrofit2.http.Header("Authorization") authorization: String,
        @retrofit2.http.Path("user_email") userEmail: String,
        @retrofit2.http.Body user: ListedUser
    ): Single<UserModificationSuccessfulResponse>
    /**
     * Este servicio permite obtener las solicitudes de amistad de un usuario 
     * The endpoint is owned by defaultname service owner
     * @param userEmail email del usuario (required)
     */
    @GET("/api/users/{user_email}/requests")
    fun apiUsersUserEmailRequestsGet(
        @retrofit2.http.Path("user_email") userEmail: String
    ): Single<UsersInformationList>
    /**
     * Parte de servicio de recupero de contraseña. Envia codigo por mail al usuario para recuperar password 
     * The endpoint is owned by defaultname service owner
     * @param userEmail User&#39;s email (required)
     */
    @POST("/api/users/{user_email}/reset_password_token")
    fun apiUsersUserEmailResetPasswordTokenPost(
        @retrofit2.http.Path("user_email") userEmail: String
    ): Single<ForgotPasswordSuccessfulResponse>
    /**
     * Este servicio permitirá listar los videos de un usuario especifico teniendo en cuenta si el usuario que hace la request es amigo o no. 
     * The endpoint is owned by defaultname service owner
     * @param authorization token (required)
     * @param userId user id (optional)
     */
    @GET("/api/users/videos/")
    fun apiUsersVideosGet(
        @retrofit2.http.Header("Authorization") authorization: String,
        @retrofit2.http.Query("user_id") userId: String?
    ): Single<VideoList>
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
    /**
     * Este servicio permitirá dar de alta un video en el sistema 
     * The endpoint is owned by defaultname service owner
     * @param video The video to upload. (optional)
     */
    @POST("/api/videos/")
    fun apiVideosPost(
        @retrofit2.http.Body video: VideoToUpload
    ): Single<UploadVideoResponse>
    /**
     * Este servicio permitirá listar videos en el sistema para mostrarlos en la home de un usuario 
     * The endpoint is owned by defaultname service owner
     * @param userId user id (required)
     */
    @GET("/api/videos/{user_id}")
    fun apiVideosUserIdGet(
        @retrofit2.http.Path("user_id") userId: String
    ): Single<VideoList>
    /**
     * Este servicio permitirá dar de alta un comentario en un video 
     * The endpoint is owned by defaultname service owner
     * @param videoId id del video (required)
     * @param authorization token (required)
     * @param comment (optional)
     */
    @POST("/api/videos/{video_id}/comments")
    fun apiVideosVideoIdCommentsPost(
        @retrofit2.http.Path("video_id") videoId: String,
        @retrofit2.http.Header("Authorization") authorization: String,
        @retrofit2.http.Body comment: Comment
    ): Single<Video>
    /**
     * Este servicio permitirá dar de baja un video en el sistema 
     * The endpoint is owned by defaultname service owner
     * @param videoId video id (required)
     */
    @DELETE("/api/videos/{video_id}")
    fun apiVideosVideoIdDelete(
        @retrofit2.http.Path("video_id") videoId: String
    ): Completable
    /**
     * Este servicio permitirá eliminar un dislike a un video 
     * The endpoint is owned by defaultname service owner
     * @param videoId id del video (required)
     * @param authorization token (required)
     */
    @DELETE("/api/videos/{video_id}/dislikes")
    fun apiVideosVideoIdDislikesDelete(
        @retrofit2.http.Path("video_id") videoId: String,
        @retrofit2.http.Header("Authorization") authorization: String
    ): Single<Video>
    /**
     * Este servicio permitirá dar dislike a un video 
     * The endpoint is owned by defaultname service owner
     * @param videoId id del video (required)
     * @param authorization token (required)
     */
    @POST("/api/videos/{video_id}/dislikes")
    fun apiVideosVideoIdDislikesPost(
        @retrofit2.http.Path("video_id") videoId: String,
        @retrofit2.http.Header("Authorization") authorization: String
    ): Single<Video>
    /**
     * Este servicio permitirá eliminar el like de un video 
     * The endpoint is owned by defaultname service owner
     * @param videoId id del video (required)
     * @param authorization token (required)
     */
    @DELETE("/api/videos/{video_id}/likes")
    fun apiVideosVideoIdLikesDelete(
        @retrofit2.http.Path("video_id") videoId: String,
        @retrofit2.http.Header("Authorization") authorization: String
    ): Single<Video>
    /**
     * Este servicio permitirá likear un video 
     * The endpoint is owned by defaultname service owner
     * @param videoId id del video (required)
     * @param authorization token (required)
     */
    @POST("/api/videos/{video_id}/likes")
    fun apiVideosVideoIdLikesPost(
        @retrofit2.http.Path("video_id") videoId: String,
        @retrofit2.http.Header("Authorization") authorization: String
    ): Single<Video>
}
