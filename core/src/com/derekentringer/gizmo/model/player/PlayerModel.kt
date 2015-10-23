package com.derekentringer.gizmo.model.player

import com.derekentringer.gizmo.model.BaseModel
import com.derekentringer.gizmo.model.BaseModelType
import com.derekentringer.gizmo.model.item.BaseItemModel
import com.derekentringer.gizmo.model.`object`.KeyModel

import java.util.ArrayList

class PlayerModel : BaseModel() {

    val keys = ArrayList<KeyModel>()
    val items = ArrayList<BaseItemModel>()

    companion object {
        val PLAYER = "PLAYER"
        val PLAYER_DESTINATIONS = "PLAYER_DESTINATIONS"
        val HEART_HEALTH_AMOUNT = 10
        val DEFAULT_LEVEL = 0
        val DEFAULT_HEARTS = 2
        val DEFAULT_HEALTH = 20
        val DEFAULT_LIVES = 2
        val DEFAULT_MAX_LIVES = 5
        val DEFAULT_MAX_HEARTS = 5
        val DEFAULT_DIGGING_POWER = 1
    }

    init {
        mBaseModelType = BaseModelType.PLAYER
    }

    var currentLevel: Int = 0
    var lives: Int = 0
    var hearts: Int = 0

    var health: Int = 0
        set(health) {
            field = health
            if (this.health > (hearts * HEART_HEALTH_AMOUNT)) {
                this.health = hearts * HEART_HEALTH_AMOUNT
            }
        }

    //TODO
    var diggingPower = 1
    var crystalBlueAmount: Int = 0

    fun addKey(keyData: KeyModel) {
        keys.add(keyData)
    }

    fun removeKey(keyType: String) {
        for (i in 0..keys.size - 1) {
            if (keys[i].keyType.equals(keyType)) {
                keys.removeAt(i)
            }
        }
    }

    fun addItem(playerItem: BaseItemModel) {
        items.add(playerItem)
    }

    fun removeItem(playerItem: BaseItemModel) {
        items.remove(playerItem)
    }

    fun incrementCrystalBlueAmount() {
        crystalBlueAmount += 1
    }

}