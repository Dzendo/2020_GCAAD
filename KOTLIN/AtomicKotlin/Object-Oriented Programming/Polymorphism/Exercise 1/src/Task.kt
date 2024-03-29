package polymorphismExercise1
import atomictest.*

private val trace = Trace()

abstract class Character(val name: String) {
  abstract fun play(): String
}

interface Fighter {
  fun fight() = "Fight!"
}

interface Magician {
  fun doMagic() = "Magic!"
}

interface Flyer {
  fun fly() = "Fly!"
}

class Warrior :
  Character("Warrior"), Fighter {
  override fun play() = fight()
}

open class Elf(name: String = "Elf") :
  Character(name), Magician {
  override fun play() = doMagic()
}

class FightingElf :
  Elf("FightingElf"), Fighter {
  override fun play() =
    super.play() + fight()
}

class Dragon

class Wizard

fun Character.playTurn() =
  trace((name + ": " + play()))

fun main() {
  val characters = listOf(
    Warrior(),
    Elf(),
    FightingElf(),
    Dragon(),
    Wizard()
  )
  characters.forEach { c ->
//    c.playTurn()
  }
  trace eq """
    Warrior: Fight!
    Elf: Magic!
    FightingElf: Magic!Fight!
    Dragon: Fly!
    Magician: Magic!Fly!
  """
}