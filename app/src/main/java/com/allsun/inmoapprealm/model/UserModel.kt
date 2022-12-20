package com.allsun.inmoapprealm.model

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId;

open class UserModel(
    @PrimaryKey
    var _id: ObjectId? = ObjectId(),

    var center: String? = null,

    var email: String? = null,

    var enabled: Boolean? = null,

    var isDeleted: Boolean? = null,

    var name: String? = null,

    var password: String? = null,

    var role: String? = null

): RealmObject() {
    override fun toString(): String {
        return "UserModel(_id=$_id, center=$center, email=$email, enabled=$enabled, isDeleted=$isDeleted, name=$name, password=$password, role=$role)"
    }
}