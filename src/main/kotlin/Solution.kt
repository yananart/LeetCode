/**
 * @author Yananart
 */
class Solution {
    fun isHappy(n: Int): Boolean {
        var now = n
        val use = HashSet<Int>()
        while (now != 1) {
            var t = 0
            while (now != 0) {
                val last = now % 10
                if (last != 0) {
                    t += last * last
                }
                now /= 10
            }
            if (!use.add(t)) return false
            now = t
        }
        return true
    }
}