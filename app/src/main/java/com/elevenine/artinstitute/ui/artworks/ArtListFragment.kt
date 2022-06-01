package com.elevenine.artinstitute.ui.artworks

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elevenine.artinstitute.App
import com.elevenine.artinstitute.R
import com.elevenine.artinstitute.databinding.FragmentArtListBinding
import com.elevenine.artinstitute.ui.artworks.ArtListViewModel.Companion.ART_LIST_PAGE_SIZE
import com.elevenine.artinstitute.utils.viewBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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

        lifecycleScope.launch {
            viewModel.uiState
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    handleUiState(it)
                }
        }
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
        /* SHOW UI MESSAGES */
        if (uiState.toastMessages.isNotEmpty()) {
            uiState.toastMessages.forEach {
                val message = it.message ?: getString(it.messageResId)
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                viewModel.onToastMessageShown(it.id)
            }
        }

        artworkAdapter?.submitList(uiState.artworks)
        binding.pbInitial.visibility = if (uiState.isLoading) View.VISIBLE else View.GONE
    }
}