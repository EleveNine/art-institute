package com.elevenine.artinstitute.ui.artworks

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.elevenine.artinstitute.App
import com.elevenine.artinstitute.R
import com.elevenine.artinstitute.databinding.FragmentArtListBinding
import com.elevenine.artinstitute.ui.list.adapter.ArtListAdapter
import com.elevenine.artinstitute.ui.list.item_decorations.SpaceHorizontalDividerDecoration
import com.elevenine.artinstitute.ui.list.item_decorations.SpaceVerticalDividerDecoration
import com.elevenine.artinstitute.utils.dp
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

        applyFullScreenInsets()

        val gridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        gridLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE

        val recyclerViewOnScrollListener: RecyclerView.OnScrollListener =
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val visibleItemCount = gridLayoutManager.childCount
                    val totalItemCount = gridLayoutManager.itemCount
                    var firstVisibleItems: IntArray? = null

                    firstVisibleItems =
                        gridLayoutManager.findFirstVisibleItemPositions(firstVisibleItems)

                    var pastVisibleItems = 0
                    if (firstVisibleItems != null && firstVisibleItems.isNotEmpty()) {
                        pastVisibleItems = firstVisibleItems[0]
                    }
                    if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                        viewModel.requestNewPage()
                    }

                }
            }

        binding.recyclerView.run {
            adapter = ArtListAdapter()
            layoutManager = gridLayoutManager
            addOnScrollListener(recyclerViewOnScrollListener)
            addItemDecoration(SpaceVerticalDividerDecoration(8.dp))
            addItemDecoration(SpaceHorizontalDividerDecoration(8.dp))
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

    override fun onDestroyView() {
        ViewCompat.setOnApplyWindowInsetsListener(requireActivity().window.decorView, null)
        super.onDestroyView()
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


    private fun applyFullScreenInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(requireActivity().window.decorView) { _, insets ->

            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            val navBarHeight = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom

            binding.recyclerView.updatePadding(0, statusBarHeight, 0, navBarHeight + 16.dp)

            insets
        }
    }
}