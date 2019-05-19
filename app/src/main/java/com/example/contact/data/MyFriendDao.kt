package com.example.contact.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.contact.FriendList

@Dao
interface MyFriendDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun tambahTeman(friend:FriendList)

    @Query(value = "SELECT * FROM FriendList")
    fun ambilSemuaTeman(): LiveData<List<FriendList>>

    @Delete
    fun deleteFriend(friend: FriendList)
}