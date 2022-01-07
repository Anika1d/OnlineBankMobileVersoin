package com.fefuproject.druzhbank.dirprofile.dircard

data class Cards(
    val whatBank: String,  // когда будут картинки  сделать whatImageBank:Image
    val numberCard: String,
    var typeCard: String,  //Дебетовая, кредитка и тд
    val valueCard: String,
    var isBlocked: Boolean,// баланс или не баланс, я пока не понял ,что там хотят


)
