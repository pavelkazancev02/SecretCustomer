package com.example.secretcustomer.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.secretcustomer.R
import com.example.secretcustomer.SecretCustomerApplication
import com.example.secretcustomer.databinding.FragmentRegisterBinding
import com.example.secretcustomer.di.ViewModelFactory
import com.example.secretcustomer.domain.SignUpViewModel
import com.example.secretcustomer.util.NavigationCommand
import javax.inject.Inject

class SignUpFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: SignUpViewModel
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Navigate back when physical 'Back' button pressed
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigateUp()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        (requireActivity().application as SecretCustomerApplication).appComponent.injectSignUpFragment(
            this
        )
        viewModel = ViewModelProvider(this, viewModelFactory).get(SignUpViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.error.observe(viewLifecycleOwner, Observer { errorEvent ->
            errorEvent.getContentIfNotHandled()?.let { resId ->
                Toast.makeText(requireContext(), getString(resId), Toast.LENGTH_LONG).show()
            }
        })

        viewModel.navigationEvent.observe(viewLifecycleOwner, Observer { navEvent ->
            navEvent.getContentIfNotHandled()?.let { navigationCommand ->
                when (navigationCommand) {
                    is NavigationCommand.Back ->
                        findNavController().navigateUp()
                    is NavigationCommand.To ->
                        findNavController().navigate(navigationCommand.directions)
                    is NavigationCommand.ToIntent -> {
                    }
                }
            }
        })

        viewModel.showLoadingBar.observe(viewLifecycleOwner, Observer { showEvent ->
            showEvent.getContentIfNotHandled()?.let { show ->
                if (show) {
                    binding.loadingBar.visibility = View.VISIBLE
                } else {
                    binding.loadingBar.visibility = View.GONE
                }
            }
        })

        viewModel.blockButtons.observe(viewLifecycleOwner, Observer { blockEvent ->
            blockEvent.getContentIfNotHandled()?.let { block ->
                if (block) {
                    binding.registerCreateBtn.isClickable = false
                    binding.registerBackBtn.isClickable = false
                } else {
                    binding.registerCreateBtn.isClickable = true
                    binding.registerBackBtn.isClickable = true
                }
            }
        })
    }
}