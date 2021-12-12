package com.elevenine.artinstitute.ui.list

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.elevenine.artinstitute.R
import com.elevenine.artinstitute.databinding.FragmentArtListBinding
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
}