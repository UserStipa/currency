package com.userstipa.currency.ui.home

import android.content.Context
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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.userstipa.currency.App
import com.userstipa.currency.R
import com.userstipa.currency.databinding.FragmentHomeBinding
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
    }

    private fun setAdapter() {
        _adapter = HomeAdapter(requireContext())
        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = adapter
    }

    private fun setUi() {
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
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
        _binding = null
    }
}