package com.example.secretcustomer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.secretcustomer.R
import com.example.secretcustomer.data.Shop
import com.example.secretcustomer.util.OnButtonClickListener
import com.google.android.material.button.MaterialButton

class CustomerShopsAdapter(
    private val context: Context,
    private val shops: List<Shop>,
    private val inspectClickListener: OnButtonClickListener<Shop>,
    private val leaveFeedbackClickListener: OnButtonClickListener<Shop>
) :
    RecyclerView.Adapter<CustomerShopsAdapter.ShopCardViewHolder>() {

    class ShopCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.findViewById<TextView>(R.id.shops_shop_name1)
        private val address = itemView.findViewById<TextView>(R.id.shops_shop_address1)
        private val inspectBtn = itemView.findViewById<MaterialButton>(R.id.shops_inspect_btn1)
        private val leaveFeedbackBtn =
            itemView.findViewById<MaterialButton>(R.id.shops_leave_feedback_btn1)

        fun bind(
            context: Context,
            shop: Shop,
            inspectClickListener: OnButtonClickListener<Shop>,
            leaveFeedbackClickListener: OnButtonClickListener<Shop>
        ) {
            title.text = shop.name
            address.text = shop.address
            inspectBtn.setOnClickListener {
                inspectClickListener.onButtonClicked(shop)
            }
            leaveFeedbackBtn.setOnClickListener {
                leaveFeedbackClickListener.onButtonClicked(shop)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopCardViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.customer_shop_layout, parent, false)
        return ShopCardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ShopCardViewHolder, position: Int) {
        holder.bind(context, shops[position], inspectClickListener, leaveFeedbackClickListener)
    }

    override fun getItemCount(): Int {
        return shops.size
    }
}