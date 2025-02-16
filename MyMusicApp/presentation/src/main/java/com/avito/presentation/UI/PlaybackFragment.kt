package com.avito.presentation.UI

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
      //  Log.d()
        viewModel.loadTrackbyId(trackId)
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

//        viewModel.isPlaying.collect { isPlaying ->
//
//            lifecycleScope.launch {
//                if (isPlaying)
//                    viewModel.pause()
//                else {
//                    val track = viewModel.currentTrack.value?.preview
//                    if (track != null) {
//                        viewModel.play(track)
//                    }
//                }
//            }
//
//
//        }

        binding.playPauseButton.setOnClickListener {
            viewModel.stop()
        }


    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}