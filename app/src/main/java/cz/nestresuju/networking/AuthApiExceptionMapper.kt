package cz.nestresuju.networking

import com.squareup.moshi.Moshi
import cz.nestresuju.model.errors.*
import retrofit2.HttpException
import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Exception mapper for Auth API.
 */
class AuthApiExceptionMapper(
    private val moshi: Moshi
) : ApiExceptionMapper {

    override fun mapException(exception: Throwable) = when (exception) {
        is UnknownHostException, is SocketTimeoutException -> InternetConnectionException()
        is HttpException -> {
            val errorResponse = try {
                exception.response()?.errorBody()?.string()?.let {
                    val moshiAdapter = moshi.adapter(AuthErrorResponse::class.java)
                    moshiAdapter.fromJson(it)
                }
            } catch (e: Exception) {
                Timber.e(e)
                null
            }

            errorResponse?.let { error ->
                when (exception.code()) {
                    HttpErrorCodes.BAD_REQUEST -> when (error.error) {
                        AuthErrorCodes.INVALID_GRANT -> InvalidCredentialsException()
                        else -> null
                    }
                    else -> null
                } ?: ServerException(exception.code(), errorCode = null, description = null)
            } ?: ServerException(exception.code(), errorCode = null, description = null)
        }
        else -> UnknownException(exception)
    }
}