package io.github.uditkarode.ididit.models

class Credential(a: String, b: String, c: String) {
    var username: String = a
    var password: String = b
    var errors: String = c

    fun isErrorFree() = errors == "" || errors == " "
}

class CredentialFactory(val user: String, val pass: String) {
    private var error: String = ""

    fun makeCredentials(): Credential {
        error = ""
        if (user.length < 4) error += "Username cannot be shorter than 4 characters!"
        if (pass.length < 6) error += "\nPassword cannot contain less than 6 characters!"
        if (user.contains(" ")) error += "\nUsername cannot contain spaces!"
        return Credential(user.toLowerCase(), pass, error)
    }
}