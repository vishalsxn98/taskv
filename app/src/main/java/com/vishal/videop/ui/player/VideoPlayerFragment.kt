package com.vishal.videop.ui.player

import com.vishal.videop.R
import com.vishal.videop.commons.base.BaseFragment
import com.vishal.videop.databinding.FragmentVideoPalyerBinding

class VideoPlayerFragment :
    BaseFragment<FragmentVideoPalyerBinding>(R.layout.fragment_video_palyer) {

    override fun onInitDataBinding() {
        viewBinding.andExoPlayerView.setSource(arguments?.getString("path") ?: "")
    }
}