package com.example.mypsikolog

class User {
    var displayName: String? = null
    var email: String? = null
    var uid: String? = null

    constructor() {}

    constructor(name: String?, email: String?, uid: String?) {
        this.displayName = name
        this.email = email
        this.uid = uid

        // stopped at 50:40
    }
}