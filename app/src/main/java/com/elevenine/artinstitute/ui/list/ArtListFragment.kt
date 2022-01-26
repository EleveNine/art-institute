package com.elevenine.artinstitute.ui.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elevenine.artinstitute.R
import com.elevenine.artinstitute.databinding.FragmentArtListBinding
import com.elevenine.artinstitute.ui.list.ArtListViewModel.Companion.ART_LIST_PAGE_SIZE
import com.elevenine.artinstitute.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint


/**
 * @author Sherzod Nosirov
 * @since 08.12.2021
 */

@AndroidEntryPoint
class ArtListFragment : Fragment(R.layout.fragment_art_list) {

    private val viewModel by viewModels<ArtListViewModel>()

    private val binding by viewBinding(FragmentArtListBinding::bind)

    private var artworkAdapter: ArtListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
    }
}