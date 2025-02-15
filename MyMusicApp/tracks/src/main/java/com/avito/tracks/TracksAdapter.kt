package com.avito.tracks

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avito.domain.model.TrackInfo
import com.avito.tracks.databinding.ItemTrackBinding
import com.bumptech.glide.Glide


class TracksAdapter(
    private val isDownloadScreenForIcon: Boolean,
    private val actionWithTrack: (trackInfo: TrackInfo) -> Unit,
) : RecyclerView.Adapter<TracksAdapter.TrackInfoViewHolder>() {

    //TODO добавить обработку клика
    private val trackList = mutableListOf<TrackInfo>()

    class TrackInfoViewHolder(
        private val binding: ItemTrackBinding,
        private val isDownloadScreenForIcon: Boolean,
        private val actionWithTrack: (trackInfo: TrackInfo) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(trackInfo: TrackInfo) {

            try {
                Glide.with(itemView.context)
                    .load(trackInfo.cover)
                    .error(R.drawable.baseline_cloud_off_24)
                    .into(binding.ivTrackImage)

            } catch (e: Exception) {
                Log.e("TracksAdapter", "Error loading image: ${e.message}", e)
            }

            binding.tvTrackTitle.text = trackInfo.title
            binding.tvAlbumTitle.text = trackInfo.album
            binding.tvArtistName.text = trackInfo.artist

            val buttonIconRes = if(isDownloadScreenForIcon) R.drawable.ic_delete
            else R.drawable.ic_download

            binding.btnActionWithTrack.setImageResource(buttonIconRes)


            binding.btnActionWithTrack.setOnClickListener {
                actionWithTrack(trackInfo)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackInfoViewHolder {
        val binding = ItemTrackBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrackInfoViewHolder(binding,isDownloadScreenForIcon, actionWithTrack)
    }

    override fun getItemCount(): Int {
        return trackList.size
    }

    override fun onBindViewHolder(holder: TrackInfoViewHolder, position: Int) {
        val track = trackList[position]
        holder.bind(track)
    }

    fun submitList(newTracks: List<TrackInfo>) {
        trackList.clear()
        trackList.addAll(newTracks)
        notifyDataSetChanged()
        //TODO заменить на diffutils
    }
}