package com.example.quino0627.mastagram

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.example.quino0627.mastagram.Model.Post
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity: Fragment(){

    lateinit var retrofitApi:RetrofitApi
    lateinit var postList :MutableList<Post>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        retrofitApi = APIUtils.getUserService()

        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.home_recycler_view) as RecyclerView
        var initialPost = Post()
//        initialPost.myDate = "20181010"
        initialPost.contents = "This is initial CONTENT"
        var list2 = listOf<Post>(initialPost)
        postList = list2.toMutableList()
        postList.add(initialPost)
        //getPostsList()


        var call = retrofitApi.getTimeLine(MainActivity.myFBUserId)
        call.enqueue(object:Callback<List<Post>>{
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                Log.d("IN THE CALLBACK", "asdf")
                if (response.isSuccessful){
                    Log.d("IN THE IF", "asdf")
                    postList = response.body() as MutableList<Post>
                    postList.reverse()
                    toast("Post Load Successfully!")
                   // Log.e("ASDFASDF",postList[0].uploader_name.toString())
                    val adapter = PostItemAdapter(postList, (activity as Context?)!!)
                    val formanage = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
                    recyclerView.layoutManager = formanage
                    recyclerView.adapter = adapter
                    recyclerView.setHasFixedSize(false)
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.e("Error: " ,t.message)
            }
        })

//
//
//        var call = retrofitApi.posts
//        call.enqueue(object:Callback<List<Post>>{
//            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
//                Log.d("IN THE CALLBACK", "asdf")
//                if (response.isSuccessful){
//                    Log.d("IN THE IF", "asdf")
//                    postList = response.body() as MutableList<Post>
//                    postList.reverse()
//                    toast("Post Load Successfully!")
//                    val adapter = PostItemAdapter(postList, (activity as Context?)!!)
//                    val formanage = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
//                    recyclerView.layoutManager = formanage
//                    recyclerView.adapter = adapter
//                    recyclerView.setHasFixedSize(false)
//                }
//            }
//
//            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
//                Log.e("Error: " ,t.message)
//            }
//        })
//

        return rootView
    }



//    fun getPostsList(){
//        Log.d("IN THE FUCTION","asdf")
//        var call = retrofitApi.posts
//        Log.d("IN THE FUNCTION2", "ASDF")
//        call.enqueue(object:Callback<List<Post>>{
//            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
//                Log.d("IN THE CALLBACK", "asdf")
//                if (response.isSuccessful){
//                    Log.d("IN THE IF", "asdf")
//                    postList = response.body() as MutableList<Post>
//
//                    toast("Post Load Successfully!")
//                }
//            }
//
//            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
//                Log.e("Error: " ,t.message)
//            }
//        })
//        Log.d("AFTER THE CALL", "CALL")
//    }

    override fun onResume() {
        super.onResume()
        val recyclerView = home_recycler_view
        var call = retrofitApi.getTimeLine(MainActivity.myFBUserId)
        call.enqueue(object:Callback<List<Post>>{
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful){
                    postList = response.body() as MutableList<Post>
                    postList.reverse()

                    // Log.e("ASDFASDF",postList[0].uploader_name.toString())
                    val adapter = PostItemAdapter(postList, (activity as Context?)!!)
                    val formanage = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
                    recyclerView.layoutManager = formanage
                    recyclerView.adapter = adapter
                    recyclerView.setHasFixedSize(false)
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.e("Error: " ,t.message)
            }
        })
    }

}