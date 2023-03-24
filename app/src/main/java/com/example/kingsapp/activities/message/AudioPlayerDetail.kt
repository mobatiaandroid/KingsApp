@file:Suppress("UNREACHABLE_CODE")

package com.example.kingsapp.activities.message

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kingsapp.R

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tcking.github.com.giraffeplayer.GiraffePlayer
import tv.danmaku.ijk.media.player.IMediaPlayer



lateinit var player: GiraffePlayer
class AudioPlayerDetail : AppCompatActivity() {

    lateinit var extras: Bundle
    lateinit var audio_title: String
    lateinit var audio_id: String
    lateinit var audio_updated: String

    lateinit var mContext: Context
    var alert_type: String = ""
    var created_at: String = ""
    var title: String = ""
    var message: String = ""
    var updated_at: String = ""
    var url: String = ""
    private lateinit var progressDialog: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_player_detail)
        extras = intent.extras!!


        mContext = this
        audio_title = extras.getString("url")!!
        Log.e("url",audio_title)
        player = GiraffePlayer(this)
        progressDialog = findViewById(R.id.progressDialog)
        val aniRotate: Animation =
            AnimationUtils.loadAnimation(mContext, R.anim.linear_interpolator)
        progressDialog.startAnimation(aniRotate)

        player!!.play(audio_title)
        player!!.onComplete {
            Toast.makeText(applicationContext, "Play completed", Toast.LENGTH_SHORT).show()
        }.onInfo { what, extra ->
            when (what) {
                IMediaPlayer.MEDIA_INFO_BUFFERING_START -> {

                }
                IMediaPlayer.MEDIA_INFO_BUFFERING_END -> {

                }
                IMediaPlayer.MEDIA_INFO_NETWORK_BANDWIDTH -> {

                }
                IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START -> {

                }
            }
        }.onError { what, extra ->

            Toast.makeText(applicationContext, "Can't play this audio$what", Toast.LENGTH_SHORT)
                .show()

        }
    }




    override fun onPause() {
        super.onPause()
        player!!.onPause()
    }

    override fun onResume() {
        super.onResume()
        player!!.onResume()

    }

    override fun onDestroy() {
        super.onDestroy()
        player!!.onDestroy()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        player!!.onConfigurationChanged(newConfig)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        return
        super.onBackPressed()
    }

}