package com.whiterwalkers.parkezy.ui.history

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.whiterwalkers.parkezy.R
import com.whiterwalkers.parkezy.model.pojos.Transaction


class HistoryRecyclerViewAdapter(
    private val context: Context,
    private val dataSet: List<Transaction>
) :
    RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(context: Context, view: View) :
        RecyclerView.ViewHolder(view) {
        var textParkingName: TextView = itemView.findViewById(R.id.text_parking_name)
        var textParkingAddress: TextView = itemView.findViewById(R.id.text_parking_address)
        var textVehicleDetails: TextView = itemView.findViewById(R.id.text_car_details)
        var textPaymentMode: TextView = itemView.findViewById(R.id.text_payment_details)
        var textTotalCharges: TextView = itemView.findViewById(R.id.text_total_charges)
        var textRating: RatingBar = itemView.findViewById(R.id.ratingBar)

        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.history_item, viewGroup, false)

        return ViewHolder(context, view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.animation = AnimationUtils.loadAnimation(
            context,
            R.anim.fade_scale_animation
        )
        val transaction = dataSet[position]
        viewHolder.textParkingName.text = transaction.parkingName
        viewHolder.textParkingAddress.text = transaction.parkingAddress
        viewHolder.textVehicleDetails.text = transaction.vehicleDetails
        viewHolder.textPaymentMode.text = transaction.paymentMode
        viewHolder.textTotalCharges.text = transaction.charges
        viewHolder.textRating.rating = transaction.rating
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}