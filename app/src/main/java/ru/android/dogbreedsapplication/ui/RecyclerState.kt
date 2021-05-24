package ru.android.dogbreedsapplication.ui

enum class RecyclerState(val viewType: Int) {
    LOADING(4000),
    ITEMS(0),
    EMPTY(5000),
    ERROR(6000)
}