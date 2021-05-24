package ru.android.dogbreedsapplication.ui.breedimages

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.android.dogbreedsapplication.repo.TAGSubbreed
import ru.bey_sviatoslav.android.dogbreedsapplication.R
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.*
import ru.bey_sviatoslav.android.dogbreedsapplication.utils.BreedImagesDiffCallback

const val TAGBreedImagesAdapter = "BreedImagesAdapter"
class BreedImagesAdapter(
    private val itemLikeListener: (String) -> Unit,
    private val reloadListener: () -> Unit,
    private val fragment: Fragment
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items = mutableListOf<String>()
    private var state: RecyclerState = RecyclerState.LOADING

    private val layoutId: Int
        get() =
            when (state) {
                RecyclerState.LOADING -> R.layout.item_loading
                RecyclerState.ITEMS -> R.layout.item_breed_image
                RecyclerState.EMPTY -> R.layout.item_empty
                else -> R.layout.item_error
            }
    private val buttonId: Int
        get() = R.id.retry_button

    fun getCurrentItem(position: Int): String{

        Log.d(TAGBreedImagesAdapter,"Получили item")
        return items[position]
    }

    fun setItems(items: List<String>, state: RecyclerState) {
        if (state == RecyclerState.ITEMS) {
            if (this.state == RecyclerState.ITEMS) {
                this.state = state
            } else {
                this.state = state
                notifyItemRemoved(0)
            }
            val diffResult =
                DiffUtil.calculateDiff(BreedImagesDiffCallback(this.items.toList(), items.toList()))
            this.items.clear()
            this.items.addAll(items)
            diffResult.dispatchUpdatesTo(this)
        } else {
            if (this.state == RecyclerState.ITEMS) {
                notifyItemRangeRemoved(0, itemCount)
                this.items.clear()
                this.state = state
                notifyItemInserted(0)
            } else {
                this.state = state
                notifyItemChanged(0)
            }
        }

        Log.d(TAGBreedImagesAdapter,"Положили item")

    }

    override fun getItemCount(): Int {

        Log.d(TAGBreedImagesAdapter,"Получили количество элемнтов")
        return if (items.isNotEmpty()) items.size else 1
    }

    override fun getItemViewType(position: Int) : Int {
        Log.d(TAGBreedImagesAdapter,"Получили количество элемнтов по типу")
       return if (position == 0) state.viewType else position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (state) {
            RecyclerState.ITEMS -> return BreedImageViewHolder(
                view = LayoutInflater.from(parent.context).inflate(
                    layoutId,
                    parent,
                    false
                ),
                itemListener = itemLikeListener,
                fragment = fragment
            )
            RecyclerState.LOADING -> return LoadingViewHolder(
                view = LayoutInflater.from(parent.context).inflate(
                    layoutId,
                    parent,
                    false
                )
            )
            RecyclerState.EMPTY -> return LoadingViewHolder(
                view = LayoutInflater.from(parent.context).inflate(
                    layoutId,
                    parent,
                    false
                )
            )
            RecyclerState.ERROR -> return MessageViewHolder(
                view = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_error,
                    parent,
                    false
                ),
                buttonId = buttonId,
                listener = reloadListener
            )
            else -> return MessageViewHolder(
                view = LayoutInflater.from(parent.context).inflate(
                    layoutId,
                    parent,
                    false
                ),
                buttonId = buttonId,
                listener = reloadListener
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BreedImageViewHolder)
            holder.bind(items[position])
    }
}