fun main(args: Array<String>) {
    val solution = Solution()
    while (true) {
        val line = readLine()!!
        println(solution.nearestPalindromic(line))
    }
}