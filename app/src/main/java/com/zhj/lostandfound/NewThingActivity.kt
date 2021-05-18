package com.zhj.lostandfound

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.zhj.lostandfound.centers.CenterActivity
import kotlinx.android.synthetic.main.activity_new_thing.*
import java.text.SimpleDateFormat
import java.util.*

class NewThingActivity : AppCompatActivity() {
    private val PERMISSION_CAMERA_REQUEST_CODE = 12
    private val CAMERA_REQUEST_CODE2 = 10
    private var mCameraUri: Uri? = null
    var IsPhoto = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_thing)
        val whatthing = intent.getIntExtra("whatthing", 0)

        val PREF_FILE_NAME = "Mydata"
        val LoginID = "loginid"
        val pref = this.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        val userid = pref.getString(LoginID, "")

        if (whatthing == 1) {//true为捡到东西
            tvTitle.text = "您好，$userid，请问您捡到什么东西了？"
            tvdetali1.text = "请简要描述您捡到的物品\n例如：一把钥匙，一个耳机等"
            tvdetali2.text = "请补充更多的细节（如在何时何处捡到）\n以及联系方式"
        } else {
            tvTitle.text = "您好，$userid，请问您丢失什么东西了？"
            tvdetali1.text = "请简要描述您捡到的物品\n例如：一把钥匙，一个耳机等"
            tvdetali2.text = "请补充更多的细节（如在何时何处丢失）\n以及联系方式"
        }


        buttoncam.setOnClickListener {
            checkPermissionCamera()
        }

        buttoncancel.setOnClickListener {
            val intent = Intent(this, NewThingActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            this.startActivity(intent)
        }

        buttonnew.setOnClickListener {
            val titleuserin = EditTextTalk.text.toString()
            val detailuserin = EditTextdetails.text.toString()
            val sdf = SimpleDateFormat("yyyyMMddHHmmss").format(Date()) + "_" + userid
            val db = this.openOrCreateDatabase("things.db", MODE_PRIVATE, null)
            val sql = "INSERT INTO myTable VALUES(?,?,?,?,?,?,?)"

            //写入一个空的留言系统板，由唯一标识码sdf来标注
            val db2 = this.openOrCreateDatabase("things.db", MODE_PRIVATE, null)
            val sql2 = "INSERT INTO myTableTwo VALUES(?,?,?,?,?,?,?,?)"


            if (IsPhoto == 0) {
                MaterialAlertDialogBuilder(this)
                    .setTitle("确定不添加图片码？")
                    .setMessage("没有图片不能有效帮助到您哦")
                    .setNeutralButton("取消") { dialog, which ->

                    }
                    .setPositiveButton("是") { dialog, which ->
                        db2.execSQL(sql2, arrayOf(sdf, 0, "", "", "", "", "", ""))
                        db2.close()
                        db.execSQL(
                            sql,
                            arrayOf(
                                sdf, userid, whatthing, titleuserin, detailuserin, 0, ""
                            )
                        )
                        db.close()
                        val intent = Intent(this, CenterActivity::class.java)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        this.startActivity(intent)
                    }
                    .show()
            } else {
                db2.execSQL(sql2, arrayOf(sdf, 0, "", "", "", "", "", ""))
                db2.close()
                db.execSQL(
                    sql,
                    arrayOf(sdf, userid, whatthing, titleuserin, detailuserin, 1, mCameraUri)
                )
                db.close()
                val intent = Intent(this, CenterActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                this.startActivity(intent)
            }
        }
    }

    fun checkPermissionCamera() {
        val hasCameraPermission = ContextCompat.checkSelfPermission(
            application, Manifest.permission.CAMERA
        )
        if (hasCameraPermission == PackageManager.PERMISSION_GRANTED) {
            //有权限，调起相机拍照。
            openCamera()
        } else {
            //没有权限，申请权限。
            requestPermissions(
                arrayOf(Manifest.permission.CAMERA), PERMISSION_CAMERA_REQUEST_CODE
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE2) {
            if (resultCode == RESULT_OK) {
                imageView.setImageURI(mCameraUri)
            } else {
                Toast.makeText(this, "取消", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_CAMERA_REQUEST_CODE) {
            if (grantResults.size > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                //允许权限，有调起相机拍照。
                openCamera()
            } else {
                //拒绝权限，弹出提示框。
                Toast.makeText(this, "拍照权限被拒绝", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun openCamera() {
        val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        var photoUri: Uri? = null
        photoUri = createImageUri()
        IsPhoto = 1
        mCameraUri = photoUri
        if (photoUri != null) {
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            startActivityForResult(captureIntent, CAMERA_REQUEST_CODE2)
        }

    }

    private fun createImageUri(): Uri? {
        val status = Environment.getExternalStorageState()
        return if (status == Environment.MEDIA_MOUNTED) {
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ContentValues())
        } else {
            contentResolver.insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, ContentValues())
        }
    }
}