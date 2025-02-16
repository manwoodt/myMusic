package com.avito.presentation.UI

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.avito.presentation.R
import com.avito.presentation.databinding.FragmentPlaybackBinding
import com.avito.presentation.viewmodels.PlaybackViewModel
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaybackFragment : Fragment() {

    private var _binding: FragmentPlaybackBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PlaybackViewModel by viewModel()
    private val args: PlaybackFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPlaybackBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val trackId = args.trackId
        Log.d("PlaybackFragment", "Загрузка трека началась")
        viewModel.loadTrackbyId(trackId)
        Log.d("PlaybackFragment", "Загрузка трека закончилась")

        lifecycleScope.launch {
            viewModel.trackInfo.collect { currentTrack ->
                currentTrack.let {
                    binding.tvTrackTitle.text = it?.title
                    binding.tvArtistName.text = it?.artist
                    binding.tvAlbumName.text = it?.album

                    Glide.with(this@PlaybackFragment)
                        .load(it?.cover)
                        .error(R.drawable.ic_not_downloaded)
                        .into(binding.ivAlbumCover)

                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.playbackState.collect { isPlaying ->
                Log.d("PlaybackFragment", "Playback state changed: $isPlaying")
                binding.playPauseButton.setImageResource(
                    if (isPlaying) R.drawable.ic_stop else R.drawable.ic_play
                )
            }
        }

        lifecycleScope.launch {
            viewModel.progress.collect { progress ->
                binding.seekBar.progress = progress
            }
        }


        binding.playPauseButton.setOnClickListener {
            viewModel.playPause()
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    viewModel.seekTo(progress.toLong())
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}