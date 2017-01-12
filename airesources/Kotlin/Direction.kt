import java.util.Random

enum class Direction {
    STILL, NORTH, EAST, SOUTH, WEST;


    companion object {

        val DIRECTIONS = arrayOf(STILL, NORTH, EAST, SOUTH, WEST)
        val CARDINALS = arrayOf(NORTH, EAST, SOUTH, WEST)

        fun randomDirection(): Direction {
            val values = values()
            return values[Random().nextInt(values.size)]
        }
    }
}
