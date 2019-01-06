package com.example.quino0627.mastagram

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout

class UploadActivity: Fragment(), UploadAdapter.OnItemSelectedListener{

    companion object {
        var photoList1: ArrayList<String> = ArrayList()
        var sub_photoList :ArrayList<String> = ArrayList()
    }

    override fun onItemSelected(selectedImage: String) {
        var intent = Intent(activity, UploadPostActivity::class.java)
        intent.putExtra("image", selectedImage)
        startActivity(intent)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var rootView = inflater!!.inflate(R.layout.fragment_upload,container, false)
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.upload_view) as RecyclerView
        photoList1 = setPhotos()
        sub_photoList.addAll(photoList1)

        val adapter = UploadAdapter(photoList1!!, activity!!)

        adapter.setClickListener(this)

        val formanage = GridLayoutManager(activity, 4, GridLayout.VERTICAL, false)
        recyclerView.layoutManager = formanage
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(false)

        return rootView
    }

    fun setPhotos(): ArrayList<String> {
        val photoList : ArrayList<String> = ArrayList()
        Log.d("Check1", "Doing setPhotos in MainActivity")
        //val cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,null,null,null, MediaStore.Images.ImageColumns.DATE_TAKEN + "DESC")
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = activity!!.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, // 이미지 컨텐트 테이블
            projection,
            null, null, null
        )// DATA를 출력
        // 모든 개체 출력

        Log.d("Cursor in setPhotos", cursor.toString())

        if (cursor != null) {
            while (cursor.moveToNext()) {
                val dataColumnIndex = cursor.getColumnIndex(projection[0])
                val filePath = cursor.getString(dataColumnIndex)
                val imageUri = Uri.parse(filePath)
                //photoList.add(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)))
                photoList.add(imageUri.toString())

                Log.d("There is Photo in setPhotos", imageUri.toString())

                //photoList.add(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)))
            }
        }
        cursor.close()
        val adapter = UploadAdapter(photoList, activity!!)
        Log.d("isAdapterExist?",adapter.toString())
        return photoList
    }


}