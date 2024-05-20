package org.example

import kotlinx.coroutines.*
import org.example.FightResult.*

const val KILLED_BY_GOD = "He/she have bad day - killed by god"

class GameEngine {

    private val firstNames: Array<String> = arrayOf("Tom", "Jerry", "Karl", "Marta", "Jennifer", "Oslo", "Kevin", "Tom1", "Jerry1", "Karl1", "Marta1", "Jennifer1", "Oslo1", "Kevin1")
    private val lastNames = arrayOf("Glorn", "Tomanson", "Majesty", "Koklin", "Troopers","Glorn1", "Tomanson1", "Majesty1", "Koklin1", "Troopers1","Glorn2", "Tomanson2", "Majesty2", "Koklin2", "Troopers2")


    private lateinit var adventurers: MutableList<Person>

    internal fun init() {
        adventurers = mutableListOf()
        for (i in 1..20) {
            val adventurer: Person =
                Person(
                    name = "${firstNames.random()} ${lastNames.random()}",
                    personClass = Classes.entries.toTypedArray().random()
                ).let {
                    it.age += (Math.random() * 1000).toInt() % 4
                    it.level += (Math.random() * 1000).toInt() % 3
                    it
                }
            with(adventurers) {
                add(element = adventurer)
                println("""
                    | ${adventurer.name} started career
                    | ->  class = ${adventurer.personClass.className} 
                    | ---->   attack = ${adventurer.attack}, defence = ${adventurer.defence}
                """.trimMargin("|"))
            }
        }
    }

    internal fun start() {
        runBlocking {
            while (adventurers.isNotEmpty()) {
                adventurers.forEach { adventurer ->
                        async {
                            play(adventurer)
                        }.await()
                }
                adventurers = adventurers.filter { it -> it.hp > 0 }.toMutableList()
            }
        }
    }

    private fun play(adventurer: Person) {
        val monster = Monster.random()
//        print("${adventurer.name} (${adventurer.hp}) vs ${monster.monsterName}     ")
        val fightResult =
            if (monster == Monster.NULL) Person.dailyRoutine(adventurer, isHolidays = true) else Person.dailyRoutine(
                adventurer,
                isHolidays = false,
                attackedByMonster = monster
            )

        when (fightResult) {
            is Healed -> println("Lucky day for ${adventurer.name}. Today is holiday and he/she is rest")
            is KilledByGod -> println("!!! GOD kill ${adventurer.name} (${adventurer.personClass.className})")
            is LoseAndDeath -> println("${adventurer.name} (age = ${adventurer.age}) didn't have luck today. He/she fights with ${fightResult.monsterName} and lose")
            is Win -> {
                println("The battle between  ${fightResult.monsterName} and ${adventurer.name} (hp = ${adventurer.hp}) ended with the victory of the second")
                fightResult.isLevelUp?.run {
                    println("${adventurer.name} today gained new level")
                }
            }

        }
//        delay((Math.random() * 100).toLong() + 400)

    }
}