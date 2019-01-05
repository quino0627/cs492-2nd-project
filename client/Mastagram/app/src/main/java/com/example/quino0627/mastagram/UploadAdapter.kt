package com.example.quino0627.mastagram

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import org.jetbrains.anko.sdk25.coroutines.onClick

class UploadAdapter(private val uriList: ArrayList<String>, private val mContext: Context): RecyclerView.Adapter<UploadAdapter.MyHolder>(){

    private lateinit var listener: UploadAdapter.OnItemSelectedListener

    fun setClickListener(listener: UploadAdapter.OnItemSelectedListener){
        this.listener = listener
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): UploadAdapter.MyHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.gallery_view,p0, false)
        return UploadAdapter.MyHolder(v, mContext)
    }

    override fun getItemCount(): Int {
        //Log.d("CheckUrlLength", uriList.size.toString())
        return uriList.size
    }

    override fun onBindViewHolder(p0: UploadAdapter.MyHolder, p1: Int) {
        p0.index(uriList[p1])

        p0.container.onClick {
            listener.onItemSelected(uriList[p1])
        }

    }

    class MyHolder(itemView : View, mContext: Context) : RecyclerView.ViewHolder(itemView){
        val mycontext = mContext
        val container = itemView.findViewById<View>(R.id.iview) as ImageView

        fun index(item : String){
            Glide.with(mycontext).load(item).into(container)
        }
    }

    interface OnItemSelectedListener {
        fun onItemSelected(selectedImage : String)

    }

}