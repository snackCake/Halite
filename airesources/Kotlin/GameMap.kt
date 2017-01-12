import java.util.ArrayList

class GameMap(val width: Int, val height: Int, productions: Array<IntArray>) {

    private val contents: Array<Array<Site?>>
    private val locations: Array<Array<Location?>>

    init {
        this.contents = Array(width) { arrayOfNulls<Site>(height) }
        this.locations = Array(width) { arrayOfNulls<Location>(height) }

        for (y in 0..height - 1) {
            for (x in 0..width - 1) {
                val site = Site(productions[x][y])
                contents[x][y] = site
                locations[x][y] = Location(x, y, site)
            }
        }
    }

    fun inBounds(loc: Location): Boolean {
        return loc.x < width && loc.x >= 0 && loc.y < height && loc.y >= 0
    }

    fun getDistance(loc1: Location, loc2: Location): Double {
        var dx = Math.abs(loc1.x - loc2.x)
        var dy = Math.abs(loc1.y - loc2.y)

        if (dx > width / 2.0) dx = width - dx
        if (dy > height / 2.0) dy = height - dy

        return (dx + dy).toDouble()
    }

    fun getAngle(loc1: Location, loc2: Location): Double {
        var dx = loc1.x - loc2.x

        // Flip order because 0,0 is top left
        // and want atan2 to look as it would on the unit circle
        var dy = loc2.y - loc1.y

        if (dx > width - dx) dx -= width
        if (-dx > width + dx) dx += width

        if (dy > height - dy) dy -= height
        if (-dy > height + dy) dy += height

        return Math.atan2(dy.toDouble(), dx.toDouble())
    }

    fun getLocation(location: Location, direction: Direction): Location? {
        when (direction) {
            Direction.STILL -> return location
            Direction.NORTH -> return locations[location.x][(if (location.y == 0) height else location.y) - 1]
            Direction.EAST -> return locations[if (location.x == width - 1) 0 else location.x + 1][location.y]
            Direction.SOUTH -> return locations[location.x][if (location.y == height - 1) 0 else location.y + 1]
            Direction.WEST -> return locations[(if (location.x == 0) width else location.x) - 1][location.y]
            else -> throw IllegalArgumentException(String.format("Unknown direction %s encountered", direction))
        }
    }

    fun getSite(loc: Location, dir: Direction): Site? {
        return getLocation(loc, dir)?.site
    }

    fun getSite(loc: Location): Site {
        return loc.site
    }

    fun getLocation(x: Int, y: Int): Location? {
        return locations[x][y]
    }

    internal fun reset() {
        for (y in 0..height - 1) {
            for (x in 0..width - 1) {
                val site = contents[x][y]
                site?.owner = 0
                site?.strength = 0
            }
        }
    }
}
