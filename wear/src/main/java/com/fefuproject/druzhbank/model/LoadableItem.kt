package com.fefuproject.druzhbank.model

class LoadableItem<T>(obj: T) {
    val value = obj
    var isLoading = true
}

fun <T> List<LoadableItem<T?>>.addLoadingItems(n: Int) : List<LoadableItem<T?>> {
    val newList = this.toMutableList()
    repeat(n) {
        newList.add(LoadableItem(null))
    }
    return newList
}