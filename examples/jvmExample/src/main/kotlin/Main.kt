import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {
  println("Hello World!")

  // Try adding program arguments via Run/Debug configuration.
  // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
  println("Program arguments: ${args.joinToString()}")
  runBlocking {
    dev.baseio.getWorkspaces().collectLatest {
      println(it)
    }
  }
}