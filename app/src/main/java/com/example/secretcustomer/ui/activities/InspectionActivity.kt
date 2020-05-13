package com.example.secretcustomer.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.secretcustomer.R
import com.example.secretcustomer.SecretCustomerApplication
import com.example.secretcustomer.di.ViewModelFactory
import com.example.secretcustomer.domain.customer.InspectionViewModel
import javax.inject.Inject

class InspectionActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: InspectionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inspection)

        (application as SecretCustomerApplication).appComponent.injectShopInspectionActivity(
            this
        )

        viewModel = ViewModelProvider(this, viewModelFactory).get(InspectionViewModel::class.java)
    }
}
