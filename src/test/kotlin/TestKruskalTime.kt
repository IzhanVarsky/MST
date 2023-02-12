import kotlin.system.measureTimeMillis

fun generateGraph(n: Int = 200_000, m: Int = 200_000, maxW: Int = 100_000): Graph<Int> {
    assert(n <= m + 1)

    val graph = Graph<Int>(n)
    for (i in 2..n) {
        graph.edges += Graph.Edge(
            (1 until i).random() - 1,
            i - 1,
            (0..maxW).random()
        )
    }
    for (i in n..m) {
        graph.edges += Graph.Edge(
            (1..n).random() - 1,
            (1..n).random() - 1,
            (0..maxW).random()
        )
    }
    return graph
}

fun testTimeOfKruskalAlgo(test_count: Int) {
    val maxN = 200_000
    val maxM = 200_000
    val times = mutableListOf<Long>()
    repeat(test_count) {
        val n = (1..maxN).random()
        val m = (n..maxM).random()
        println("N: $n, M: $m")
        val graph = generateGraph(n, m)
        val time = measureTimeMillis {
            KruskalMST(graph).solve()
        }
        println("Test #${it + 1}. Time: ${time}ms.")
        times += time
    }
    println("===========================")
    println("Mean time is: ${times.average()}ms.")
}

fun main() {
    testTimeOfKruskalAlgo(50)
}
