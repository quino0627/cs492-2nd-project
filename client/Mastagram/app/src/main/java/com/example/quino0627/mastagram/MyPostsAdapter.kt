package com.example.quino0627.mastagram

import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.widget.CardView
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.Log.d
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.quino0627.mastagram.Model.Post
import org.jetbrains.anko.notificationManager
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onLongClick
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPostsAdapter(val postList: List<Post>, val context: Context , val myPostsActivity: MyPostsActivity) :

    RecyclerView.Adapter<MyPostsAdapter.ViewHolder>() {
    lateinit var retrofitApi: RetrofitApi
    private lateinit var listener1: MyPostsAdapter.OnItemSelectedListener

    fun setClickListener(listener1: OnItemSelectedListener) {
        this.listener1 = listener1
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyPostsAdapter.ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.gallery_view, p0, false)
        return MyPostsAdapter.ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(p0: MyPostsAdapter.ViewHolder, p1: Int) {
        Glide.with(context).load("http://143.248.140.106:1080/" + postList[p1].pirctueUrl).into(p0.postImage)
        p0.container.onClick {
            listener1.onItemSelected(postList[p1])
        }
        p0.container.onLongClick {
            lateinit var retrofitApi: RetrofitApi
            val builder = AlertDialog.Builder(this@MyPostsAdapter.context, R.style.AlertTheme)
            builder.setTitle("DELETE?")
            builder.setNegativeButton("취소") { _, _ ->
                d("alert cancel", "cancel")
            }
            builder.setPositiveButton("확인") { _, _ ->
                lateinit var retrofitApi: RetrofitApi
                d("alert ok", " ok")
                deletePost(postList[p1].post_id, postList)
            }
            builder.show()
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var postImage = itemView.findViewById<ImageView>(R.id.iview)
        var container = itemView.findViewById<CardView>(R.id.image_container)

    }

    interface OnItemSelectedListener {
        fun onItemSelected(selectedPost: Post)
    }

    fun deletePost(post_id: String, postList: List<Post>) {
        Log.d("DELETEPOST", post_id)
        retrofitApi = APIUtils.getUserService()
        val call = retrofitApi.deletePost(post_id)
        call.enqueue(object : Callback<Post> {
            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.e("ERROR: ", t.message)
            }

            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful()) {
                    context.toast("Post delete Successfully!")
//                    postList.forEach { x ->
//                        if (x.post_id == post_id) {
//                            notifyItemRemoved(postList.indexOf(x));
//                        }
//                    }
//                    notifyDataSetChanged()
//                    val adapter = MyPostsAdapter(postList, context!!)
//                    adapter.setClickListener(listener1)
                    myPostsActivity.onResume()
                }

            }

        })
    }


}