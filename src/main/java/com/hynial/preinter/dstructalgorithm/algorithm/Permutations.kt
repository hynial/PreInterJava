package com.hynial.preinter.dstructalgorithm.algorithm

import java.util.*
import java.util.Collections.emptyList

class Permutations {
    fun <T> List<T>.permutations(): List<List<T>> {
        //escape case
        if (this.isEmpty()) return emptyList()

        if (this.size == 1) return listOf(this)

        if (this.size == 2) return listOf(listOf(this.first(), this.last()), listOf(this.last(), this.first()))

        //recursive case
        return this.flatMap { lastItem ->
            this.minus(lastItem).permutations().map { it.plus(lastItem) }
        }
    }

    fun permute(nums: IntArray): List<List<Int>> {
        val queue = LinkedList<List<Int>>()
        queue.add(emptyList<Int>())
        while (true) {
            val size = queue.size
            for (num in 1..size) {
                val temp = queue.removeFirst()
                for (int in nums) {
                    if (!temp.contains(int)) {
                        queue.addLast(temp + int)
                    }
                }
            }
            if (queue.peek().size == nums.size) {
                break
            }
        }
        return queue.toList()
    }

}