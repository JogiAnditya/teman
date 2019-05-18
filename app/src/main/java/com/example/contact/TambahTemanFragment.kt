package com.example.contact

import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.contact.data.AppDatabase
import com.example.contact.data.MyFriendDao
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.addfriend.*
import kotlinx.android.synthetic.main.friendlist.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TambahTemanFragment: Fragment () {

    private var namaInput : String = ""
    private var emailInput : String = ""
    private var telpInput : String = ""
    private  var alamatInput : String = ""
    private var genderInput : String = ""
    //private val imageByte: ByteArray? = null


    private var db: AppDatabase? = null
    private var myFriendDao: MyFriendDao? = null

    companion object {
        fun newInstance(): TambahTemanFragment {
            return TambahTemanFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.addfriend, container, false)
    }
    private fun initLocalDB() {
        db = AppDatabase.getAppDataBase(activity!!)
        myFriendDao = db?.MyFriendDao()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSave.setOnClickListener{
            (activity as MainActivity).tampilMyFriendFragment()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }

    private fun validasiInput() {
        namaInput = edtName.text.toString()
        emailInput = edtEmail.text.toString()
        telpInput = edtTelp.text.toString()
        alamatInput = edtAddress.text.toString()
        genderInput = spinnerGender.selectedItem.toString()

        when{
            //imageByte == null -> tampilToast("Ambil foto terlebih dahulu")
            namaInput.isEmpty() -> edtName.error = "Nama tidak boleh kosong"
            genderInput.equals("Pilih Kelamin") -> tampilToast("Kelamin harus dipilih")
            emailInput.isEmpty() -> edtEmail.error = ("Email tidak boleh kosong")
            telpInput.isEmpty() -> edtTelp.error = "Telepon tidak boleh kosong"
            alamatInput.isEmpty() -> edtAddress.error = "Alamat tidak boleh kosong"

            else -> {
                val friend = FriendList(namaInput, genderInput, emailInput, telpInput, alamatInput)
                tambahDataTeman(friend)
            }
        }
    }

    private fun tambahDataTeman(teman: FriendList) : Job {

        return GlobalScope.launch {
            myFriendDao?.tambahTeman(teman)
            (activity as MainActivity).tampilMyFriendFragment()
        }
    }

    private fun tampilToast(message: String) {
        Toast.makeText(activity!!, message, Toast.LENGTH_SHORT).show()
    }
}