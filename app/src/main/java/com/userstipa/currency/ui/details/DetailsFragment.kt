package com.userstipa.currency.ui.details

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import com.userstipa.currency.App
import com.userstipa.currency.R
import com.userstipa.currency.databinding.FragmentDetailsBinding
import com.userstipa.currency.domain.model.HistoryRange
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: DetailsViewModelFactory
    private val viewModel by viewModels<DetailsViewModel> { viewModelFactory }
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: DetailsFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            scrimColor = Color.TRANSPARENT
            duration = 500
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        binding.name.text = args.currencyName
        binding.cardView.transitionName =
            getString(R.string.shared_element_home_to_details, args.currencyId)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        setUi()
        viewModel.fetchData(args.currencyId, getHistoryRange(binding.toggleButton.checkedButtonId))
    }

    private fun setObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { uiState ->
                    uiState.currency?.let { currency -> binding.lineGraph.currency = currency }
                    uiState.error?.let { showMessage(it) }
                }
            }
        }
    }

    private fun setUi() {
        binding.toggleButton.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                val historyRange = getHistoryRange(checkedId)
                viewModel.fetchData(args.currencyId, historyRange)
            }
        }
    }

    private fun getHistoryRange(checkedId: Int): HistoryRange {
        return when (checkedId) {
            R.id.hour -> HistoryRange.LAST_HOUR
            R.id.day -> HistoryRange.LAST_DAY
            R.id.week -> HistoryRange.LAST_WEEK
            R.id.month -> HistoryRange.LAST_MONTH
            R.id.year -> HistoryRange.LAST_YEAR
            else -> HistoryRange.LAST_DAY
        }
    }

    private fun showMessage(message: String) {
        binding.name.text = message
    }


}