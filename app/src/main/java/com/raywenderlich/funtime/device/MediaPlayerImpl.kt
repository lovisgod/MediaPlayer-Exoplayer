package com.raywenderlich.funtime.device

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.raywenderlich.funtime.R

class MediaPlayerImpl: MediaPlayer {
    override fun releasePlayer() {
        exoPlayer.stop()
        exoPlayer.release()
    }

    override fun getPlayerImpl(context: Context): ExoPlayer {
        this.context = context
        initializePlayer()
        return exoPlayer
    }

    override fun play(url: String) {
        val userAgent = Util.getUserAgent(context, context.getString(R.string.app_name))
        val mediaSources = ExtractorMediaSource.Factory(DefaultDataSourceFactory(context, userAgent))
                .setExtractorsFactory(DefaultExtractorsFactory())
                .createMediaSource(Uri.parse(url))
        exoPlayer.prepare(mediaSources)
        exoPlayer.playWhenReady = true
    }

    private lateinit var exoPlayer: ExoPlayer
    private lateinit var context:Context

    private fun initializePlayer(){
        val trackSelector = DefaultTrackSelector()
        val loadControl = DefaultLoadControl()
        val rendererFactory = DefaultRenderersFactory(context)
        exoPlayer = ExoPlayerFactory.newSimpleInstance(rendererFactory,trackSelector, loadControl)
    }
}