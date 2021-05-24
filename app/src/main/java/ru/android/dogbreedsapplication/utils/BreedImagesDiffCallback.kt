package ru.android.dogbreedsapplication.utils

import androidx.recyclerview.widget.DiffUtil

class BreedImagesDiffCallback(
    private val old: List<String>,
    private val new: List<String>
) : DiffUtil.Callback(){
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldItemPosition == newItemPosition

    override fun getOldListSize(): Int = old.size

    override fun getNewListSize(): Int = new.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        old[oldItemPosition] == new[newItemPosition]
}