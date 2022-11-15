package com.whiterwalkers.parkezy.ui.parkinglots.manageparking

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.whiterwalkers.parkezy.R
import com.whiterwalkers.parkezy.model.pojos.ParkingSpot


class ParkingListRecyclerViewAdapter(
    private val context: Context,
    private val dataSet: List<ParkingSpot>,
    private val callback: ParkingSlotCallback
) :
    RecyclerView.Adapter<ParkingListRecyclerViewAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(context: Context, view: View, callback: ParkingSlotCallback) :
        RecyclerView.ViewHolder(view) {
        var imgParking: ImageView
        var imgOverflow: ImageView
        var imgContainer: ImageView
        var title: TextView
        var address: TextView
        var info: TextView
        var rate: TextView
        var ratingBar: RatingBar
        var parkingSwitch: SwitchCompat

        init {
            // Define click listener for the ViewHolder's View.
            imgParking = itemView.findViewById(R.id.item_parking_image)
            imgContainer = itemView.findViewById(R.id.container)
            title = itemView.findViewById(R.id.item_parking_title)
            address = itemView.findViewById(R.id.item_parking_address)
            info = itemView.findViewById(R.id.item_parking_info)
            rate = itemView.findViewById(R.id.item_parking_score)
            ratingBar = itemView.findViewById(R.id.item_parking_ratingbar)
            imgOverflow = itemView.findViewById(R.id.item_parking_more)
            parkingSwitch = itemView.findViewById(R.id.item_parking_switch)

            itemView.setOnClickListener {
                // On Item click, details to be shown
            }
            imgOverflow.setOnClickListener {
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
            }
            parkingSwitch.setOnCheckedChangeListener { view, isChecked ->
                callback.toggleAvailability(adapterPosition, isChecked)
            }

        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.parking_spot_item, viewGroup, false)

        return ViewHolder(context, view, callback)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // bind data here

        // we apply animation to views here
        // first lets create an animation for user photo
        viewHolder.imgParking.animation = AnimationUtils.loadAnimation(
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
        val parkingSpot = dataSet[position]
        viewHolder.title.text = parkingSpot.parkingName
        viewHolder.address.text = parkingSpot.address
        viewHolder.info.text = parkingSpot.info
        viewHolder.ratingBar.rating = parkingSpot.ratings
        viewHolder.rate.text = "(${parkingSpot.ratings}/5)"
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}