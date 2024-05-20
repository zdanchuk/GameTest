package org.example

sealed class FightResult() {
    class Win (val monsterName : String, val isLevelUp : Boolean?) : FightResult()
    class LoseAndDeath(val monsterName : String) : FightResult()
    class KilledByGod(val godName : String = "Zeus") : FightResult()
    class Healed(val healOverHp :Int = 0) : FightResult()
}