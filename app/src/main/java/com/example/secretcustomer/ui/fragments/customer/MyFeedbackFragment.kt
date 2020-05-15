package com.example.secretcustomer.ui.fragments.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.secretcustomer.R
import com.example.secretcustomer.SecretCustomerApplication
import com.example.secretcustomer.adapters.CustomerFeedbackAdapter
import com.example.secretcustomer.data.Feedback
import com.example.secretcustomer.databinding.FragmentMyFeedbackBinding
import com.example.secretcustomer.di.ViewModelFactory
import com.example.secretcustomer.domain.customer.FeedbackViewModel
import com.example.secretcustomer.util.NavigationCommand
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class MyFeedbackFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: FeedbackViewModel
    private val feedbackList: ArrayList<Feedback> = ArrayList()
    private lateinit var feedbackAdapter: CustomerFeedbackAdapter
    private lateinit var feedbackRecyclerView: RecyclerView
    private lateinit var binding: FragmentMyFeedbackBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        (requireActivity().application as SecretCustomerApplication).appComponent.injectCustomerFeedbackFragment(
            this
        )

        viewModel = ViewModelProvider(this, viewModelFactory).get(FeedbackViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_feedback, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        prepareAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun prepareAdapter() {
        feedbackAdapter = CustomerFeedbackAdapter(requireContext(), feedbackList)
        feedbackRecyclerView = binding.feedbackContainer
        feedbackRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            itemAnimator = DefaultItemAnimator()
            adapter = feedbackAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.feedback.observe(viewLifecycleOwner, Observer { feedback ->
                feedbackList.clear()
                feedbackList.addAll(feedback)
                feedbackAdapter.notifyDataSetChanged()
        })

        viewModel.navigationEvent.observe(viewLifecycleOwner, Observer { navEvent ->
            navEvent.getContentIfNotHandled()?.let { navigationCommand ->
                when (navigationCommand) {
                    is NavigationCommand.Back ->
                        findNavController().navigateUp()
                    is NavigationCommand.To ->
                        findNavController().navigate(navigationCommand.directions)
                    is NavigationCommand.ToIntent ->
                        startActivity(navigationCommand.intent)
                    else -> {
                    }
                }
            }
        })

        viewModel.showLoadingBar.observe(viewLifecycleOwner, Observer { showEvent ->
            showEvent.getContentIfNotHandled()?.let { show ->
                if (show) {
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
    }

}
