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
        val trackId = args.trackId
        Log.d("PlaybackFragment", "Загрузка трека началась")
        viewModel.loadTrackbyId(trackId)
        Log.d("PlaybackFragment", "Загрузка трека закончилась")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.currentTrack.collect { currentTrack ->
                currentTrack.let {
                    binding.tvTrackTitle.text = it?.title
                    binding.tvArtistName.text = it?.artist
                    binding.tvAlbumName.text = it?.album

                    Glide.with(this@PlaybackFragment)
                        .load(it?.cover)
                        .error(com.avito.presentation.R.drawable.ic_not_downloaded)
                        .into(binding.ivAlbumCover)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.currentPosition.collect { position ->
                val minutes = position / 60000
                val seconds = (position % 60000) / 1000
                binding.currentTime.text = String.format("%02d:%02d", minutes, seconds)
            }
        }

        lifecycleScope.launch {
            viewModel.duration.collect { duration ->
                val minutes = duration / 60000
                val seconds = (duration % 60000) / 1000
                binding.trackDuration.text = String.format("%02d:%02d", minutes, seconds)
            }
        }

        lifecycleScope.launch {
            viewModel.progress.collect { progress ->
                binding.seekBar.progress = progress
            }
        }

        lifecycleScope.launch {
            viewModel.isPlaying.collect { isPlaying ->
                if (isPlaying) {
                    binding.playButton.visibility = View.GONE
                    binding.pauseButton.visibility = View.VISIBLE
                } else {
                    binding.playButton.visibility = View.VISIBLE
                    binding.pauseButton.visibility = View.GONE
                }
            }
        }

        binding.playButton.setOnClickListener {
            viewModel.play(viewModel.currentTrack.value?.preview ?: "")
        }

        binding.pauseButton.setOnClickListener {
            viewModel.pause()
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    // Перематываем трек на указанную позицию
                    viewModel.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Этот метод можно оставить пустым, если не требуется
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Этот метод можно оставить пустым, если не требуется
            }
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}