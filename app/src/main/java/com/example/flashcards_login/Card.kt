package com.example.flashcards_login

import java.util.*

class Card(
  val front: String = "",
  val back: String = "",
  val created: String = Calendar.getInstance().toString(),
  val timeStart: String = Calendar.getInstance().toString(),
  val timeEnd: String = Calendar.getInstance().toString()
) {

  fun updateTime(inc: Int, op: String) {
    TODO("Not specified")
//    when (op) {
//      "Hours" -> print("x == 2")
//      "Days" -> print("x == 1")
//      else -> { // Note the block
//        throw Exception("Should never happen")
//      }
//    }
  }

}

/*
/**
 * You can edit, run, and share this code.
 * play.kotlinlang.org
 */

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

fun main(args: Array<String>) {

    val c1 = Calendar.getInstance()
    val c2 = Calendar.getInstance()
    val cTotal = c1.clone() as Calendar

    val current = LocalDateTime.now()

    val formatter = DateTimeFormatter.ISO_LOCAL_TIME
    val formatted = current.format(formatter)

    println("Current Date is: $formatted")

//     cTotal.add(Calendar.YEAR, c2.get(Calendar.YEAR))
//     cTotal.add(Calendar.MONTH, c2.get(Calendar.MONTH) + 1) // Zero-based months
//     cTotal.add(Calendar.DATE, c2.get(Calendar.DATE))
//     cTotal.add(Calendar.HOUR_OF_DAY, c2.get(Calendar.HOUR_OF_DAY))
//     cTotal.add(Calendar.MINUTE, c2.get(Calendar.MINUTE))
//     cTotal.add(Calendar.SECOND, c2.get(Calendar.SECOND))
//     cTotal.add(Calendar.MILLISECOND, c2.get(Calendar.MILLISECOND))

//     println("${c1.time} + ${c2.time} = ${cTotal.time}")
//

// 	val res: Int = c2.compareTo(cTotal)
// 	println("${c1.time}")
//     println(res)

    val start = Calendar.getInstance()
//     val end = Calendar.getInstance()
    val end = c1.clone() as Calendar

    end.add(Calendar.MINUTE, 2)

    println("Start time: ${start.time} \nEnd Time: ${end.time}")
}
 */