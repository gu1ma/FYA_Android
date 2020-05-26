package io.klineapps.fya.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.klineapps.fya.App
import io.klineapps.fya.R
import io.klineapps.fya.model.Sport

class SportsAdapter (private val sports:Array<Sport>):RecyclerView.Adapter<SportsAdapter.SportsViewHolder>(){
    override fun getItemCount(): Int {
         //To change body of created functions use File | Settings | File Templates.
        return sports.size
    }

    override fun onBindViewHolder(holder: SportsViewHolder, position: Int) {
        val sport = sports[position]
        holder.bind(sport)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SportsViewHolder(inflater,parent)
    }


    class SportsViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_sports, parent, false)){

        private var sportImage:ImageView? = null
        private var sportTitle:TextView? = null
        private var sportAddress:TextView? = null
        private var sportPrice:TextView? = null
        init {
            sportImage = itemView.findViewById(R.id.sport_image)
            sportTitle = itemView.findViewById(R.id.sport_title)
            sportAddress = itemView.findViewById(R.id.sport_address)
            sportPrice = itemView.findViewById(R.id.sport_price)
        }

        fun bind(sport: Sport){

            sportTitle?.text = sport.dsSport
            sportAddress?.text = sport.address
            sportPrice?.text = sport.price
            Glide.with(App.instance?.applicationContext!!).load(sport.image).centerCrop().into(sportImage!!)
        }
    }
}