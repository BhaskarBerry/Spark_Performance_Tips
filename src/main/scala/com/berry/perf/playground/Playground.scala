package com.berry.perf.playground

import org.apache.spark.sql.SparkSession

/**
  * If you can compile and run this application, it means that the libraries were downloaded correctly.
  */
object Playground {

  val spark = SparkSession.builder()
    .appName("Spark Optimization ")
    .master("local")
    .getOrCreate()

  val sc = spark.sparkContext

  def main(args: Array[String]): Unit = {
    val rdd = sc.parallelize(1 to 1000)
    println(s"I have my first RDD, it has ${rdd.count} rows. Now let me go optimize massive jobs.")
  }
}
