package org.example

import org.example.FightResult.*

data class Person(
    val name: String,
    val personClass: Classes,
    var age: Int = 16,
    var level: Int = 1,
    var hp: Int = 200

) {
    var attack: Int = personClass.attack
    val defence: Int = personClass.defence


    companion object {
        fun dailyRoutine(
            adventurer: Person,
            isHolidays: Boolean = true,
            attackedByMonster: Monster? = null
        ): FightResult {
            if (isHolidays) {
                adventurer.hp += 50
                return Healed()
            }
            attackedByMonster?.let {
                if (it == Monster.GOD) {
                    adventurer.hp = 0
                    return KilledByGod()
                }

                var monsterHp = it.hp
                var roundLeft = 10
                while (adventurer.hp > 0 && monsterHp > 0 && roundLeft > 0) {
                    adventurer.hp -= if (it.attack > adventurer.defence) (it.attack - adventurer.defence) else 0
                    monsterHp -= if (adventurer.attack > it.defence) (adventurer.attack - it.defence) else 0
                    roundLeft--
                }
                if (adventurer.hp <= 0) {
                    return LoseAndDeath(monsterName = it.monsterName)
                }
                adventurer.age++
                val isLevelUp : Boolean? = if (adventurer.age % 16 == 0) {
                    adventurer.level++
                    adventurer.attack++
                    adventurer.hp += 30
                    true
                }  else null

                return Win(monsterName = it.monsterName, isLevelUp)

            }

            return KilledByGod()
        }
    }
}
