package code.stats.database.entity

import io.realm.kotlin.ext.realmDictionaryOf
import io.realm.kotlin.types.RealmDictionary
import io.realm.kotlin.types.RealmMap
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class UserStatDbo : RealmObject {
    @PrimaryKey
    var user: String = ""
    var totalXp: Int = 0
    var newXp: Int = 0
    var machines: RealmDictionary<StatItemDbo?> = realmDictionaryOf()
    var languages: RealmDictionary<StatItemDbo?> = realmDictionaryOf()
    var dates: RealmDictionary<Int> = realmDictionaryOf()
    var lastChange: String = ""
}

class StatItemDbo : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var newXps: Int? = null
    var xps: Int? = null
}