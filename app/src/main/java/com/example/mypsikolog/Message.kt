package com.example.mypsikolog

class Message {
    var message: String? = null
    var timeSent: String? = null
    var senderId: String? = null

    constructor(){}

    constructor(message: String?, senderId: String?) {
        this.message = message
        this.senderId = senderId
    }

    constructor(message: String?, timeSent: String?, senderId: String?) {
        this.message = message
        this.timeSent = timeSent
        this.senderId = senderId
    }
}