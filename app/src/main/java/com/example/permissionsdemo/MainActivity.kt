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
    private val MULTIPLE_PERMISSION_CODE = 200
    private var requestPermissionList:ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        button_single.setOnClickListener(this)
        button_multiple.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view){
            button_single -> checkForCameraPermission()
            button_multiple -> checkForMultiplePermission()

        }
    }

    private fun checkForMultiplePermission(){
        var permissions = arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION

        )
        for(permission in permissions){
            if(ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED){
                requestPermissionList.add(permission)
            }
            requestMultiplePermission()

        }
    }

    private fun requestMultiplePermission() {
        ActivityCompat.requestPermissions(
                this,
                arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ),
                MULTIPLE_PERMISSION_CODE
        )
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
            MULTIPLE_PERMISSION_CODE ->{
                if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(applicationContext, "Multiple permission denied", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(applicationContext, "Multiple permission granted", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun openCamera(){
        var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }
}