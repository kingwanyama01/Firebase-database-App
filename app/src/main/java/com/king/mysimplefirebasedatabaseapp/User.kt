package com.king.mysimplefirebasedatabaseapp

class User {
    var name:String = ""
    var email:String = ""
    var age:String = ""

    constructor(name:String, email:String, age:String){
        this.name = name
        this.email = email
        this.age = age
    }

    constructor(){}
}