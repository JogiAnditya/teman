package com.example.contact

import android.app.AlertDialog
import android.content.DialogInterface
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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
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
    lateinit var adapter: MyFriendAdapter

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
        //initAdapter()
        //simulasiDataTeman()
        //tampilTeman()
        ambilDataTeman()

    }

    private fun initLocalDB() {
        db = AppDatabase.getAppDataBase(activity!!)
        myFriendDao = db?.MyFriendDao()
    }

    private fun tampilTeman() {
        rvMyFriends.layoutManager = LinearLayoutManager(activity)
        rvMyFriends.adapter = MyFriendAdapter(activity!!, listTeman!!){
            
        }
    }
    private fun initAdapter() {
        adapter = MyFriendAdapter(activity!!, listTeman){}


        rvMyFriends.layoutManager = LinearLayoutManager(activity)
        rvMyFriends.adapter = adapter

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
    private fun confrimDialog(friend: FriendList, position: Int) {
        AlertDialog.Builder(activity!!)
            .setTitle("Delete ${friend.nama}")
            .setMessage("Do you really want to delete ${friend.nama} ?")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton(android.R.string.yes,
                DialogInterface.OnClickListener { dialog, whichButton ->
                    deleteFriend(friend)
                    adapter.notifyItemRemoved(position)
                }).show()
    }

    private fun deleteFriend(friend: FriendList): Job {
        return GlobalScope.launch {
            myFriendDao?.deleteFriend(friend)
        }
    }
}