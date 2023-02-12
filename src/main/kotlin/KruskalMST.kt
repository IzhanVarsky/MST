import java.util.*

fun main() {
    KruskalMST().solve()
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

class KruskalMST {
    private val graph = mutableListOf<Triple>()

    fun solve() {
        val sc = Scanner(System.`in`)
        val n = sc.nextInt()
        val m = sc.nextInt()
        for (i in 0 until m) {
            val a = sc.nextInt() - 1
            val b = sc.nextInt() - 1
            val c = sc.nextInt()
            graph.add(Triple(c, a, b))
        }
        graph.sortBy { it.w }
        var ans: Long = 0
        val dsu = DSU(n)
        for (triple in graph) {
            val w = triple.w
            val b = triple.a
            val c = triple.b
            if (dsu.findSet(b) != dsu.findSet(c)) {
                ans += w
                dsu.unionSets(b, c)
            }
        }
        println(ans)
    }

    class Triple(var w: Int, var a: Int, var b: Int)
}