package com.example.secretcustomer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.example.secretcustomer.R
import com.example.secretcustomer.data.Shop
import com.example.secretcustomer.data.ShopWithAvailability
import com.example.secretcustomer.util.OnButtonClickListener
import com.google.android.material.button.MaterialButton


class CustomerShopsAdapter(
    private val context: Context,
    private val shops: List<ShopWithAvailability>,
    private val inspectClickListener: OnButtonClickListener<Shop>,
    private val leaveFeedbackClickListener: OnButtonClickListener<Shop>
) :
    RecyclerView.Adapter<CustomerShopsAdapter.ShopCardViewHolder>() {

    inner class ShopCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.findViewById<TextView>(R.id.shops_shop_name1)
        private val address = itemView.findViewById<TextView>(R.id.shops_shop_address1)
        private val inspectBtn = itemView.findViewById<MaterialButton>(R.id.shops_inspect_btn1)
        private val leaveFeedbackBtn =
            itemView.findViewById<MaterialButton>(R.id.shops_leave_feedback_btn1)
        private val buttonsContainer =
            itemView.findViewById<ConstraintLayout>(R.id.buttons_container)

        fun bind(
            context: Context,
            shopObj: ShopWithAvailability,
            inspectClickListener: OnButtonClickListener<Shop>,
            leaveFeedbackClickListener: OnButtonClickListener<Shop>
        ) {
            title.text = shopObj.shop.name
            address.text = shopObj.shop.address
            if (shopObj.isSessionAvailable) {
                inspectBtn.setOnClickListener {
                    inspectClickListener.onButtonClicked(shopObj.shop)
                }
            } else {
                val constraintSet = ConstraintSet()
                constraintSet.clone(buttonsContainer)
                constraintSet.apply {
                    setVisibility(inspectBtn.id, View.GONE)
                    clear(leaveFeedbackBtn.id, ConstraintSet.START)
                    connect(
                        leaveFeedbackBtn.id,
                        ConstraintSet.START,
                        buttonsContainer.id,
                        ConstraintSet.START,
                        0
                    )
                }
                constraintSet.applyTo(buttonsContainer)
            }
            leaveFeedbackBtn.setOnClickListener {
                leaveFeedbackClickListener.onButtonClicked(shopObj.shop)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopCardViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.customer_shop_card_layout, parent, false)
        return ShopCardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ShopCardViewHolder, position: Int) {
        holder.bind(context, shops[position], inspectClickListener, leaveFeedbackClickListener)
    }

    override fun getItemCount(): Int {
        return shops.size
    }
}