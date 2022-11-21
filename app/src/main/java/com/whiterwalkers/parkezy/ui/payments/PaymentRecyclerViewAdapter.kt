package com.whiterwalkers.parkezy.ui.payments

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.whiterwalkers.parkezy.R
import com.whiterwalkers.parkezy.model.pojos.Payment


class PaymentRecyclerViewAdapter(
    private val context: Context,
    private val dataSet: List<Payment>,
    private val callback: PaymentSelectionCallback
) :
    RecyclerView.Adapter<PaymentRecyclerViewAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(context: Context, view: View, callback: PaymentSelectionCallback) :
        RecyclerView.ViewHolder(view) {
        var textPaymentName: TextView = itemView.findViewById(R.id.text_payment_name)
        var textPaymentWalletBalance: TextView = itemView.findViewById(R.id.text_wallet_balance)
        var imagePaymentIcon: ImageView = itemView.findViewById(R.id.image_icon)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.payment_item, viewGroup, false)

        return ViewHolder(context, view, callback)
    }

    var rowIndex = -1

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val payment = dataSet[position]
        viewHolder.textPaymentName.text = payment.paymentName
        payment.balance?.let {
            viewHolder.textPaymentWalletBalance.text = "Wallet Balance: Rs $it"
        }
        viewHolder.imagePaymentIcon.setImageResource(payment.icon)
//        if (dataSet[position].isPrimary)
//            rowIndex = position
        viewHolder.itemView.setOnClickListener {
            rowIndex = position
            callback.onSelectedPaymentOption(payment)
            dataSet[position].isPrimary = true
            notifyDataSetChanged()
        }
        if (rowIndex == position)
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#22a4dfcc"))
        else
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#ffffff"))
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}