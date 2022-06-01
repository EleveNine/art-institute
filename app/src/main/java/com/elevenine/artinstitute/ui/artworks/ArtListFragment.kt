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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elevenine.artinstitute.App
import com.elevenine.artinstitute.R
import com.elevenine.artinstitute.databinding.FragmentArtListBinding
import com.elevenine.artinstitute.domain.interactor.FetchPagedArtworksInteractor
import com.elevenine.artinstitute.ui.artworks.ArtListViewModel.Companion.ART_LIST_PAGE_SIZE
import com.elevenine.artinstitute.utils.applyDefaultInsets
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
        val args by navArgs<ArtListFragmentArgs>()
        viewModelFactoryCreator.create(args.categoryId)
    }

    private val binding by viewBinding(FragmentArtListBinding::bind)

    private val artListAdapter: ArtListAdapter?
        get() = binding.recyclerView.adapter as? ArtListAdapter

    /**
     * Observer that listens to the change in the adapter items. In case new items are added to
     * the top of the list, then the automatic scroll to the top must take place.
     */
    private val artListAdapterDataObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            if (positionStart == 0) binding.recyclerView.scrollToPosition(0)
        }
    }


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

        applyDefaultInsets(binding.root)

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
                        && totalItemCount >= FetchPagedArtworksInteractor.PAGE_SIZE
                    ) {
                        viewModel.requestNewPage()
                    }

                }
            }

        binding.recyclerView.run {
            adapter = ArtListAdapter()
            layoutManager = linearLayoutManager
            addOnScrollListener(recyclerViewOnScrollListener)
        }
    }

    override fun onResume() {
        super.onResume()

        // register the transactionsAdapter observer to scroll to top every time new items are
        // added to the top of the list
        artListAdapter?.registerAdapterDataObserver(artListAdapterDataObserver)
    }

    override fun onPause() {
        super.onPause()

        // unregister the transactionsAdapter observer to avoid losing scroll position on config
        // change or when navigating back to this Fragment
        artListAdapter?.unregisterAdapterDataObserver(artListAdapterDataObserver)
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

        artListAdapter?.submitList(uiState.artworks)
        binding.pbInitial.visibility = if (uiState.isLoading) View.VISIBLE else View.GONE
    }
}