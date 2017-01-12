import java.util.ArrayList

object MyBot : Logger {
    @Throws(java.io.IOException::class)
    @JvmStatic fun main(args: Array<String>) {

        val iPackage = Networking.buildInitPackage()
        val myID = iPackage.myID
        val gameMap = iPackage.map

        Networking.sendInit("MyJavaBot")

        while (gameMap != null) {
            val moves = ArrayList<Move>()

            Networking.updateFrame(gameMap)

            for (y in 0..gameMap.height - 1) {
                for (x in 0..gameMap.width - 1) {
                    val location = gameMap.getLocation(x, y)
                    val site = location?.site
                    if (site != null && location != null && site.owner == myID) {
                        moves.add(Move(location, Direction.randomDirection()))
                    }
                }
            }
            Networking.sendFrame(moves)
        }
        Logger.close()
    }
}
