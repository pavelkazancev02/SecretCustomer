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
import com.example.secretcustomer.databinding.FragmentProfileBinding
import com.example.secretcustomer.di.ViewModelFactory
import com.example.secretcustomer.domain.customer.ProfileViewModel
import com.example.secretcustomer.util.NavigationCommand
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        (requireActivity().application as SecretCustomerApplication).appComponent.injectProfileFragment(
            this
        )

        viewModel = ViewModelProvider(this, viewModelFactory).get(ProfileViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
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
