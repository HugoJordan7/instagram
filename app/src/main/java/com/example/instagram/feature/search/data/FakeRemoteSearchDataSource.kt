package com.example.instagram.feature.search.data

import android.os.Handler
import android.os.Looper
import com.example.instagram.common.base.RequestCallback
import com.example.instagram.common.model.Database
import com.example.instagram.common.model.UserAuth
import java.util.Locale

class FakeRemoteSearchDataSource: SearchDataSource {

    override fun fetchUsers(name: String, callback: RequestCallback<List<UserAuth>>) {
        Handler(Looper.getMainLooper()).postDelayed({
             val users = Database.usersAuth.filter {
                 it.name.lowercase(Locale.getDefault()).startsWith(name.lowercase(Locale.getDefault())) && it.uuid != Database.sessionAuth!!.uuid
             }
            callback.onSuccess(users)
            callback.onComplete()
        }, 2000)
    }

}