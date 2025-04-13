package com.example.myapplication

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.SurfaceView
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.agora.rtc2.video.*
import io.agora.rtc2.*

class CallActivity : AppCompatActivity() {

    companion object {
        private const val PERMISSION_REQUEST = 22
    }

    private val appId = R.string.agora_api
    private var mRtcEngine: RtcEngine ?= null
    private val channel = R.string.channel.toString()
    private val token = R.string.temp_token.toString()

    private fun initialise() {
        try {
            val config = RtcEngineConfig().apply {
                mContext = applicationContext
                mAppId = appId
                mEventHandler = mRtcEventHandler
            }
            mRtcEngine = RtcEngine.create(config)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun joinChannel() {
        val options = ChannelMediaOptions().apply {
            clientRoleType = Constants.CLIENT_ROLE_BROADCASTER
            channelProfile = Constants.CHANNEL_PROFILE_COMMUNICATION
            publishMicrophoneTrack = true
            publishCameraTrack = true
        }
        mRtcEngine?.joinChannel(token, channel, 0, options)
    }

    private val mRtcEventHandler = object : IRtcEngineEventHandler() {
        override fun onJoinChannelSuccess(channel: String?, uid: Int, elapsed: Int) {
            super.onJoinChannelSuccess(channel, uid, elapsed)
            runOnUiThread {
                Toast.makeText(this@CallActivity, "Joined channel $channel", Toast.LENGTH_LONG).show()
            }
        }

        override fun onUserJoined(uid: Int, elapsed: Int) {
            super.onUserJoined(uid, elapsed)
            runOnUiThread {
                Toast.makeText(this@CallActivity, "User $uid joined", Toast.LENGTH_LONG).show()
            }
        }

        override fun onUserOffline(uid: Int, reason: Int) {
            super.onUserOffline(uid, reason)
            runOnUiThread {
                Toast.makeText(this@CallActivity, "User $uid is offline", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun enableVideo() {
        mRtcEngine?.apply {
            enableVideo()
            startPreview()
        }
    }

    private fun setLocal() {
        val c: FrameLayout = findViewById(R.id.remote_video)
        val surfaceView = SurfaceView(baseContext)
        c.addView(surfaceView)
        mRtcEngine?.setupLocalVideo(VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT, 0))
    }

    private fun setRemote(uid: Int) {
        val c: FrameLayout = findViewById(R.id.local_video)
        val surfaceView = SurfaceView(applicationContext).apply { setZOrderMediaOverlay(true) }
        c.addView(surfaceView)
        mRtcEngine?.setupRemoteVideo(VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT, uid))
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this@CallActivity, getRequiredPermissions(), PERMISSION_REQUEST)
    }

    private fun getRequiredPermissions(): Array<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            arrayOf(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.RECORD_AUDIO,
                android.Manifest.permission.READ_PHONE_STATE,
                android.Manifest.permission.BLUETOOTH_CONNECT
            )
        }
        else {
            arrayOf(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.RECORD_AUDIO
            )
        }
    }

    private fun checkPermissions(): Boolean {
        return getRequiredPermissions().all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == PERMISSION_REQUEST && checkPermissions())
            startVideoCall()
    }

    private fun startVideoCall() {
        initialise()
        enableVideo()
        setLocal()
        joinChannel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_call)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if(checkPermissions())
            startVideoCall()
        else
            requestPermission()

    }

    override fun onDestroy() {
        super.onDestroy()
        clean()
    }

    private fun clean() {
        mRtcEngine?.apply {
            stopPreview()
            leaveChannel()
        }
        mRtcEngine = null
    }
}