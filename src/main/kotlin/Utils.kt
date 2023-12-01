import kotlin.io.path.Path
import kotlin.io.path.readLines

fun readInput(day: String, filename: String) = Path("data/day$day/$filename").readLines()

fun Any?.println() = println(this)
