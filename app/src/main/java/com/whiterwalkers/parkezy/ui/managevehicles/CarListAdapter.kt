package com.whiterwalkers.parkezy.ui.managevehicles

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.whiterwalkers.parkezy.R
import com.whiterwalkers.parkezy.model.pojos.Car


class CarListAdapter(
    private val context: Context,
    private val dataSet: List<Car>,
    private val callback: CarSelectionCallback
) :
    RecyclerView.Adapter<CarListAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(context: Context, view: View, callback: CarSelectionCallback) :
        RecyclerView.ViewHolder(view) {
        var ivCarImage: ImageView = itemView.findViewById(R.id.ivCarImage)
        var tvCarName: TextView = itemView.findViewById(R.id.tvCarName)
        var tvCarDetails: TextView = itemView.findViewById(R.id.tvCarDetails)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_car_list, viewGroup, false)

        return ViewHolder(context, view, callback)
    }

    var rowIndex = -1

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvCarName.text = dataSet[position].nickName
        viewHolder.tvCarDetails.text =
            dataSet[position].make + " (" + dataSet[position].model + ") "
//        if(dataSet[position].isPrimary)
//            rowIndex = position
        viewHolder.itemView.setOnClickListener {
            rowIndex = position
            callback.onSelectedVehicle(dataSet[position])
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