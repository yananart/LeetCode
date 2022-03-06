/**
 * @author Yananart
 */
class Solution {
    fun nearestPalindromic(n: String): String {
        if (n.length == 1) {
            // 个位数 取比自己小的一个
            return (n.toInt() - 1).toString()
        }

        // 原来的数 可用long表示
        val o = n.toLong()

        if (strCharSum(n) == 1) {
            // 类似 10 100 1000 这种直接返回 9 99 999
            return (o - 1).toString()
        }
        if (n == n.reversed()) {
            // 下面两种处理以后 后面就不用管了 否则会很复杂
            if (strCharSum(n) == 2) {
                // 类似 11 101 1001 这种直接返回 9 99 999
                return (o - 2).toString()
            }
            if (n.toCharArray().all { it == '9' }) {
                // 类似 99 999 9999 这种直接返回 101 1001 10001
                return (o + 2).toString()
            }
        }

        // big small 代表两个最近的回文数
        val big: Long
        val small: Long

        if (n.length % 2 == 1) {
            // 这种是有中位数的
            val midIndex = n.length / 2
            val left = n.substring(0, midIndex)
            val mid = n[midIndex].toString().toInt()
            val right = n.substring(midIndex + 1)
            val leftReversed = left.reversed()

            if (leftReversed.toInt() > right.toInt()) {
                big = (left + mid + left.reversed()).toLong()
                // 转换后回文右侧比原来的大或相等 找一个更小的回文再比一次
                small = if (mid == 0) {
                    val leftLess = (left.toInt() - 1).toString()
                    (leftLess + '9' + leftLess.reversed()).toLong()
                } else {
                    (left + (mid - 1) + leftReversed).toLong()
                }
            } else if (leftReversed.toInt() < right.toInt()) {
                small = (left + mid + left.reversed()).toLong()
                // 转换后回文右侧比原来的小 找一个更大的回文再比一次
                big = if (mid == 9) {
                    val leftAdd = (left.toInt() + 1).toString()
                    (leftAdd + '0' + leftAdd.reversed()).toLong()
                } else {
                    (left + (mid + 1) + leftReversed).toLong()
                }
            } else {
                // 已经回文了 找上下两个
                small = if (mid == 0) {
                    val leftLess = (left.toInt() - 1).toString()
                    (leftLess + '9' + leftLess.reversed()).toLong()
                } else {
                    (left + (mid - 1) + leftReversed).toLong()
                }
                big = if (mid == 9) {
                    val leftAdd = (left.toInt() + 1).toString()
                    (leftAdd + '0' + leftAdd.reversed()).toLong()
                } else {
                    (left + (mid + 1) + leftReversed).toLong()
                }
            }
        } else {
            // 没有中位数
            val midIndex = n.length / 2
            val left = n.substring(0, midIndex)
            val right = n.substring(midIndex)
            val leftReversed = left.reversed()

            if (leftReversed.toInt() > right.toInt()) {
                // 转换后回文右侧比原来的大或相等 找一个更小的回文再比一次
                big = (left + left.reversed()).toLong()
                val leftLess = (left.toInt() - 1).toString()
                small = (leftLess + leftLess.reversed()).toLong()
            } else if (leftReversed.toInt() < right.toInt()) {
                // 转换后回文右侧比原来的小 找一个更大的回文再比一次
                small = (left + left.reversed()).toLong()
                val leftAdd = (left.toInt() + 1).toString()
                big = (leftAdd + leftAdd.reversed()).toLong()
            } else {
                // 已经回文了 找上下两个
                val leftLess = (left.toInt() - 1).toString()
                small = (leftLess + leftLess.reversed()).toLong()
                val leftAdd = (left.toInt() + 1).toString()
                big = (leftAdd + leftAdd.reversed()).toLong()
            }
        }

        return if (big - o < o - small) {
            big.toString()
        } else {
            small.toString()
        }
    }

    private fun strCharSum(str: String): Int {
        // leetcode没有这个方法就很难受
        // return str.toCharArray().sumOf { it.toString().toInt() }
        var sum = 0
        str.toCharArray().forEach { sum += it.toString().toInt() }
        return sum
    }
}