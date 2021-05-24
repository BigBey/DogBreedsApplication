package ru.android.dogbreedsapplication.ui.subbreeds

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_subbreeds.*

import ru.bey_sviatoslav.android.dogbreedsapplication.R
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.RecyclerState
import ru.bey_sviatoslav.android.dogbreedsapplication.utils.Coordinator
import ru.bey_sviatoslav.android.dogbreedsapplication.viewmodel.SubbreedsViewModel


class SubbreedsFragment : Fragment() {
    private lateinit var viewModel: SubbreedsViewModel
    private lateinit var adapter: SubbreedsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subbreeds, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)
            .get(SubbreedsViewModel::class.java)
        viewModel.onRetry(arguments?.getString("breedname") ?: "Breed name")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        viewModel.viewStateData.observe(this.viewLifecycleOwner, Observer {
            val state = when {
                it.isBreedsLoading -> RecyclerState.LOADING
                it.errorLoadingBreeds != null -> RecyclerState.ERROR
                it.subbreeds.isEmpty() -> RecyclerState.EMPTY
                else -> RecyclerState.ITEMS
            }
            val isRefreshable = !(it.isBreedsLoading || it.errorLoadingBreeds != null)

            refresher_subbreeds.isEnabled = isRefreshable
            refresher_subbreeds.isRefreshing = it.isRefreshLoading
            adapter.setItems(it.subbreeds, state)

            if (it.errorRefreshLoading != null)
                Snackbar.make(
                    fragment_subbreeds,
                    it.errorRefreshLoading.localizedMessage ?: "",
                    Snackbar.LENGTH_SHORT
                ).show()
        })
    }

    private fun initViews() {
        btn_back_to_breeds.setOnClickListener {
            Coordinator.onFragmentPop(requireActivity().supportFragmentManager)
        }

        refresher_subbreeds.setOnRefreshListener {
            viewModel.onRefresh(arguments?.getString("breedname") ?: "Breed name")
        }
        refresher_subbreeds.setColorSchemeResources(R.color.colorAccent)

        adapter = SubbreedsAdapter({
            Coordinator.onSubbreedClicked(
                requireActivity().supportFragmentManager,
                arguments?.getString("breedname") ?: "Breed name",
                it
            )
        }, { viewModel.onRetry(arguments?.getString("breedname") ?: "Breed name") })

        recycler_subbreeds.adapter = adapter
        recycler_subbreeds.layoutManager = LinearLayoutManager(this.activity)
        recycler_subbreeds.setHasFixedSize(true)

        val breedname = arguments?.getString("breedname") ?: "Breed name"
        toolbar_title_subbreeds.text = breedname[0].toUpperCase() + breedname.substring(1)
    }

    companion object {
        fun newInstance(breedname: String): SubbreedsFragment {
            val fragment = SubbreedsFragment()
            val args = Bundle().apply {
                putString("breedname", breedname)
            }
            return fragment.apply { arguments = args }
        }
    }
}
