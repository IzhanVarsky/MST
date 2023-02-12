import java.util.*

fun main() {
//    val graph = readGraphUsingScanner()
    val graph = readGraphUsingReadLine()
    val res = KruskalMST(graph).solve()
    println(res)
}

fun readGraphUsingScanner(): Graph<Int> {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()
    val m = sc.nextInt()
    val graph = Graph<Int>(n)
    for (i in 0 until m) {
        val a = sc.nextInt() - 1
        val b = sc.nextInt() - 1
        val w = sc.nextInt()
        graph.edges += Graph.Edge(a, b, w)
    }
    return graph
}

fun readLineOfInts() = readLine()!!.split(" ").map(String::toInt)

fun readGraphUsingReadLine(): Graph<Int> {
    val (n, m) = readLineOfInts()
    val graph = Graph<Int>(n)
    for (i in 0 until m) {
        val (a, b, w) = readLineOfInts()
        graph.edges += Graph.Edge(a - 1, b - 1, w)
    }
    return graph
}

class DSU(private val parent: IntArray, private val rank: IntArray) {
    constructor(n: Int) : this(IntArray(n) { it }, IntArray(n))

    fun findSet(v: Int): Int = findSetWithPathCompression(v)

    private fun naiveFindSet(v: Int) = if (v == parent[v]) v else findSet(parent[v])

    private fun findSetWithPathCompression(v: Int) =
        if (v == parent[v]) v else findSet(parent[v]).also { parent[v] = it }

    fun unionSets(x: Int, y: Int) = unionSetsWithRanking(x, y)

    private fun naiveUnionSets(x: Int, y: Int) {
        val a = findSet(x)
        val b = findSet(y)
        if (a != b) {
            parent[b] = a
        }
    }

    private fun unionSetsWithRanking(x: Int, y: Int) {
        var a = findSet(x)
        var b = findSet(y)
        if (a != b) {
            if (rank[a] < rank[b]) {
                val t = a
                a = b
                b = t
            }
            parent[b] = a
            if (rank[a] == rank[b]) ++rank[a]
        }
    }
}

class Graph<T>(val vertex_count: Int) {
    val edges = mutableListOf<Edge<T>>()

    data class Edge<T>(val a: Int, val b: Int, val w: T)
}

class KruskalMST(private val graph: Graph<Int>) {
    fun solve(): Long {
        graph.edges.sortBy { it.w }
        val dsu = DSU(graph.vertex_count)
//        return graph.edges.sumOf { (a, b, w) ->
//            if (dsu.findSet(a) != dsu.findSet(b)) {
//                dsu.unionSets(a, b)
//                w.toLong()
//            } else 0L
//        }
        var ans: Long = 0
        for ((a, b, w) in graph.edges) {
            if (dsu.findSet(a) != dsu.findSet(b)) {
                ans += w
                dsu.unionSets(a, b)
            }
        }
        return ans
    }
}