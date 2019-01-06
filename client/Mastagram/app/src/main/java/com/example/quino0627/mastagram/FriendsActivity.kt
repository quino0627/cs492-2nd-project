package com.example.quino0627.mastagram

import android.content.ContentResolver
import android.media.Image
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.quino0627.mastagram.Model.Post
import com.example.quino0627.mastagram.Model.User
import com.facebook.Profile
import kotlinx.android.synthetic.main.post_view.*
import org.jetbrains.anko.image
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FriendsActivity: Fragment(), FriendsAdapter.onItemSelectedListener{
    override fun onItemSelected(selectedFriend: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        var friendsList: ArrayList<User> = ArrayList()
        var adapder : FriendsAdapter = FriendsAdapter(friendsList)
    }

    lateinit var retrofitApi:RetrofitApi

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return inflater.inflate(R.layout.fragment_friends, container, false)

        val rootView = inflater!!.inflate(R.layout.fragment_friends, container, false)
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.friends_recycler_view) as RecyclerView


        var asdf = getFriends()
        var tempList2 = ArrayList<User>()
        retrofitApi = APIUtils.getUserService()
        val call2 = retrofitApi.getFriendsList(MainActivity.myFBUserId)
        Log.e("NIKO", MainActivity.myFBUserId)
        call2.enqueue(object: Callback<List<User>> {

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.e("ERROROROR: ", t.message)
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if(response.isSuccessful){
                    Log.e("SUCCESS", "SUCCESs")
                    tempList2 = response.body() as ArrayList<User>

                    friendsList = tempList2
                    friendsList.addAll(asdf)
                    Log.e("BANANA", "BANANA")
                    val adapter = FriendsAdapter(friendsList)
                    val formanage = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
                    recyclerView.layoutManager = formanage
                    recyclerView.adapter = adapter
                    recyclerView.setHasFixedSize(false)

                }
            }

        })

//        val adapter = FriendsAdapter(friendsList)
//        val formanage = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
//        recyclerView.layoutManager = formanage
//        recyclerView.adapter = adapter
//        recyclerView.setHasFixedSize(false)

//        Log.e("BANANA", "BANANA")
//        val adapter = FriendsAdapter(friendsList)
//        val formanage = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
//        recyclerView.layoutManager = formanage
//        recyclerView.adapter = adapter
//        recyclerView.setHasFixedSize(false)

        var myNameTextView = rootView.findViewById<AppCompatTextView>(R.id.my_name)
        var myPhoneTextView = rootView.findViewById<AppCompatTextView>(R.id.my_phone)
        var myProfileImageView = rootView.findViewById<ImageView>(R.id.my_profile_image)

        retrofitApi = APIUtils.getUserService()
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


        return rootView

    }

    private fun getFriends(): ArrayList<User> {
        val tempList = ArrayList<User>()
        val resolver: ContentResolver = activity!!.contentResolver
        var contentResolver = activity!!.contentResolver
        val cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.Contacts.SORT_KEY_PRIMARY)

        if(cursor.count>0){
            while (cursor.moveToNext()){
                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val phoneNumber = (cursor.getString(
                    cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)
                )).toInt()
                if (phoneNumber > 0){
                    val cursorPhone = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", arrayOf(id), null)
                    if(cursorPhone.count>0){
                        while (cursorPhone.moveToNext()){
                            val phoneNumValue = cursorPhone.getString(
                                cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                            )
                            val tempUser = User()
                            tempUser.name = name
                            tempUser.phone = phoneNumValue
                            tempList.add(tempUser)
                        }
                    }
                    cursorPhone.close()
                }
            }
        } else{
            toast("No Contacts Availiable!")
        }
        cursor.close()


        Log.e("APPLE", "APPLE")



        return tempList
    }


}


