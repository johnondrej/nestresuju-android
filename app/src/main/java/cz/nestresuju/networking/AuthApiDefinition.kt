package cz.nestresuju.networking

import cz.nestresuju.model.entities.api.AuthResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Definition of API endpoints for authentication.
 */
interface AuthApiDefinition {

    @FormUrlEncoded
    @POST("connect/token")
    suspend fun login(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("grant_type") grantType: String,
        @Field("scope") scope: String,
        @Field("username") username: String,
        @Field("password") password: String
    ): AuthResponse

    @FormUrlEncoded
    @POST("connect/token")
    suspend fun loginWithRefreshToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("grant_type") grantType: String,
        @Field("scope") scope: String,
        @Field("refresh_token") refreshToken: String
    ): AuthResponse
}