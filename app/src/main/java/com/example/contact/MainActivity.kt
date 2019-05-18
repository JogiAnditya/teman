package com.example.contact

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tampilMyFriendFragment()
    }

    private fun gantiFragment(fragmentManager: FragmentManager,
                              fragment: Fragment, frameId:Int) {
        val transaction =fragmentManager.beginTransaction()
        transaction.replace(frameId,fragment)
        transaction.commit()
    }
    fun tampilMyFriendFragment(){
        gantiFragment(supportFragmentManager,MyFriendFragment.newInstance(),
            R.id.framelayout)
    }
    fun tampilTambahTemanFragment(){
        gantiFragment(supportFragmentManager,TambahTemanFragment.newInstance(),
            R.id.framelayout)
    }
}