package com.patrickchow.mediaservicesassignment

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_play.isEnabled = false



        playOrPauseFunctionality()

        seekBarFunctionality()
    }

    private fun playOrPauseFunctionality() {
        // In the on-click listener for the button,
        // If the video is not playing, start it
        // else pause the video
        // Start the animation

        btn_play.setOnClickListener {
            if(!vv_recording.isPlaying){
                vv_recording.start()
            }
        }

        btn_pause.setOnClickListener {
            //Pause it
            if(vv_recording.isPlaying){
                vv_recording.pause()
            }
        }

    }

    private fun seekBarFunctionality() {
        // In the SeekBar listener, when the seekbar progress is changed,
        // update the video progress

        sb_progress.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar.let {
                    vv_recording.seekTo(seekBar!!.progress)
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()

        vv_recording.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.recording))

        vv_recording.setOnPreparedListener{mp ->
            btn_play.isEnabled = true
            mp?.let {
                sb_progress.max = mp.duration
            }
        }

        val handler = Handler()
        this@MainActivity.runOnUiThread(object: Runnable{
            override fun run() {
                if (vv_recording != null){
                    val currentPosition = vv_recording.currentPosition
                    sb_progress.progress = currentPosition
                }
                handler.postDelayed(this, 1000)
            }
        })
    }

    override fun onStop() {
        super.onStop()
        vv_recording.pause()
    }
}