package com.diplom.tattoo.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diplom.tattoo.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SharedDatabaseViewModel : ViewModel() {

    private var mDatabase = FirebaseDatabase.getInstance()
    private var mDatabaseReference = mDatabase.reference.child("users")
    private var mAuth = FirebaseAuth.getInstance()
    private val cUserDB = mDatabaseReference.child(mAuth.currentUser!!.uid)

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    init {
        updateProfileUi()
    }

    private fun updateProfileUi() {
        val cUserDB = mDatabaseReference!!.child(mAuth.currentUser!!.uid)
        cUserDB.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                _user.apply {
                    postValue(
                        User(
                            snapshot.child("firstName").value as String,
                            snapshot.child("lastName").value as String,
                            mAuth.currentUser!!.email as String
                        )
                    )
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    fun updateDataDB(data: User) {
        cUserDB.updateChildren(mapOf("firstName" to data.firstName))
        cUserDB.updateChildren(mapOf("lastName" to data.lastName))
        updateProfileUi()
    }
}