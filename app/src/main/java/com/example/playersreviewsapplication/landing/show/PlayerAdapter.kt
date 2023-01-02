package com.example.playersreviewsapplication.landing.show

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.playersreviewsapplication.R
import com.example.playersreviewsapplication.data.response.Player

class PlayerAdapter(
    private val players: List<Player>,
    private val onPlayerClickListener: OnPlayerClickListener
) :
    RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.player_recycler_item, parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.playerName.text = players[position].name
        holder.playerBrief.text = players[position].brief
        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transforms(FitCenter(), RoundedCorners(16))
        Glide.with(holder.itemView.context)
            .load(players[position].image)
            .apply(requestOptions)
            .skipMemoryCache(false)//for caching the image url in case phone is offline
            .into(holder.playerImage)
        holder.playerItem.setOnClickListener {
            onPlayerClickListener.onPlayerPressed(players[position])
        }
    }

    override fun getItemCount(): Int {
        return players.size
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val playerName: TextView = itemView.findViewById(R.id.player_name)
        val playerBrief: TextView = itemView.findViewById(R.id.player_brief)
        val playerImage: ImageView = itemView.findViewById(R.id.player_image)
        val playerItem: ConstraintLayout = itemView.findViewById(R.id.player_item)
    }
}