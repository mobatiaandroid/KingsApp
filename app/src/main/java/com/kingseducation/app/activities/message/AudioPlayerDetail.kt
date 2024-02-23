@file:Suppress("UNREACHABLE_CODE")

package com.kingseducation.app.activities.message

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kingseducation.app.R


private var player: MediaPlayer? = null
private var handler2 = Handler()
private var seebbar: SeekBar? = null
class AudioPlayerDetail : AppCompatActivity() {

    lateinit var extras: Bundle
    lateinit var audio_title: String
    lateinit var audio_id: String
    lateinit var audio_updated: String

    lateinit var mContext: Context
    lateinit var playbutton_audio:ImageView
    lateinit var duration_time:TextView
    lateinit var textcurrent_time:TextView
    lateinit var back_arrow:ImageView
    var alert_type: String = ""
    var created_at: String = ""
    var title: String = ""
    var message: String = ""
    var updated_at: String = ""
    var url: String = ""
   // private lateinit var progressDialog: RelativeLayout
   var flag:Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_player_detail)
       // extras = intent.extras!!
        extras = intent.extras!!
        audio_title =  extras.getString("url")!!
        Log.e("url",audio_title)
        mContext = this


        seebbar = findViewById(R.id.seebbar)
        playbutton_audio = findViewById(R.id.playbutton)
        textcurrent_time = findViewById(R.id.textcurrent_time)
        duration_time = findViewById(R.id.duration_time)
        back_arrow=findViewById(R.id.back_arrow)
        player = MediaPlayer()
        seebbar!!.max = 100


        // progressDialog = findViewById(R.id.progressDialog)
       /* val aniRotate: Animation =
            AnimationUtils.loadAnimation(mContext, R.anim.linear_interpolator)
        progressDialog.startAnimation(aniRotate)*/
        back_arrow.setOnClickListener {
           // player!!.pause()
            finish()
        }
        if (player!!.isPlaying) {
            handler2.removeCallbacks(updater)
            player!!.pause()
            playbutton_audio.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24)
        } else {
            Log.e("suess", "success")
            player!!.start()
            playbutton_audio.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24)
            try {
                updateseekbar()
            } catch (e: Exception) {

            }
        }
        setUpMediaPlayer(audio_title)
        playbutton_audio.setOnClickListener {
            if(flag)
            {

                playbutton_audio.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24)
                player!!.start()
            }
            else
            {
                playbutton_audio.setImageResource(R.drawable.ic_baseline_play_circle_outline_24)
                player!!.pause()
            }
            flag = !flag
        }
    }

     fun setUpMediaPlayer( filename: String) {

        val mydir = mContext.getDir("audioCheck", Context.MODE_PRIVATE) //Creating an internal dir;


        try {
            //mediaPlayer1.reset();
            //mediaPlayer1 = MediaPlayer.create(mContext, R.raw.countdown_isg);
            //mediaPlayer1.setDataSource(AppPreferenceManager.getAudio(mContext));
            //mediaPlayer1.setDataSource(questionArray.get(questionCount).getQuestion());
            player!!.setDataSource(filename)
          //  Log.e("audioplay1", path)
            player!!.setOnPreparedListener(MediaPlayer.OnPreparedListener { mp -> mp.start() })
            player!!.prepare()
            duration_time.text = "/" + milliseconds(player!!.duration.toLong())

        } catch (exception: Exception) {
            //Toast.makeText(this, "failed to load audio" + exception.getMessage(), Toast.LENGTH_SHORT).show();
            println("failed for load" + exception.message)
        }

        //  }
    }


     val updater = Runnable {
         try {
             updateseekbar()
             val currentduration: Long = player!!.currentPosition.toLong()
             textcurrent_time.text=(milliseconds(currentduration).toString())
         }catch (e:Exception)
         {

         }

       // AppPreferenceManager.setCurrentTime(mContext, milliseconds(currentduration))
    }

     fun updateseekbar() {
        Log.e("success","success")

         Log.e("dfgdh", "success")
         try {
             seebbar!!.progress =
                 (player!!.currentPosition.toFloat() / player!!.duration * 100).toInt()
             System.out.print("seekbar" + seebbar)
             handler2.postDelayed(updater, 1000)
         } catch (e: InterruptedException) {
             e.printStackTrace()
         }

         /* Toast.makeText(this, "successs", Toast.LENGTH_SHORT).show();*/

     }

    fun milliseconds(milliscnd: Long): String {
        var timer = ""
        val secondString: String
        val hour = (milliscnd / (1000 * 60 * 60)).toInt()
        val min = (milliscnd % (1000 * 60 * 60)).toInt() / (1000 * 60)
        val sec = (milliscnd % (1000 * 60 * 60)).toInt() % (1000 * 60) / 1000
        if (hour > 0) {
            timer = "$hour;"
        }
        secondString = if (sec < 10) {
            "0$sec"
        } else {
            "" + sec
        }
        timer = "$timer$min:$secondString"
        return timer
    }
    override fun onPause() {
        super.onPause()
        player!!.pause()
    }


    override fun onDestroy() {
        super.onDestroy()
        player!!.stop()
        player!!.release()
    }



    override fun onBackPressed() {

          finish()

    }
}