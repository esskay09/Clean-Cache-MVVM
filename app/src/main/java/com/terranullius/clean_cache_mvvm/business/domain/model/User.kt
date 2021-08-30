package com.terranullius.clean_cache_mvvm.business.domain.model

data class User(
    val id: Int,
    val name: String,
    var cached: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (other is User) {
            if (other.id == this.id && other.cached == this.cached && other.name == this.name) return true
            return false
        } else return false
    }
}