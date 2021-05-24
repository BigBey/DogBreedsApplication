package ru.android.dogbreedsapplication.ui.breedimages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_breed_images.*
import ru.bey_sviatoslav.android.dogbreedsapplication.R
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.RecyclerState
import ru.bey_sviatoslav.android.dogbreedsapplication.utils.Coordinator
import ru.bey_sviatoslav.android.dogbreedsapplication.utils.sharePhoto
import ru.bey_sviatoslav.android.dogbreedsapplication.viewmodel.SubbreedImagesViewModel

const val TAGSubbreedImagesFragment = "SubbreedImagesFragment"

class SubbreedImagesFragment : Fragment() {
    private lateinit var viewModel: SubbreedImagesViewModel
    private lateinit var adapter: BreedImagesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_breed_images, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)
            .get(SubbreedImagesViewModel::class.java)
        viewModel.onRetry(
            arguments?.getString("breedname") ?: "Breed name",
            arguments?.getString("subbreedname") ?: "Subbreed name"
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        viewModel.viewStateData.observe(this.viewLifecycleOwner, Observer {
            val state = when {
                it.isSubbreedImagesLoading -> RecyclerState.LOADING
                it.errorLoadingSubbreedImages != null -> RecyclerState.ERROR
                it.subbreedImages.isEmpty() -> RecyclerState.EMPTY
                else -> RecyclerState.ITEMS
            }
            val isRefreshable =
                !(it.isSubbreedImagesLoading || it.errorLoadingSubbreedImages != null)

            refresher_breed_images.isEnabled = isRefreshable
            refresher_breed_images.isRefreshing = it.isRefreshLoading
            adapter.setItems(it.subbreedImages, state)

            if (it.errorRefreshLoading != null)
                Snackbar.make(
                    fragment_breed_images,
                    it.errorRefreshLoading.localizedMessage ?: "",
                    Snackbar.LENGTH_SHORT
                ).show()
        })
    }

    private fun initViews() {
        Log.d(TAGSubbreedImagesFragment,"Инициализировали View")

        btn_back_to_breeds_from_images.setOnClickListener {
            Coordinator.onFragmentPop(requireActivity().supportFragmentManager)
        }

        imgvw_share.setOnClickListener {
            Log.d(TAGSubbreedImagesFragment,"Нажали на imgvw_share")

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
            viewModel.onRefresh(
                arguments?.getString("breedname") ?: "Breed name",
                arguments?.getString("subbreedname") ?: "Subbreed name"
            )
        }
        refresher_breed_images.setColorSchemeResources(R.color.colorAccent)

        adapter = BreedImagesAdapter({},
            {
                viewModel.onRetry(
                    arguments?.getString("breedname") ?: "Breed name",
                    arguments?.getString("subbreedname") ?: "Subbreed name"
                )
            },
            this
        )

        viewpager_breed_images.adapter = adapter
        viewpager_breed_images.orientation = ViewPager2.ORIENTATION_VERTICAL

        val subbreedname = arguments?.getString("subbreedname") ?: "Subbreed name"
        toolbar_title_breed_images.text = subbreedname[0].toUpperCase() + subbreedname.substring(1)
    }

    companion object {
        fun newInstance(breedname: String, subbreedname: String): SubbreedImagesFragment {
            val fragment = SubbreedImagesFragment()
            val args = Bundle().apply {
                putString("breedname", breedname)
                putString("subbreedname", subbreedname)
            }
            return fragment.apply { arguments = args }
        }
    }
}
