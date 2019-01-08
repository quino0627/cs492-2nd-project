package com.example.quino0627.mastagram

import android.Manifest
import android.content.Intent
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.quino0627.mastagram.Login.LoginActivity
import com.example.quino0627.mastagram.Model.RegisterCheck
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger

import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.FacebookCallback
import com.facebook.login.LoginManager



class MainActivity : AppCompatActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    val MULTIPLE_PERMISSIONS = 10
    val permissions = arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    lateinit var callbackManager: CallbackManager
    lateinit var retrofitApi: RetrofitApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)



        retrofitApi = APIUtils.getUserService()
        var accessToken = AccessToken.getCurrentAccessToken()
        var isLogedIn = accessToken != null && !accessToken.isExpired
        Log.d("ISLOGEDIN IS" , isLogedIn.toString())
//        if (!isLogedIn) {
//            val loginIntent = Intent(this@MainActivity, LoginActivity::class.java)
//            startActivity(loginIntent)
//        }
//        else{
//            Companion.myFBUserId = accessToken.userId
//        }
//
//        var userId = accessToken.userId //이게 디비에 올라가서 유저_id 가 되고 이걸로 유저를 식별 유닉!
//        Log.d("THIS IS USERID", userId.toString())
//
//        var call = retrofitApi.isRegistered(userId)
//        call.enqueue(object: Callback<RegisterCheck> {
//            override fun onResponse(call: Call<RegisterCheck>, response: Response<RegisterCheck>) {
//                if (response.isSuccessful){
//                    Log.d("userId is ",userId)
//                    Log.d("RESULT IS?" , response.body()!!.result.toString())
//                    if (response.body()!!.result == false){
//                        val registerIntent = Intent(this@MainActivity, RegisterActivity::class.java)
//                        startActivity(registerIntent)
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<RegisterCheck>, t: Throwable) {
//                Log.e("fail to get BOOLEAN", t.message)
//            }
//
//        })


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

        ActivityCompat.requestPermissions(this, permissions, MULTIPLE_PERMISSIONS)

    }

    override fun onResume() {
        super.onResume()
        var tabLayout : TabLayout = findViewById(R.id.tabs)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val position = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        if(tabLayout.selectedTabPosition == 1){
            val tab = tabLayout.getTabAt(0)
            tab!!.select()
        }


    }

    override fun onStart() {
        super.onStart()
        var accessToken = AccessToken.getCurrentAccessToken()
        var isLogedIn = accessToken != null && !accessToken.isExpired
        if (!isLogedIn) {
            val loginIntent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(loginIntent)
        }
        else{
            Companion.myFBUserId = accessToken.userId
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {
            when(position){
                0 -> {
                    return HomeActivity()
                }
                1->{
                    return UploadActivity()
                }
                2->{
                    return FriendsActivity()
                }
                3->{
                    return MyPostsActivity()
                }
                else -> return null
            }
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 4
        }

        override fun getPageTitle(position: Int) : CharSequence?{
            when(position){
                0 -> return "HOME"
                1 -> return "UPLOAD"
                2 -> return "FRIENDS"
                3->return "MYPOSTS"
            }
            return null
        }
    }

    companion object {
        var myFBUserId:String = "DEFAULT"
    }


}
