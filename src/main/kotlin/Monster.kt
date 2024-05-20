package org.example

enum class Monster(val monsterName: String, val hp: Int, val attack: Int, val defence: Int) {

    GOBLIN("Goblin", 10, 10, 3),
    ORC("Orc", 20, 15, 7),
    TROLL("Troll", 30, 20, 11),


    GOD("God", Int.MAX_VALUE, Int.MAX_VALUE, Int.MAX_VALUE),
    NULL("Holiday", 1, -10, 0);

    companion object {
        fun random(): Monster {
            return entries.random()

        }
    }
}
