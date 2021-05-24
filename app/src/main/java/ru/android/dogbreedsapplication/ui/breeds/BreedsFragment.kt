package ru.android.dogbreedsapplication.ui.breeds

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_breeds.*
import ru.android.dogbreedsapplication.ui.breedimages.TAGSubbreedImagesFragment

import ru.bey_sviatoslav.android.dogbreedsapplication.R
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.RecyclerState
import ru.bey_sviatoslav.android.dogbreedsapplication.utils.Coordinator
import ru.bey_sviatoslav.android.dogbreedsapplication.viewmodel.BreedsViewModel

const val TAGBreedsFragment = "BreedsFragment"
class BreedsFragment : Fragment() {
    private lateinit var viewModel: BreedsViewModel
    private lateinit var adapter: BreedsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_breeds, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)
            .get(BreedsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        viewModel.viewStateData.observe(this.viewLifecycleOwner, Observer {
            val state = when {
                it.isBreedsLoading -> RecyclerState.LOADING
                it.errorLoadingBreeds != null -> RecyclerState.ERROR
                it.breeds.isEmpty() -> RecyclerState.EMPTY
                else -> RecyclerState.ITEMS
            }
            val isRefreshable = !(it.isBreedsLoading || it.errorLoadingBreeds != null)

            refresher.isEnabled = isRefreshable
            refresher.isRefreshing = it.isRefreshLoading
            adapter.setItems(it.breeds.toList(), state)

            if (it.errorRefreshLoading != null)
                Snackbar.make(
                    fragment_breeds,
                    it.errorRefreshLoading.localizedMessage ?: "",
                    Snackbar.LENGTH_SHORT
                ).show()
        })
    }

    private fun initViews() {
        Log.d(TAGBreedsFragment,"Инициализировали View")
        refresher.setOnRefreshListener {
            viewModel.onRefresh()
        }
        refresher.setColorSchemeResources(R.color.colorAccent)

        adapter = BreedsAdapter({
            Coordinator.onBreedClicked(
                requireActivity().supportFragmentManager,
                it.first,
                it.second.size
            )
        }, { viewModel.onRetry() })

        breeds_recycler.adapter = adapter
        breeds_recycler.layoutManager = LinearLayoutManager(this.activity)
        breeds_recycler.setHasFixedSize(true)
    }

    companion object {
        fun newInstance() = BreedsFragment()
    }

}
