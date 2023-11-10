package com.userstipa.currency.ui.details

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
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import com.userstipa.currency.App
import com.userstipa.currency.R
import com.userstipa.currency.databinding.FragmentDetailsBinding
import com.userstipa.currency.domain.model.CurrencyPriceDetails
import com.userstipa.currency.domain.model.HistoryRange
import com.userstipa.currency.ui.uitls.OnTransitionEnd
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
    private val currencyId by lazy { args.currencyId }
    private val currencyName by lazy { args.currencyName }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val isFirstViewCreated = (savedInstanceState == null)
        setUi(isFirstViewCreated)
        viewModel.fetchData(currencyId, getHistoryRange(binding.toggleButton.checkedButtonId))
    }

    private fun setUi(isFirstViewCreated: Boolean) {
        binding.apply {
            name.text = currencyName
            cardView.transitionName = getString(R.string.transition_home_to_details, currencyId)
            update.setOnClickListener { viewModel.fetchData(currencyId) }
            toggleButton.addOnButtonCheckedListener { group, checkedId, isChecked ->
                if (isChecked) {
                    viewModel.fetchData(currencyId, getHistoryRange(checkedId))
                }
            }
        }
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            scrimColor = Color.TRANSPARENT
            duration = resources.getInteger(R.integer.duration_transitions_animation).toLong()
            addListener(OnTransitionEnd(isFirstViewCreated) {
                setObservers()
            })
        }
    }

    private fun setObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { uiState ->
                    binding.progressBar.isVisible = uiState.isLoading
                    uiState.currency?.let { setCurrency(it) }
                    uiState.error?.let { showError(it) }
                }
            }
        }
    }

    private fun setCurrency(currency: CurrencyPriceDetails) {
        binding.apply {
            detailsLayout.visibility = View.VISIBLE
            errorLayout.visibility = View.INVISIBLE
            lineGraph.currency = currency
            maxPrice.text = currency.maxPriceUsdFormatted
            minPrice.text = currency.minPriceUsdFormatted
            marketCapUsd.text = currency.marketCapUsd
            supply.text = currency.supply
            maxSupply.text = currency.maxSupply
            vwap24hr.text = currency.vwap24Hr
            explorer.text = currency.explorer
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

    private fun showError(error: String?) {
        binding.apply {
            detailsLayout.visibility = View.INVISIBLE
            errorLayout.visibility = View.VISIBLE
            binding.error.text = error
        }
    }


}