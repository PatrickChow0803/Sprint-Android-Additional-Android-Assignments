package com.patrickchow.mediaservicesassignment

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btn_play.isEnabled = false

        playOrPauseFunctionality()



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

    override fun onStart() {
        super.onStart()

        vv_recording.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.recording))

        vv_recording.setOnPreparedListener{mp ->
            btn_play.isEnabled = true
            mp?.let {
                sb_progress.max = mp.duration
            }
        }
    }
}
