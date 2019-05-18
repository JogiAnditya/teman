package com.example.contact.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.contact.FriendList

@Dao
interface MyFriendDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun tambahTeman(friend:FriendList)

    @Query(value = "SELECT * FROM FriendList")
    fun ambilSemuaTeman(): LiveData<List<FriendList>>
}