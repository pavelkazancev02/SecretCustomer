package com.example.secretcustomer.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.secretcustomer.R
import com.example.secretcustomer.SecretCustomerApplication
import com.example.secretcustomer.databinding.FragmentLoginBinding
import com.example.secretcustomer.di.ViewModelFactory
import com.example.secretcustomer.domain.LoginViewModel
import com.example.secretcustomer.util.NavigationCommand
import javax.inject.Inject

class LoginFragment: Fragment() {

    // Инжектим нашу кастомную фабрику из модуля di , так как она сама будет определять,
    // создать новую вью модель или использовать существующую
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    // DataBinding из layout
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        // Обязательно инжектим наш в фрагмент в аппликейшион, иначе фабрика не заинжектиться и все упадет
        (requireActivity().application as SecretCustomerApplication).appComponent.injectLoginFragment(this)


        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {

        // Набюдаем наши LiveData
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
                    is NavigationCommand.ToIntent ->
                        startActivity(navigationCommand.intent)
                    is NavigationCommand.ToIntentWithFinish -> {
                        startActivity(navigationCommand.intent)
                        activity?.finish()
                    }
                    is NavigationCommand.Finish ->
                        activity?.finish()
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
    }
}