import java.util.*

fun main() {
    runDay("09", 1928) { input ->
        val decoded = decode(input[0].map { it.digitToInt() })
        val compacted = compact(decoded)
        return@runDay compacted.filterNotNull()
            .mapIndexed { index, id ->
                index.toLong() * id.toLong()
            }.sum()
    }

    runDay("09", 2858) { input ->
        val disk = Disk.of(input[0].map { it.digitToInt() })
        disk.compact()
        disk.checkSum()
    }
}

// PART 1
private fun decode(arr: List<Int>): List<Int?> {
    var currentId = 0
    var block = true

    val res = mutableListOf<Int?>()
    for (elem in arr) {
        if (block) {
            for (i in 0..<elem) {
                res.add(currentId)
            }
            currentId++
        } else {
            for (i in 0..<elem) {
                res.add(null)
            }
        }
        block = !block
    }
    return res
}

private fun compact(arr: List<Int?>): List<Int?> {
    val l = arr.toMutableList()
    var i = 0
    var j = arr.size - 1
    while (i < j) {
        while (l[i] != null) i++
        while (l[j] == null) j--
        if (i >= j) break
        val tmp = l[i]
        l[i] = l[j]
        l[j] = tmp
    }
    return l
}

// PART2
private data class Block(val id: Int, val range: IntRange)

private data class Disk(
    val memory: NavigableSet<Block>
) {
    companion object {
        fun of(arr: List<Int>): Disk {
            var currentId = 0
            var block = true

            val res = TreeSet<Block>(compareBy { it.range.first })
            var i = 0
            for (elem in arr) {
                i += if (block) {
                    res.add(Block(currentId, i..i + elem))
                    currentId++
                    elem
                } else {
                    elem
                }
                block = !block
            }
            return Disk(res)
        }
    }

    fun checkSum(): Long {
        val s = mutableListOf<Int?>()
        for (b in memory) {
            val blockSize = b.range.last - b.range.first
            for (i in 0..<blockSize) {
                s.add(b.id)
            }
            val next = memory.higher(b)
            if (next != null) {
                val emptyBlockSize = next.range.first - b.range.last
                for (i in 0..<emptyBlockSize) {
                    s.add(null)
                }
            }
        }
        return s.mapIndexedNotNull { index, id ->
            if (id != null) index.toLong() * id.toLong() else null
        }.sum()
    }

    fun compact() {
        for (b in memory.reversed()) {
            tryToFit(b)
        }
    }

    fun tryToFit(b: Block) {
        var i = 0
        var elem = memory.first()
        while (elem != b) {
            val nextElem = memory.higher(elem)!!
            val emptyBlockSize = nextElem.range.first - elem.range.last
            val blockSize = b.range.last - b.range.first
            if (emptyBlockSize >= blockSize) {
                memory.remove(b)
                memory.add(Block(b.id, elem.range.last..elem.range.last + blockSize))
                return
            }
            i += blockSize
            elem = nextElem
        }
    }
}
