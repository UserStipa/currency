package com.userstipa.currency.ui.search_currency

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.color.MaterialColors
import com.google.android.material.transition.MaterialContainerTransform
import com.userstipa.currency.App
import com.userstipa.currency.R
import com.userstipa.currency.databinding.FragmentSearchBinding
import com.userstipa.currency.ui.uitls.OnTransitionEnd
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: SearchViewModelFactory
    private val viewModel by viewModels<SearchViewModel> { viewModelFactory }
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var _adapter: SearchAdapter? = null
    private val adapter get() = _adapter!!


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        viewModel.fetchData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val isFirstViewCreated = (savedInstanceState == null)
        setAdapter()
        setUi(isFirstViewCreated, view)
    }

    private fun setAdapter() {
        _adapter = SearchAdapter(
            onClickAddCurrency = { viewModel.addCurrency(it) },
            onClickRemoveCurrency = { viewModel.removeCurrency(it) }
        )
        binding.apply {
            list.layoutManager = LinearLayoutManager(requireContext())
            list.adapter = adapter
        }
    }

    private fun setUi(isFirstViewCreated: Boolean, view: View) {
        binding.apply {
            root.transitionName = getString(R.string.transition_home_to_search)
            update.setOnClickListener { viewModel.fetchData() }
        }
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = resources.getInteger(R.integer.duration_transitions_animation).toLong()
            scrimColor = Color.TRANSPARENT
            containerColor = MaterialColors.getColor(view, com.google.android.material.R.attr.colorSurface)
            startContainerColor = MaterialColors.getColor(view, com.google.android.material.R.attr.colorSurface)
            endContainerColor = MaterialColors.getColor(view, com.google.android.material.R.attr.colorSurface)
            addListener(OnTransitionEnd(isFirstViewCreated) {
                setObservers()
            })
        }
    }

    private fun setObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { uiState ->
                    binding.errorLayout.visibility = View.INVISIBLE
                    binding.progressBar.isVisible = uiState.isLoading
                    adapter.list = uiState.list
                    uiState.error?.let { showError(it) }
                }
            }
        }
    }

    private fun showError(text: String?) {
        binding.apply {
            binding.errorLayout.visibility = View.VISIBLE
            binding.error.text = text
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _adapter = null
    }
}