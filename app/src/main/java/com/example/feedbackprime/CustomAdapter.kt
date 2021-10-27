package com.example.feedbackprime

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val mList: List<ItemsViewModel>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]
// sets the text to the textview from our itemHolder class
        holder.conversation.text = ItemsViewModel.convo
        // sets the text to the textview from our itemHolder class
        holder.speaker.text = ItemsViewModel.speak
        // sets the image to the imageview from our itemHolder class
        holder.feedbackIcon.setImageResource(ItemsViewModel.image)


    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        val conversation: TextView = itemView.findViewById(R.id.conversation)
        val speaker: TextView = itemView.findViewById(R.id.speaker)
        val feedbackIcon: ImageView = itemView.findViewById(R.id.feedbackIcon)
    }
}
