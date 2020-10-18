package com.padc.grocery.network.remoteConfig

import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

object FirebaseRemoteConfigManager {

    private val remoteConfig = Firebase.remoteConfig

    init {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
    }

    fun setUpRemoteConfigWithDefaultValue(){
        val defaultValues : Map<String,Any> = hashMapOf(
            "mainScreenAppBarTitle" to "Grocery-App",
            "viewType" to 0
        )
        remoteConfig.setDefaultsAsync(defaultValues)
    }


    fun fetchRemoteConfigs(){
        remoteConfig.fetch()
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Log.d("Firebase","Firebase remote config fetch success")

                    remoteConfig.activate().addOnCanceledListener {
                        Log.d("Firebase","Firebase remote config activated")
                    }
                }

                else{
                    Log.d("Firebase","Firebase remote config fetch failed")
                }
            }
    }


    fun getToolbarName() : String{
        return remoteConfig.getValue("mainScreenAppBarTitle").asString()
    }

    fun getViewType() : Int{
        return remoteConfig.get("viewType").asString().toInt()
    }
}