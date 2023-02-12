import java.util.*

//fun main() {
//    val sc = Scanner(System.`in`)
//    val n = sc.nextInt()
//    val m = sc.nextInt()
//    val graph = Graph(n)
//    for (i in 0 until m) {
//        val a = sc.nextInt() - 1
//        val b = sc.nextInt() - 1
//        val w = sc.nextInt()
//        graph.edges += Graph.Edge(a, b, w)
//    }
//    val res = KruskalMST(graph).solve()
//    println(res)
//}

fun main() {
    val (n, m) = readLine()!!.split(" ").map(String::toInt)
    val graph = Graph(n)
    for (i in 0 until m) {
        val (a, b, w) = readLine()!!.split(" ").map(String::toInt)
        graph.edges += Graph.Edge(a - 1, b - 1, w)
    }
    val res = KruskalMST(graph).solve()
    println(res)
}

class DSU(private val parent: IntArray, private val rank: IntArray) {
    constructor(n: Int) : this(IntArray(n) { it }, IntArray(n))

    fun findSet(v: Int): Int =
        if (v == parent[v]) v else findSet(parent[v]).also { parent[v] = it }

    fun unionSets(x: Int, y: Int) {
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

class Graph(val vertex_count: Int) {
    val edges = mutableListOf<Edge>()

    data class Edge(val a: Int, val b: Int, val w: Int)
}

class KruskalMST(private val graph: Graph) {
    fun solve(): Long {
        graph.edges.sortBy { it.w }
        var ans: Long = 0
        val dsu = DSU(graph.vertex_count)
        for ((a, b, w) in graph.edges) {
            if (dsu.findSet(a) != dsu.findSet(b)) {
                ans += w
                dsu.unionSets(a, b)
            }
        }
        return ans
    }
}