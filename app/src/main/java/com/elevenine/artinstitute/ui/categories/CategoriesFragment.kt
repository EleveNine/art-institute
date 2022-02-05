package com.elevenine.artinstitute.ui.categories

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.elevenine.artinstitute.R
import com.elevenine.artinstitute.databinding.FragmentCategoriesBinding
import com.elevenine.artinstitute.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Sherzod Nosirov
 * @since 05.02.2022
 */

@AndroidEntryPoint
class CategoriesFragment : Fragment(R.layout.fragment_categories) {

    private val viewModel by viewModels<CategoriesViewModel>()

    private val binding by viewBinding(FragmentCategoriesBinding::bind)

    private var categoriesAdapter: CategoriesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.uiState.observe(this, { state ->
            handleUiState(state)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManager = LinearLayoutManager(requireContext())

        categoriesAdapter = CategoriesAdapter()
        with(binding.recyclerView) {
            adapter = categoriesAdapter
            layoutManager = linearLayoutManager
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        categoriesAdapter = null
    }

    private fun handleUiState(state: CategoriesUiState) {
        categoriesAdapter?.submitList(state.categories)

        binding.pbInitial.visibility = if (state.isInitialLoading) View.VISIBLE else View.GONE

        if (state.showErrorToast) {
            Toast.makeText(requireContext(), "An error occurred", Toast.LENGTH_SHORT).show()
        }
    }

}