package ru.android.dogbreedsapplication.utils

import androidx.recyclerview.widget.DiffUtil

class BreedsDiffCallback(
    private val old: List<Pair<String, List<String>>>,
    private val new: List<Pair<String, List<String>>>
) : DiffUtil.Callback(){
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        old[oldItemPosition].first == new[newItemPosition].first

    override fun getOldListSize(): Int = old.size

    override fun getNewListSize(): Int = new.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        old[oldItemPosition] == new[newItemPosition]
}