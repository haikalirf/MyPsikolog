package com.example.mypsikolog

class User {
    var profilePicture: String? = null
    var displayName: String? = null
    var email: String? = null
    var uid: String? = null
    var isPsikolog: Boolean? = null
    var chatPrice: Int? = null
    var appointmentPrice: Int? = null
    var rating: Float? = null
    var address: String? = null
    var about: String? = null

    constructor()

    constructor(name: String?, email: String?, uid: String?) {
        this.displayName = name
        this.email = email
        this.uid = uid
        this.isPsikolog = false
        this.chatPrice = 0
        this.appointmentPrice = 0
        this.rating = 0f
        this.address = ""
        this.about = ""
    }
}