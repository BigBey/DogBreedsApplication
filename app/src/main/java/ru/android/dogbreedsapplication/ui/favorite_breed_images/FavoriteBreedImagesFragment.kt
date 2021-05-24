package ru.android.dogbreedsapplication.ui.favorite_breed_images

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_breed_images.*
import ru.android.dogbreedsapplication.ui.breeds.TAGBreedsFragment

import ru.bey_sviatoslav.android.dogbreedsapplication.R
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.RecyclerState
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.breedimages.BreedImagesAdapter
import ru.bey_sviatoslav.android.dogbreedsapplication.utils.Coordinator
import ru.bey_sviatoslav.android.dogbreedsapplication.utils.sharePhoto
import ru.bey_sviatoslav.android.dogbreedsapplication.viewmodel.FavoriteBreedImagesViewModel
import java.lang.Exception
const val TAGBreedImagesFragment = "BreedsImagesFragment"


class FavoriteBreedImagesFragment : Fragment() {
    private lateinit var viewModel: FavoriteBreedImagesViewModel
    private lateinit var adapter: BreedImagesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_breed_images, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)
            .get(FavoriteBreedImagesViewModel::class.java)
        viewModel.setFavoriteBreedImagesRepository(context!!)
        viewModel.onRetry(arguments?.getString("breedname") ?: "Breed name")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        viewModel.viewStateData.observe(this.viewLifecycleOwner, Observer {
            val state = when {
                it.isFavoriteBreedImagesLoading -> RecyclerState.LOADING
                it.errorLoadingFavoriteBreedImages != null -> RecyclerState.ERROR
                it.favoriteBreedImages.isEmpty() -> RecyclerState.EMPTY
                else -> RecyclerState.ITEMS
            }
            val isRefreshable =
                !(it.isFavoriteBreedImagesLoading || it.errorLoadingFavoriteBreedImages != null)

            refresher_breed_images.isEnabled = isRefreshable
            refresher_breed_images.isRefreshing = it.isRefreshLoading
            adapter.setItems(it.favoriteBreedImages, state)

            if (it.errorRefreshLoading != null)
                Snackbar.make(
                    fragment_breed_images,
                    it.errorRefreshLoading.localizedMessage ?: "",
                    Snackbar.LENGTH_SHORT
                ).show()
        })
    }

    private fun initViews() {
        Log.d(TAGBreedImagesFragment,"Инициализировали View")
        btn_back_to_breeds_from_images.setOnClickListener {
            Coordinator.onFragmentPop(requireActivity().supportFragmentManager)
        }

        imgvw_share.setOnClickListener {
            MaterialAlertDialogBuilder(context)
                .setTitle(resources.getString(R.string.share_dialog_title))
                .setNegativeButton(resources.getString(R.string.share_dialog_cancel_button)) { dialog, which ->
                    //Cancel
                }
                .setPositiveButton(resources.getString(R.string.share_dialog_confirm_button)) { dialog, which ->
                    try {
                        sharePhoto(
                            adapter.getCurrentItem(viewpager_breed_images.currentItem),
                            context!!
                        )
                    } catch (e: Exception) {
                        MaterialAlertDialogBuilder(context)
                            .setTitle(resources.getString(R.string.error_title))
                            .setMessage(resources.getString(R.string.error_text))
                            .setPositiveButton(resources.getString(R.string.ok)) { dialog, which -> }
                            .show()
                    }
                }
                .show()
        }

        refresher_breed_images.setOnRefreshListener {
            viewModel.onRefresh(arguments?.getString("breedname") ?: "Breed name")
        }
        refresher_breed_images.setColorSchemeResources(R.color.colorAccent)

        adapter = BreedImagesAdapter({
        }, {viewModel.onRetry(
            arguments?.getString("breedname") ?: "Breed name"
        )}, this)

        viewpager_breed_images.adapter = adapter
        viewpager_breed_images.orientation = ViewPager2.ORIENTATION_VERTICAL

        val breedname = arguments?.getString("breedname") ?: "Breed name"
        toolbar_title_breed_images.text = breedname[0].toUpperCase() + breedname.substring(1)
        toolbar_title_breed_images.textSize = 15F
        toolbar_title_back_to_breeds.text = resources.getString(R.string.favorite_breeds_title)
        toolbar_title_back_to_breeds.textSize = 15F
    }

    companion object {
        fun newInstance(breedname: String): FavoriteBreedImagesFragment {
            val fragment = FavoriteBreedImagesFragment()
            val args = Bundle().apply {
                putString("breedname", breedname)
            }
            return fragment.apply { arguments = args }
        }
    }
}
