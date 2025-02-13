package com.avito.tracks

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avito.domain.model.TrackInfo
import com.avito.tracks.databinding.ItemTrackBinding
import com.bumptech.glide.Glide

class TracksAdapter : RecyclerView.Adapter<TracksAdapter.TrackInfoViewHolder>() {

    //TODO добавить обработку клика
    private val trackList = mutableListOf<TrackInfo>()

    class TrackInfoViewHolder(private val binding: ItemTrackBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(trackInfo: TrackInfo){

                try {
                    Log.d("TracksAdapter", "trackInfo.picture = ${trackInfo.artist.picture}")
//                    binding.ivTrackImage.load(trackInfo.artist.picture ){
//                        placeholder(R.drawable.ic_launcher_foreground)
//                        //TODO заменить картинку
//                        error(R.drawable.baseline_cloud_off_24)
//                    }
                    Glide.with(itemView.context)
                        .load(trackInfo.artist.picture)
                        .error(R.drawable.baseline_cloud_off_24) // Проверь, что такой ресурс существует
                        .into(binding.ivTrackImage)

                }
                catch (e:Exception){
                    Log.e("TracksAdapter", "Error loading image: ${e.message}", e)
                }

                binding.tvTrackTitle.text = trackInfo.title
                binding.tvAlbumTitle.text = trackInfo.album.title
                binding.tvArtistName.text = trackInfo.artist.name

            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackInfoViewHolder {
        val binding = ItemTrackBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  TrackInfoViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return trackList.size
    }

    override fun onBindViewHolder(holder: TrackInfoViewHolder, position: Int) {
        val track = trackList[position]
        holder.bind(track)
    }

    fun submitList(newTracks: List<TrackInfo>){
        trackList.clear()
        trackList.addAll(newTracks)
        notifyDataSetChanged()
        //TODO заменить на diffutils
    }
}