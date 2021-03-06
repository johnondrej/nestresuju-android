package cz.nestresuju.networking

import com.squareup.moshi.Moshi
import cz.nestresuju.model.errors.*
import cz.nestresuju.model.errors.constants.AuthErrorCodes
import cz.nestresuju.model.errors.constants.HttpErrorCodes
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

    override fun mapException(exception: Throwable): Throwable = when (exception) {
        is DomainException -> exception // already mapped exception came from OAuth manager, do not map it again
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
                        AuthErrorCodes.INVALID_GRANT -> {
                            when (error.errorDescription) {
                                AuthErrorCodes.DESCRIPTION_INVALID_CREDENTIALS -> InvalidCredentialsException()
                                else -> null
                            }
                        }
                        else -> null
                    }
                    else -> null
                } ?: ServerException(exception.code(), errorCode = null, description = null) as Throwable
            } ?: ServerException(exception.code(), errorCode = null, description = null) as Throwable
        }
        else -> UnknownException(exception)
    }
}