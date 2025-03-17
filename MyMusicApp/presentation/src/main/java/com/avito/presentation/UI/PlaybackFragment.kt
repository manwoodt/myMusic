package com.avito.presentation.UI

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.avito.presentation.MusicService
import com.avito.presentation.R
import com.avito.presentation.databinding.FragmentPlaybackBinding
import com.avito.presentation.viewmodels.PlaybackViewModel
import com.bumptech.glide.Glide
import kotlinx.coroutines.delay
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
        startMusicService()
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
            viewModel.isPlaying.collect { isPlaying ->
                Log.d("PlaybackFragment", "Playback state changed: $isPlaying")
                binding.playPauseButton.setImageResource(
                    if (isPlaying) R.drawable.ic_stop else R.drawable.ic_play
                )
            }
        }

        lifecycleScope.launch {
            viewModel.progress.collect { progress ->
                binding.seekBar.progress = progress
                binding.currentTime.text = formatTime(progress)
                delay(500)
            }
        }


        lifecycleScope.launch {
            viewModel.duration.collect { duration ->
                Log.d("trackDuration", duration.toString())
                binding.trackDuration.text = formatTime(duration)
                if (duration > 0)
                    binding.seekBar.max = duration
            }
        }


        binding.playPauseButton.setOnClickListener {
            viewModel.playPause()
        }


        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) viewModel.seekTo(progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun startMusicService() {
        Log.d("PlaybackFragment", "startMusicService() called")
        val intent = Intent(requireContext(), MusicService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d("PlaybackFragment", "Starting foreground service")
            requireContext().startForegroundService(intent) // API 26+
        } else {
            Log.d("PlaybackFragment", "Starting background service")
            requireContext().startService(intent) // Для старых версий
        }
    }

    private fun formatTime(ms: Int): String {
        val minutes = ms / 60000
        val seconds = (ms % 60000) / 1000
        return String.format("%02d:%02d", minutes, seconds)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}