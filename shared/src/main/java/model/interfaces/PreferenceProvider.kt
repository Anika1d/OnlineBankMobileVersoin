package model.interfaces

interface IPreferenceProvider {
    var token: String?
    fun updateToken(newToken: String?)
}