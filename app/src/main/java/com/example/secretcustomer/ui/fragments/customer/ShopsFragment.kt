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
import com.example.secretcustomer.adapters.CustomerShopsAdapter
import com.example.secretcustomer.data.Shop
import com.example.secretcustomer.databinding.FragmentShopsBinding
import com.example.secretcustomer.di.ViewModelFactory
import com.example.secretcustomer.domain.customer.ShopsViewModel
import com.example.secretcustomer.util.NavigationCommand
import com.example.secretcustomer.util.OnButtonClickListener
import javax.inject.Inject


class ShopsFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: ShopsViewModel
    private val shopsList: ArrayList<Shop> = ArrayList()
    private lateinit var shopsAdapter: CustomerShopsAdapter
    private lateinit var shopsRecyclerView: RecyclerView
    private lateinit var binding: FragmentShopsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        (requireActivity().application as SecretCustomerApplication).appComponent.injectShopsFragment(
            this
        )

        viewModel = ViewModelProvider(this, viewModelFactory).get(ShopsViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shops, container, false)
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
        val inspectClickListener = object : OnButtonClickListener<Shop> {
            override fun onButtonClicked(item: Shop) {
                viewModel.inspectShop(item)
            }
        }

        val leaveFeedbackClickListener = object : OnButtonClickListener<Shop> {
            override fun onButtonClicked(item: Shop) {
                viewModel.leaveFeedback(item)
            }
        }

        shopsAdapter = CustomerShopsAdapter(
            requireActivity(),
            shopsList,
            inspectClickListener,
            leaveFeedbackClickListener
        )
        shopsRecyclerView = binding.shopsContainer
        shopsRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            itemAnimator = DefaultItemAnimator()
            adapter = shopsAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.shops.observe(viewLifecycleOwner, Observer { shopsEvent ->
            shopsEvent.getContentIfNotHandled()?.let { shops ->
                shopsList.clear()
                shopsList.addAll(shops)
                shopsAdapter.notifyDataSetChanged()
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
