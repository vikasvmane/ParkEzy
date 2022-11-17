package com.whiterwalkers.parkezy.ui.managevehicles

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.whiterwalkers.parkezy.R
import com.whiterwalkers.parkezy.model.pojos.Car
import com.whiterwalkers.parkezy.ui.parkinglots.manageparking.ParkingSlotCallback


class CarListAdapter(
    private val context: Context,
    private val dataSet: List<Car>,
    private val callback: ParkingSlotCallback
) :
    RecyclerView.Adapter<CarListAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(context: Context, view: View, callback: ParkingSlotCallback) :
        RecyclerView.ViewHolder(view) {
        var ivCarImage: ImageView = itemView.findViewById(R.id.ivCarImage)
        var tvCarName: TextView = itemView.findViewById(R.id.tvCarName)
        var tvCarDetails: TextView = itemView.findViewById(R.id.tvCarDetails)

        init {
            // Define click listener for the ViewHolder's View.


            ivCarImage.setOnClickListener {
                // On Item click, details to be shown
            }
            /*    imgOverflow.setOnClickListener {
                    //creating a popup menu
                    val popup = PopupMenu(context, it)
                    //inflating menu from xml resource
                    popup.inflate(R.menu.parking_spot_options)
                    //adding click listener
                    popup.setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.action_rate -> {
                                callback.onParkingRateClick(
                                    adapterPosition,
                                )
                                true
                            }
                            R.id.action_schedule -> {
                                callback.onParkingScheduleClick(
                                    adapterPosition,
                                )
                                true
                            }
                            R.id.action_delete -> {
                                callback.onParkingDeleteClick(
                                    adapterPosition,
                                )
                                true
                            }
                            else -> false
                        }
                    }
                    //displaying the popup
                    popup.show()
                }*/

        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_car_list, viewGroup, false)

        return ViewHolder(context, view, callback)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // bind data here

        // we apply animation to views here
        // first lets create an animation for user photo
        viewHolder.ivCarImage.animation = AnimationUtils.loadAnimation(
            context,
            R.anim.fade_transition_animation
        );

        // lets create the animation for the whole card
        // first lets create a reference to it
        // you ca use the previous same animation like the following

        // but i want to use a different one so lets create it ..
        viewHolder.itemView.animation = AnimationUtils.loadAnimation(
            context,
            R.anim.fade_scale_animation
        );
        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        viewHolder.tvCarName.text = dataSet[position].nickName
        viewHolder.tvCarDetails.text =
            dataSet[position].make + " (" + dataSet[position].model + ") "

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}