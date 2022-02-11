package com.elevenine.artinstitute.ui.artworks

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elevenine.artinstitute.App
import com.elevenine.artinstitute.R
import com.elevenine.artinstitute.databinding.FragmentArtListBinding
import com.elevenine.artinstitute.ui.artworks.ArtListViewModel.Companion.ART_LIST_PAGE_SIZE
import com.elevenine.artinstitute.utils.viewBinding
import javax.inject.Inject


/**
 * @author Sherzod Nosirov
 * @since 08.12.2021
 */

class ArtListFragment : Fragment(R.layout.fragment_art_list) {

    @Inject
    lateinit var viewModelFactoryCreator: ArtListViewModelFactory.Creator

    private val viewModel: ArtListViewModel by viewModels {
        viewModelFactoryCreator.create(1) //todo add category_id from navigation
    }

    private val binding by viewBinding(FragmentArtListBinding::bind)

    private var artworkAdapter: ArtListAdapter? = null

    override fun onAttach(context: Context) {
        App.getAppComponent().inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // if StateFlow is observed from the ViewModel use 'repeatOnLifecycle' method to start
        // or restart the coroutine when the Fragment is STARTED and stop it when the Fragment is
        // in the STOPPED state.
        // See https://medium.com/androiddevelopers/repeatonlifecycle-api-design-story-8670d1a7d333
        viewModel.uiState.observe(this, {
            handleUiState(it)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManager = LinearLayoutManager(requireContext())

        val recyclerViewOnScrollListener: RecyclerView.OnScrollListener =
            object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val visibleItemCount: Int = linearLayoutManager.childCount
                    val totalItemCount: Int = linearLayoutManager.itemCount
                    val firstVisibleItemPosition: Int =
                        linearLayoutManager.findFirstVisibleItemPosition()

                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= ART_LIST_PAGE_SIZE
                    ) {
                        viewModel.requestNewPage()
                    }

                }
            }

        artworkAdapter = ArtListAdapter()
        with(binding.recyclerView) {
            adapter = artworkAdapter
            layoutManager = linearLayoutManager
            addOnScrollListener(recyclerViewOnScrollListener)
        }

        viewModel.requestNewPage()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        artworkAdapter = null
    }

    private fun handleUiState(uiState: ArtListUiState) {
        artworkAdapter?.submitList(uiState.artworks)
        binding.pbInitial.visibility = if (uiState.isInitialLoading) View.VISIBLE else View.GONE

        if (uiState.showErrorToast) {
            Toast.makeText(requireContext(), "An error occurred", Toast.LENGTH_SHORT).show()
        }
    }
}