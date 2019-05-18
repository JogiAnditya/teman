package com.example.contact

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FriendList (
    val nama: String,
    val kelamin: String,
    val email: String,
    val telp: String,
    val alamat: String,
    @PrimaryKey(autoGenerate = true)
    val temanID: Int? = null
) {
    infix fun tambahTeman(teman: Any) {

    }
}