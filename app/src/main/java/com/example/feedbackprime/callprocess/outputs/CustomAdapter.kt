package com.example.feedbackprime.callprocess.outputs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.feedbackprime.R
import com.example.feedbackprime.callprocess.api.models.Messages

class CustomAdapter(private val messages: ArrayList<Messages>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = messages[position]
        val speakerText = itemsViewModel.text
        val fromSpeaker = itemsViewModel.from.name
        val polarityScore = itemsViewModel.sentiment.polarity.score.toDouble()

        holder.conversation.text = speakerText
        if (fromSpeaker.isNullOrEmpty()) {
            if (position % 2 == 0)
                holder.speaker.text = "Speaker 1"
            else
                holder.speaker.text = "Speaker 2"
        } else
            holder.speaker.text = fromSpeaker

        if (polarityScore > -0.3 && polarityScore <= -1.0)
            holder.feedbackIcon.setImageResource(R.drawable.sad)
        else if (polarityScore >= -0.3 && polarityScore <= 0.3)
            holder.feedbackIcon.setImageResource(R.drawable.neutral)
        else if (polarityScore > 0.3 && polarityScore <= 1.0)
            holder.feedbackIcon.setImageResource(R.drawable.happy)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val conversation: TextView = itemView.findViewById(R.id.conversation)
        val speaker: TextView = itemView.findViewById(R.id.speakerName)
        val feedbackIcon: ImageView = itemView.findViewById(R.id.feedbackIcon)
    }
}
