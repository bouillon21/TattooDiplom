package com.diplom.tattoo.data

data class User(
    val firstName: String,
    val lastName: String
) {
    var email: String? = null
    var photoUrl: String? = "null"

    constructor(
        firstName: String,
        lastName: String,
        email: String,
        photoUrl: String?
    ) : this(firstName, lastName) {
        this.email = email;
        if (photoUrl != null)
            this.photoUrl = photoUrl
    }
}
