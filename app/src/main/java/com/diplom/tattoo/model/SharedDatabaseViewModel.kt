package com.diplom.tattoo.model

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diplom.tattoo.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.concurrent.locks.ReentrantLock


class SharedDatabaseViewModel : ViewModel() {

    private var mDatabase = FirebaseDatabase.getInstance()
    private var mDatabaseReference = mDatabase.reference.child("users")
    private var mAuth = FirebaseAuth.getInstance()
    private val cUserDB = mDatabaseReference.child(mAuth.currentUser!!.uid)
    private var storage: FirebaseStorage = FirebaseStorage.getInstance()
    private var storageRef: StorageReference = storage.reference

    private val PATH_USER_AVATAR = storageRef.child("avatars").child(mAuth.currentUser!!.uid)

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    init {
        updateProfileUi()
    }

    private fun updateProfileUi() {
        val cUserDB = mDatabaseReference.child(mAuth.currentUser!!.uid)
        cUserDB.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                _user.apply {
                    postValue(
                        User(
                            snapshot.child("firstName").value.toString(),
                            snapshot.child("lastName").value.toString(),
                            mAuth.currentUser!!.email.toString(),
                            snapshot.child("photoUrl").value.toString()
                        )
                    )
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

//    private fun uploadImageDB(uri: Uri?) {
//        if (uri != null) {
//            PATH_USER_AVATAR.putFile(uri).addOnCompleteListener { it ->
//                if (it.isSuccessful) {
//                    PATH_USER_AVATAR.downloadUrl.addOnCompleteListener {
//                        if (it.isSuccessful) {
//                            cUserDB.updateChildren(
//                                mapOf(name to it.result.toString())
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }

//    fun uploadUserAvatar(uri: Uri?) {
//        uploadImageDB(
//            uri,
//            storageRef.child("avatars").child(mAuth.currentUser!!.uid),
//            "photoUrl"
//        )
//    }

    fun updateUserDB(data: User, uri: Uri?) {
        if (uri != null) {
            PATH_USER_AVATAR.putFile(uri).addOnCompleteListener { it ->
                if (it.isSuccessful) {
                    PATH_USER_AVATAR.downloadUrl.addOnCompleteListener {
                        if (it.isSuccessful) {
                            cUserDB.updateChildren(
                                mapOf("photoUrl" to it.result.toString())
                            )
                        }
                    }
                }
            }
        }
        cUserDB.updateChildren(mapOf("firstName" to data.firstName))
        cUserDB.updateChildren(mapOf("lastName" to data.lastName))
    }
}