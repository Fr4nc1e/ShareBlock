package com.code.block.feature.auth.data.repository

import android.content.SharedPreferences
import com.code.block.R
import com.code.block.core.utils.* // ktlint-disable no-wildcard-imports
import com.code.block.feature.auth.data.source.remote.AuthApi
import com.code.block.feature.auth.data.source.remote.request.CreateAccountRequest
import com.code.block.feature.auth.data.source.remote.request.LoginRequest
import com.code.block.feature.auth.domain.repository.AuthRepository
import retrofit2.HttpException
import java.io.IOException

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val sharedPreferences: SharedPreferences
) : AuthRepository {

    // Register
    override suspend fun register(
        email: String,
        username: String,
        password: String
    ): RegisterResource {
        val request = CreateAccountRequest(
            email = email,
            username = username,
            password = password
        )

        return try {
            val response = api.register(request)
            if (response.successful) {
                response.message?.let {
                    Resource.Success(uiText = UiText.CallResponseText(it))
                } ?: Resource.Success(uiText = UiText.StringResource(R.string.register_successfully))
            } else {
                response.message?.let {
                    Resource.Error(uiText = UiText.CallResponseText(it))
                } ?: Resource.Error(uiText = UiText.StringResource(R.string.unknown_error))
            }
        } catch (e: IOException) {
            Resource.Error(uiText = UiText.StringResource(R.string.fail_to_connect))
        } catch (e: HttpException) {
            Resource.Error(uiText = UiText.StringResource(R.string.fail_to_connect))
        }
    }

    // Login
    override suspend fun login(
        email: String,
        password: String
    ): LoginResource {
        val request = LoginRequest(
            email = email,
            password = password
        )
        return try {
            val response = api.login(request)
            if (response.successful) {
                response.data?.token?.let { token ->
                    sharedPreferences.edit()
                        .putString(Constants.JWT_TOKEN, token)
                        .apply()
                }
                Resource.Success(uiText = UiText.StringResource(R.string.login_successfully))
            } else {
                response.message?.let { message ->
                    Resource.Error(uiText = UiText.CallResponseText(message))
                } ?: Resource.Error(uiText = UiText.StringResource(R.string.unknown_error))
            }
        } catch (e: IOException) {
            Resource.Error(uiText = UiText.StringResource(R.string.fail_to_connect))
        } catch (e: HttpException) {
            Resource.Error(uiText = UiText.StringResource(R.string.fail_to_connect))
        }
    }

    // Authentication
    override suspend fun authenticate(): AuthenticationResource {
        return try {
            api.authenticate()
            Resource.Success(uiText = null)
        } catch (e: IOException) {
            Resource.Error(uiText = UiText.StringResource(R.string.fail_to_connect))
        } catch (e: HttpException) {
            Resource.Error(uiText = UiText.StringResource(R.string.fail_to_connect))
        }
    }
}
