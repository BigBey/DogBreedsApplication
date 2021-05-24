package ru.android.dogbreedsapplication.vo

import org.json.JSONObject
import java.util.*

data class BreedsResult (
    val message: SortedMap<String, List<String>>,
    val status: String
)