package cz.nestresuju.networking

import com.squareup.moshi.Moshi
import cz.ackee.ackroutine.OAuthCallInterceptor
import cz.ackee.ackroutine.OAuthManager
import cz.ackee.ackroutine.core.OAuthCredentials
import cz.ackee.retrofitadapter.AckroutineCallAdapterFactory
import cz.nestresuju.BuildConfig
import cz.nestresuju.common.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

/**
 * Koin networking modules.
 */

const val AUTH_OKHTTP = "okhttp_auth"
const val AUTH_RETROFIT = "retrofit_auth"
const val AUTH_EXCEPTION_MAPPER = "exception_mapper_auth"

val networkingModule = module {

    single {
        OkHttpClient.Builder()
            .addNetworkInterceptor(get<OAuthManager>().provideAuthInterceptor())
            .addNetworkInterceptor(
                HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
            ).build()
    }

    single(named(AUTH_OKHTTP)) {
        OkHttpClient.Builder()
            .addNetworkInterceptor(
                HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
            ).build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(Constants.API_BASE_URL)
            .addCallAdapterFactory(
                AckroutineCallAdapterFactory(
                    OAuthCallInterceptor(oAuthManager = get()),
                    ErrorMappingInterceptor(exceptionMapper = get())
                )
            )
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .client(get())
            .build()
    }

    single(named(AUTH_RETROFIT)) {
        Retrofit.Builder()
            .baseUrl(Constants.API_AUTH_BASE_URL)
            .addCallAdapterFactory(
                AckroutineCallAdapterFactory(
                    ErrorMappingInterceptor(exceptionMapper = get(named(AUTH_EXCEPTION_MAPPER)))
                )
            )
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .client(get(named(AUTH_OKHTTP)))
            .build()
    }

    single {
        Moshi.Builder()
            .add(Rfc3339ZonedDateTimeJsonAdapter)
            .build()
    }

    single {
        OAuthManager(
            context = androidContext(),
            refreshTokenAction = { refreshToken ->
                // TODO: update when API returns refresh token
                object : OAuthCredentials {
                    override val accessToken = ""
                    override val expiresIn: Long = 0
                    override val refreshToken: String = ""
                }
            },
            onRefreshTokenFailed = { /* TODO: implement logout logic */ }
        )
    }

    single { get<Retrofit>(named(AUTH_RETROFIT)).create<AuthApiDefinition>() }

    single { get<Retrofit>().create<ApiDefinition>() }

    factory<ApiExceptionMapper> { GeneralApiExceptionMapper(moshi = get()) }

    factory<ApiExceptionMapper>(named(AUTH_EXCEPTION_MAPPER)) { AuthApiExceptionMapper(moshi = get()) }
}