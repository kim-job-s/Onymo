package com.onymo.app.repository

import com.onymo.app.model.User

class LoginRepository {

    private val registeredUser = User(
        name = "김기태",
        email = "kitae@sasac.co.kr",
        password = "1234",
        companyName = "새싹",
        department = "IT",
        position = "팀장",
        job = "개발자"
    )

    fun authenticate(email: String, password: String): User? {
        return if (email == registeredUser.email && password == registeredUser.password) {
            registeredUser
        } else {
            null
        }
    }
}