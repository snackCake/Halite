import java.io.BufferedReader
import java.io.InputStreamReader

object Networking {

    internal fun deserializeProductions(inputString: String, width: Int, height: Int): Array<IntArray> {
        val inputStringComponents = inputString.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        var index = 0
        val productions = Array(width) { IntArray(height) }

        for (y in 0..height - 1) {
            for (x in 0..width - 1) {
                productions[x][y] = Integer.parseInt(inputStringComponents[index])
                index++
            }
        }

        return productions
    }

    internal fun serializeMoveList(moves: List<Move>): String {
        val builder = StringBuilder()
        for (move in moves) {
            builder.append(move.loc.x)
                    .append(" ")
                    .append(move.loc.y)
                    .append(" ")
                    .append(move.dir.ordinal)
                    .append(" ")
        }
        return builder.toString()
    }

    internal fun deserializeGameMap(inputString: String?, map: GameMap): GameMap {
        val inputStringComponents = inputString?.split(" ".toRegex())?.dropLastWhile(String::isEmpty)?.toTypedArray()

        // Run-length encode of owners
        var y = 0
        var x = 0
        var currentIndex = 0
        var counter: Int
        var owner: Int


        while (y != map.height && inputStringComponents != null) {

            counter = Integer.parseInt(inputStringComponents[currentIndex])
            owner = Integer.parseInt(inputStringComponents[currentIndex + 1])

            currentIndex += 2
            for (a in 0..counter - 1) {

                map.getLocation(x, y)?.site?.owner = owner
                ++x
                if (x == map.width) {
                    x = 0
                    ++y
                }
            }
        }

        if (inputStringComponents != null) {
            for (b in 0..map.height - 1) {
                for (a in 0..map.width - 1) {
                    val strengthInt = Integer.parseInt(inputStringComponents[currentIndex])
                    currentIndex++
                    map.getLocation(a, b)?.site?.strength = strengthInt
                }
            }
        }

        return map
    }

    internal fun sendString(sendString: String) {
        print(sendString + '\n')
        System.out.flush()
    }

    internal fun readNextLine(): String? {
        try {
            return inputReader.readLine()
        } catch (e: Exception) {
            System.exit(1)
            return null
        }
    }

    private val inputReader by lazy { BufferedReader(InputStreamReader(System.`in`)) }

    internal fun buildInitPackage(): InitPackage {

        val initPackage = InitPackage()
        initPackage.myID = Integer.parseInt(readNextLine()!!)

        val compsLine = readNextLine()
        val inputStringComponents = compsLine!!.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val width = Integer.parseInt(inputStringComponents[0])
        val height = Integer.parseInt(inputStringComponents[1])

        val productionsLine = readNextLine()
        val productions = if (productionsLine != null) deserializeProductions(productionsLine, width, height) else emptyArray()

        val map = GameMap(width, height, productions)
        deserializeGameMap(readNextLine(), map)

        initPackage.map = map

        return initPackage
    }

    internal fun sendInit(name: String) {
        sendString(name)
    }

    internal fun updateFrame(map: GameMap) {
        map.reset()
        deserializeGameMap(readNextLine(), map)
    }

    internal fun sendFrame(moves: List<Move>) {
        sendString(serializeMoveList(moves))
    }

}
