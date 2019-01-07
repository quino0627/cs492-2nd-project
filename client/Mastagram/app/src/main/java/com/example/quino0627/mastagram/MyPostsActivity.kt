package com.example.quino0627.mastagram

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import com.example.quino0627.mastagram.Model.Post
import com.example.quino0627.mastagram.Model.User
import kotlinx.android.synthetic.main.activity_my_posts.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPostsActivity : Fragment() , MyPostsAdapter.OnItemSelectedListener{


    override fun onItemSelected(selectedPost: Post) {
        Log.e("SELECTED", "!!!!")
        var intent = Intent(activity, MyPostDetailsActivity::class.java)
        intent.putExtra("name", selectedPost.uploader_name)
        intent.putExtra("imageUrl", selectedPost.pirctueUrl)
        intent.putExtra("content", selectedPost.contents)
        intent.putExtra("tag1", selectedPost.tag[0])
        intent.putExtra("tag2", selectedPost.tag[1])
        intent.putExtra("tag3", selectedPost.tag[2])

        startActivity(intent)
    }

    lateinit var postList:MutableList<Post>
    lateinit var retrofitApi:RetrofitApi

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.activity_my_posts, container, false)
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.my_post_recycler_view) as RecyclerView

        retrofitApi = APIUtils.getUserService()

        var myNameTextView = rootView.findViewById<AppCompatTextView>(R.id.my_name)
        var myPhoneTextView = rootView.findViewById<AppCompatTextView>(R.id.my_phone)



        val call = retrofitApi.getUser(MainActivity.myFBUserId)
        call.enqueue(object: Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("ERROR: ", t.message)
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.isSuccessful()){
                    myNameTextView.text = (response.body() as User).name
                    myPhoneTextView.text = (response.body() as User).phone
                }
            }

        })

        var call2 = retrofitApi.getOnesPosts(MainActivity.myFBUserId)
        call2.enqueue(object:Callback<List<Post>>{
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                Log.e("MYPOSTPAGE", "CONSOLE")
                Log.e("MYPOSTPAGE", (response.body()==null).toString())
                if (response.isSuccessful){

                    postList = response.body() as MutableList<Post>
                    postList.reverse()
                    toast("MYPOST SIZE IS "+postList.size)
                    // Log.e("ASDFASDF",postList[0].uploader_name.toString())

                    val adapter = MyPostsAdapter(postList, activity!!)
                    val forManage = GridLayoutManager(activity, 3, GridLayout.VERTICAL, false)
                    adapter.setClickListener(this@MyPostsActivity)
                    recyclerView.layoutManager = forManage
                    recyclerView.adapter = adapter
                    recyclerView.setHasFixedSize(false)
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.e("Error: " ,t.message)
            }
        })


        return rootView
    }

    override fun onResume() {
        super.onResume()
        val recyclerView = my_post_recycler_view
        var call = retrofitApi.getOnesPosts(MainActivity.myFBUserId)
        call.enqueue(object:Callback<List<Post>>{
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful){
                    postList = response.body() as MutableList<Post>
                    postList.reverse()
                    toast("Post Load Successfully!")
                    // Log.e("ASDFASDF",postList[0].uploader_name.toString())

                    val adapter = MyPostsAdapter(postList, activity!!)
                    val formanage = GridLayoutManager(activity, 3, GridLayout.VERTICAL, false)
                    adapter.setClickListener(this@MyPostsActivity)
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
