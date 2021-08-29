package com.terranullius.clean_cache_mvvm.business.interactors.common

import javax.inject.Inject

class UserInteractors @Inject constructor(
    private val getUsers: GetUsers
) {

    fun getUsers(page: Int) = getUsers.getUsers(page)

}