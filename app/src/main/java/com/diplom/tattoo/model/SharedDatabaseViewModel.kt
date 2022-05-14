package com.diplom.tattoo.model

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diplom.tattoo.data.Master
import com.diplom.tattoo.data.Record
import com.diplom.tattoo.data.Tattoo
import com.diplom.tattoo.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class SharedDatabaseViewModel : ViewModel() {

    private var mDatabase = FirebaseDatabase.getInstance()
    private var mDatabaseReference = mDatabase.reference
    private var mAuth = FirebaseAuth.getInstance()
    private val cUserDB = mDatabaseReference.child("users").child(mAuth.currentUser!!.uid)
    private var storage: FirebaseStorage = FirebaseStorage.getInstance()
    private var storageRef: StorageReference = storage.reference

    private val PATH_USER_AVATAR = storageRef.child("avatars").child(mAuth.currentUser!!.uid)

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _master = MutableLiveData<ArrayList<Master>>()
    val master: LiveData<ArrayList<Master>> = _master

    private val _tattoo = MutableLiveData<ArrayList<Tattoo>>()
    val tattoo: LiveData<ArrayList<Tattoo>> = _tattoo

    private val _records = MutableLiveData<ArrayList<Record>>()
    val records: LiveData<ArrayList<Record>> = _records

    init {
        updateProfileUi()
        updateMasters()
        updateTattoo()
        updateMyRecords()
    }

    private fun updateProfileUi() {
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

    private fun updateMasters() {
        val masterDB = arrayListOf<Master>()
        val ref = mDatabaseReference.child("masters")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                masterDB.clear()
                for (snapshot in dataSnapshot.children) {
                    val master = snapshot.getValue(Master::class.java)
                    masterDB.add(master!!)
                }
                _master.value = masterDB
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun updateTattoo() {
        val tattooDB = arrayListOf<Tattoo>()
        val ref = mDatabaseReference.child("tattoo")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                tattooDB.clear()
                for (snapshot in dataSnapshot.children) {
                    val tattoo = snapshot.getValue(Tattoo::class.java)
                    tattooDB.add(tattoo!!)
                }
                _tattoo.value = tattooDB
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun updateMyRecords() {
        val recordsDB = arrayListOf<Record>()
        val ref = mDatabaseReference.child("records")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                recordsDB.clear()
                for (snapshot in dataSnapshot.children) {
                    val record = snapshot.getValue(Record::class.java)
                    recordsDB.add(record!!)
                }
                _records.value = recordsDB
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    fun addRecordDB(rec: Record){
        val keyRecord = mDatabaseReference.child("records").push().key
        val cRecordDB = mDatabaseReference.child("records").child(keyRecord!!)

        cRecordDB.child("UID").setValue(mAuth.currentUser!!.uid)
        cRecordDB.child("data").setValue(rec.data)
        cRecordDB.child("master").setValue(rec.master)
        cRecordDB.child("tattoo").setValue(rec.tattoo)
        cRecordDB.child("time").setValue(rec.time)
    }

    fun uploadTattoo(data: Tattoo) {
        val keyTattoo = mDatabaseReference.child("tattoo").push().key

        val cTattooDB = mDatabaseReference.child("tattoo").child(keyTattoo!!)
        cTattooDB.child("title").setValue(data.title)
        cTattooDB.child("des").setValue(data.des)
        for (i in 0 until data.photoUrl.size)
            cTattooDB.child("photoUrl").child(i.toString()).setValue(data.photoUrl[i])
        for (i in 0 until data.color.size)
            cTattooDB.child("color").child(i.toString()).setValue(data.color[i])
        for (i in 0 until data.recommended.size)
            cTattooDB.child("recommended").child(i.toString()).setValue(data.recommended[i])
    }

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