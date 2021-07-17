import kotlin.String
import kotlin.Unit

public class Greeter(
  public val name: String
) {
  public fun greet(): Unit {
    println("""Hello, $name""")
  }
}
