package com.schools.nycschools.ui.schoolDetails

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.schools.nycschools.R
import com.schools.nycschools.databinding.FragmentSchoolDetailBinding
import com.schools.nycschools.domain.model.NYCSchoolSATScoreResponseItem
import com.schools.nycschools.domain.model.Output
import com.schools.nycschools.ui.base.BaseFragment
import com.schools.nycschools.utils.callClick
import com.schools.nycschools.utils.directionClick
import com.schools.nycschools.utils.emailClick
import com.schools.nycschools.utils.faxClick
import com.schools.nycschools.utils.linkClick
import com.schools.nycschools.utils.setOnSafeClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * School detail fragment
 *
 * @constructor Create empty School detail fragment
 */
@AndroidEntryPoint
class SchoolDetailFragment : BaseFragment<FragmentSchoolDetailBinding>(
    FragmentSchoolDetailBinding::inflate
) {
    private val viewModel: SchoolDetailViewModel by viewModels()
    private val args: SchoolDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        fetchSchoolSAT()
    }

    private fun fetchSchoolSAT() {
        args.nycSchool.dbn?.let { viewModel.fetchSchoolDetail(it) }
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.run {
                    launch {
                        schoolsState.collect {
                            when (it.status) {
                                Output.Status.LOADING -> {
                                    setViewVisibility(Output.Status.LOADING)
                                }

                                Output.Status.SUCCESS -> {
                                    it.data?.let { data ->
                                        if (data.isNotEmpty()) {
                                            updateUi(data.first())
                                        }
                                    }
                                    updateStaticUi()
                                    setViewVisibility(Output.Status.SUCCESS)
                                }

                                Output.Status.ERROR -> {
                                    setViewVisibility(Output.Status.ERROR, it.error?.statusMessage)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun updateStaticUi() {
        binding.run {
            textTitle.text = args.nycSchool.school_name
            textSubtitle.text = args.nycSchool.dbn
            args.nycSchool.school_email?.let { email ->
                incContact.ibEmail.visibility = View.VISIBLE
                incContact.ibEmail.setOnSafeClickListener {
                    requireActivity().emailClick(email)
                }
            } ?: run {
                incContact.ibEmail.visibility = View.GONE
            }
            args.nycSchool.phone_number?.let { number ->
                incContact.ibCall.visibility = View.VISIBLE
                incContact.ibCall.setOnSafeClickListener {
                    requireActivity().callClick(number)
                }
            } ?: run {
                incContact.ibCall.visibility = View.GONE
            }

            args.nycSchool.fax_number?.let { number ->
                incContact.ibFax.visibility = View.VISIBLE
                incContact.ibFax.setOnSafeClickListener {
                    requireActivity().faxClick(number)
                }
            } ?: run {
                incContact.ibFax.visibility = View.GONE
            }

            args.nycSchool.latitude?.let { lat ->
                args.nycSchool.longitude?.let { long ->
                    incContact.ibDirection.visibility = View.VISIBLE
                    incContact.ibDirection.setOnSafeClickListener {
                        requireActivity().directionClick(lat, long)
                    }
                } ?: run {
                    incContact.ibDirection.visibility = View.GONE
                }
            } ?: run {
                incContact.ibDirection.visibility = View.GONE
            }

            args.nycSchool.website?.let { website ->
                incContact.ibLink.visibility = View.VISIBLE
                incContact.ibLink.setOnSafeClickListener {
                    requireActivity().linkClick(website)
                }
            } ?: run {
                incContact.ibLink.visibility = View.GONE
            }

            args.nycSchool.subway?.let { subway ->
                incDirection.ivSubwayTransport.visibility = View.VISIBLE
                incDirection.tvSubwayTransport.visibility = View.VISIBLE
                incDirection.tvSubwayTransport.text = subway
            } ?: run {
                incDirection.ivSubwayTransport.visibility = View.GONE
                incDirection.tvSubwayTransport.visibility = View.GONE
            }

            args.nycSchool.bus?.let { bus ->
                incDirection.ivBusTransport.visibility = View.VISIBLE
                incDirection.tvBusTransport.visibility = View.VISIBLE
                incDirection.tvBusTransport.text = bus
            } ?: run {
                incDirection.ivBusTransport.visibility = View.GONE
                incDirection.tvBusTransport.visibility = View.GONE
            }
        }
    }

    private fun updateUi(data: NYCSchoolSATScoreResponseItem) {
        binding.run {
            data.run {
                textDescription.text = args.nycSchool.overview_paragraph
                btnNumberOfPeople.text = getString(R.string.test_taker, num_of_sat_test_takers)
                btnMathScore.text = getString(R.string.math, sat_math_avg_score)
                btnReadingScore.text = getString(R.string.reading, sat_critical_reading_avg_score)
                btnWritingScore.text = getString(R.string.writing, sat_writing_avg_score)
            }
        }
    }

    private fun setViewVisibility(status: Output.Status, error: String? = null) {
        binding.run {
            progressDialog.isVisible = status == Output.Status.LOADING
            layoutView.isVisible = status == Output.Status.SUCCESS
            errorLayout.root.isVisible = status == Output.Status.ERROR
            error?.let {
                errorLayout.labelErrorDescription.text = it
            }
        }
    }
}
