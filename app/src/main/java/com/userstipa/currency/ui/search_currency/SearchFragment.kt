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
import com.userstipa.currency.domain.model.Currency
import com.userstipa.currency.ui.uitls.OnTransitionEnd
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchFragment : Fragment(), SearchAdapterListener {

    @Inject
    lateinit var viewModelFactory: SearchViewModelFactory
    private val viewModel by viewModels<SearchViewModel> { viewModelFactory }
    private val adapter = SearchAdapter(this)
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!


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
        setUi(isFirstViewCreated, view)
    }

    private fun setUi(isFirstViewCreated: Boolean, view: View) {
        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = adapter
        binding.root.transitionName = getString(R.string.transition_home_to_search)
        binding.update.setOnClickListener {
            viewModel.fetchData()
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
                    adapter.list = uiState.list
                    binding.progressBar.isVisible = uiState.isLoading
                    showMessage(uiState.error)
                }
            }
        }
    }

    private fun showMessage(text: String?) {
        binding.messageLayout.isVisible = (text != null)
        binding.message.text = text
    }

    override fun onClickAddCurrency(currency: Currency) {
        viewModel.addCurrency(currency)
    }

    override fun onClickRemoveCurrency(currency: Currency) {
        viewModel.removeCurrency(currency)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}