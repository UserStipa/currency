package com.userstipa.currency.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialElevationScale
import com.userstipa.currency.App
import com.userstipa.currency.R
import com.userstipa.currency.databinding.FragmentHomeBinding
import com.userstipa.currency.domain.model.CurrencyPrice
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: HomeViewModelFactory
    private val viewModel by viewModels<HomeViewModel> { viewModelFactory }
    private var _adapter: HomeAdapter? = null
    private val adapter get() = _adapter!!
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialElevationScale(false).apply {
            duration = resources.getInteger(R.integer.duration_transitions_animation).toLong()
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.duration_transitions_animation).toLong()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setUi()
        setObservers()

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
    }

    private fun setAdapter() {
        val onClickCurrency = { currency: CurrencyPrice, view: View ->
            val sharedElement = view to getString(R.string.shared_element_home_to_details, currency.id)
            val extras = FragmentNavigatorExtras(sharedElement)
            val direction = HomeFragmentDirections.actionHomeToDetails(currency.id, currency.name)
            findNavController().navigate(direction, extras)
        }
        _adapter = HomeAdapter(onClickCurrency)
        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = adapter
    }

    private fun setUi() {
        binding.btnAdd.transitionName = getString(R.string.shared_element_home_to_search)
        binding.btnAdd.setOnClickListener { view ->
            val sharedElement = view to getString(R.string.shared_element_home_to_search)
            val extras = FragmentNavigatorExtras(sharedElement)
            val direction = HomeFragmentDirections.actionHomeToSearch()
            findNavController().navigate(direction, extras)
        }
        binding.update.setOnClickListener {
            viewModel.subscribeData()
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

    override fun onStart() {
        super.onStart()
        viewModel.subscribeData()
    }

    override fun onStop() {
        super.onStop()
        viewModel.unsubscribeData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _adapter = null
        _binding = null
    }
}