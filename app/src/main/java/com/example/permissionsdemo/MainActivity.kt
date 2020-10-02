package com.example.permissionsdemo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val CAMERA_REQUEST_CODE = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        button_single.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        checkForCameraPermission()
    }

    private fun checkForCameraPermission(){
        var permission = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
        )
        if(permission != PackageManager.PERMISSION_GRANTED){
            /// we dont have the permission for camera and hence we have to request for the permission

            requestCameraPermission()
        }else{
            openCamera()
        }


    }

    private fun requestCameraPermission(){
        ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
        CAMERA_REQUEST_CODE
        )

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            CAMERA_REQUEST_CODE ->{
                if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(applicationContext, "permission denied", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(applicationContext, "permission granted", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private fun openCamera(){
        var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }
}