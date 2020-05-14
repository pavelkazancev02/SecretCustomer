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
import com.example.secretcustomer.R
import com.example.secretcustomer.SecretCustomerApplication
import com.example.secretcustomer.databinding.FragmentShopsFinishBinding
import com.example.secretcustomer.di.ViewModelFactory
import com.example.secretcustomer.domain.customer.InspectionViewModel
import com.example.secretcustomer.util.NavigationCommand
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class ShopInspectionFinishFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: InspectionViewModel
    private lateinit var binding: FragmentShopsFinishBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        (requireActivity().application as SecretCustomerApplication).appComponent.injectInspectionFinishFragment(
            this
        )

        viewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        ).get(InspectionViewModel::class.java)

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_shops_finish, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }


    private fun observeViewModel() {
        viewModel.navigationEvent.observe(viewLifecycleOwner, Observer { navEvent ->
            navEvent.getContentIfNotHandled()?.let { navigationCommand ->
                when (navigationCommand) {
                    is NavigationCommand.Back ->
                        findNavController().navigateUp()
                    is NavigationCommand.To ->
                        findNavController().navigate(navigationCommand.directions)
                    is NavigationCommand.ToIntent ->
                        startActivity(navigationCommand.intent)
                    is NavigationCommand.Finish ->
                        activity?.finish()
                    else -> {
                    }
                }
            }
        })
    }

}
