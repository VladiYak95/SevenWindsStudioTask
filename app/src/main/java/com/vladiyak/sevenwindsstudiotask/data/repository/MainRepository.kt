package com.vladiyak.sevenwindsstudiotask.data.repository

import com.vladiyak.sevenwindsstudiotask.data.models.location.LocationList
import com.vladiyak.sevenwindsstudiotask.data.models.menu.CoffeeList
import com.vladiyak.sevenwindsstudiotask.data.models.signup.Token
import com.vladiyak.sevenwindsstudiotask.data.models.signup.User
import com.vladiyak.sevenwindsstudiotask.data.network.coffeeapi.CoffeeApiService
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiService: CoffeeApiService
) {

    suspend fun getLocations(token: String): LocationList {
        return apiService.getLocationsList(token)
    }

    suspend fun getLocationMenu(id: String, token: String): CoffeeList {
        return apiService.getLocationMenu(id, token)
    }

    suspend fun signUp(user: User): Token {
        return apiService.signUp(user)
    }

    suspend fun login(user: User): Token {
        return apiService.login(user)
    }

}