package com.terranullius.clean_cache_mvvm.business.interactors

import com.terranullius.clean_cache_mvvm.business.domain.model.User
import javax.inject.Inject

class UserInteractors @Inject constructor(
    private val getUsers: GetUsers,
    private val getSavedUsers: GetSavedUsers,
    private val deleteUser: DeleteUser,
    private val insertUser: InsertUser
) {

    suspend fun getUsers(page: Int) = getUsers.getUsers(page)

    suspend fun getSavedUsers() = getSavedUsers.getSavedUsers()

    suspend fun deleteUser(user: User) = deleteUser.deleteUser(user)

    suspend fun insertUser(user: User) = insertUser.insertUser(user)

}