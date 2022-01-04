package com.fefuproject.druzhbank.dirvalute

data class Valute(
    val isBuyUp:Boolean,
    val isResellUp:Boolean,
    val short_name_valute: String,
    val long_name_valute:String,
    val price_Buy:Float,
    val price_Resell:Float,
    )
