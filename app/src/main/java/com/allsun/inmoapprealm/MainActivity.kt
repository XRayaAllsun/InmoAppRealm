package com.allsun.inmoapprealm


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.allsun.inmoapprealm.model.UserModel
import io.realm.Realm
import io.realm.mongodb.*
import org.bson.Document
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.pojo.PojoCodecProvider


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Realm.init(this)
        val appID = "inmorealm-xthwd"
        val app = App(
            AppConfiguration.Builder(appID)
                .build()
        )
        val credentials = Credentials.anonymous()

        app.loginAsync(credentials) { result: App.Result<User?> ->
            if (result.isSuccess) {
                Log.v("QUICKSTART", "Successfully authenticated anonymously.")
                val user = app.currentUser()
                // interact with realm using your user object here
                val mongodbClient =
                    user!!.getMongoClient("mongodb-atlas")
                val mongodbDatabase =
                    mongodbClient.getDatabase("inmovilizadoTest")
// registry to handle POJOs (Plain Old Java Objects)
                val pojoCodecRegistry = CodecRegistries.fromRegistries(
                    AppConfiguration.DEFAULT_BSON_CODEC_REGISTRY,
                    CodecRegistries.fromProviders(
                        PojoCodecProvider.builder().automatic(true).build()))
                val mongodbCollection =
                    mongodbDatabase.getCollection(
                        "users",
                        UserModel::class.java).withCodecRegistry(pojoCodecRegistry)
                Log.v("EXAMPLE", "Successfully instantiated the MongoDB collection handle")
                val queryFilter = Document("name","ADMIN")
                mongodbCollection.findOne(queryFilter)
                    .getAsync { task ->
                        if (task.isSuccess) {
                            val result = task.get()
                            Log.v("EXAMPLE", "successfully found a document: $result")
                        } else {
                            Log.e("EXAMPLE", "failed to find document with: ${task.error}")
                        }
                    }

            } else {
                Log.e("QUICKSTART", "Failed to log in. Error: " + result.error)
            }
        }
    }
}