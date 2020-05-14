package com.example.secretcustomer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.secretcustomer.R
import com.example.secretcustomer.data.Feedback

class CustomerFeedbackAdapter(
    private val context: Context,
    private val feedback: List<Feedback>
) :
    RecyclerView.Adapter<CustomerFeedbackAdapter.FeedbackCardViewHolder>() {

    class FeedbackCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.findViewById<TextView>(R.id.my_feedback_shop_name1)
        private val address = itemView.findViewById<TextView>(R.id.my_feedback_shop_address1)
        private val feedbackText = itemView.findViewById<TextView>(R.id.my_feedback_shop_fb1)
        private val moodImg = itemView.findViewById<ImageView>(R.id.my_feedback_shop_mood1)

        fun bind(
            context: Context,
            feedback: Feedback
        ) {
            title.text = feedback.shopName
            address.text = feedback.pros
            feedbackText.text = feedback.additionalInfo
            when (feedback.rating) {
                1 -> moodImg.setImageResource(R.drawable.mood1_75dp)
                2 -> moodImg.setImageResource(R.drawable.mood2_75dp)
                3 -> moodImg.setImageResource(R.drawable.mood3_75dp)
                4 -> moodImg.setImageResource(R.drawable.mood4_75dp)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedbackCardViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.customer_feedback_card_layout, parent, false)
        return FeedbackCardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FeedbackCardViewHolder, position: Int) {
        holder.bind(context, feedback[position])
    }

    override fun getItemCount(): Int {
        return feedback.size
    }
}