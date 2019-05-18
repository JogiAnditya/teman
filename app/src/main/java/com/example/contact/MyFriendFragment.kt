package com.example.contact

import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contact.data.AppDatabase
import com.example.contact.data.MyFriendDao
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.friendlist.*
import java.util.Observer

class MyFriendFragment : Fragment() {

    companion object {
        fun newInstance(): MyFriendFragment {
            return MyFriendFragment()
        }
    }

    lateinit var listTeman: MutableList<FriendList>

    private var db: AppDatabase? = null
    private var myFriendDao: MyFriendDao? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.friendlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //initLocalDB()

        fabAdd.setOnClickListener {
            (activity as MainActivity).tampilTambahTemanFragment()
        }
        listTeman = ArrayList()
        initLocalDB()
        //simulasiDataTeman()
        tampilTeman()
    }

    private fun initLocalDB() {
        db = AppDatabase.getAppDataBase(activity!!)
        myFriendDao = db?.MyFriendDao()
    }

    private fun tampilTeman() {
        listMyFriend.layoutManager = LinearLayoutManager(activity)
        listMyFriend.adapter = MyFriendAdapter(activity!!, listTeman!!) {
            val friend = it
        }
    }

    private fun ambilDataTeman() {
        listTeman = ArrayList()
        myFriendDao?.ambilSemuaTeman()?.observe(this, androidx.lifecycle.Observer { r ->
            listTeman = r.toMutableList()
            when {
                listTeman.size == 0 -> tampilToast("Belum ada data teman")

                else -> {
                    tampilTeman()
                }

            }
        })
    }
    private fun tampilToast(message: String) {
        Toast.makeText(activity!!, message, Toast.LENGTH_SHORT).show()
    }

    private fun simulasiDataTeman() {
        listTeman = ArrayList()

        listTeman.add(
            FriendList(
                nama = "Joko",
                kelamin = "Laki-Laki", email = "jokoanwar07@gmail.com",
                telp = "087767549980", alamat = "Jogja"
            )
        )
        listTeman.add(
            FriendList(
                nama = "Wawan",
                kelamin = "Laki-Laki", email = "wawanjaya66@gmail.com",
                telp = "082114765467", alamat = "Sleman"
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }
}