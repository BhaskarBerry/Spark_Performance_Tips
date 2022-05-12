package com.berry.perf.SparkBasics

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

/**
  * Creating a spark Session
  * the DF API
  * filtering, Aggregating, grouping, joining
  * Datasets
  * RDDs
  */
object SparkBasics extends App{
  // the entry point to spark structure API
  val spark = SparkSession.builder.
    appName("Spark Basics")
    .master("local[2]")
    .getOrCreate()

  // read a DF
  val cars = spark.read
    .format("json")
    .option("inferSchema","true" )
    .load("src/main/resources/data/cars/cars.json")

//  cars.show()
//  cars.printSchema()

 import spark.implicits._
  //select
  val usefulCarsData = cars.select(
    col("Name"), // Column Object
    $"Year" // another column object (needs spark implicits)
    , (col("Weight_in_lbs") / 2.2).as("weight_in_kg"),
    expr("Weight_in_lbs/2.2").as("weight_in_kg_2")
  )
  //usefulCarsData.show()
  val carsWeights = cars.selectExpr("Weight_in_lbs/2.2")

  // Filtering
  val europeanCars =  cars.filter(col("Origin") =!= "USA" )

  // aggregation
  val avgHP = cars.select(avg(col("Horsepower")).as("aveargaeHP")) // sum , mean ,stddev, min , max

  // grouping
  val countByOrigin = cars.groupBy(col("Origin")) // a relational grouped dataset
    .count()

  // joining

  val guitarPlayers = spark.read
    .format("json")
    .option("inferSchema","true" )
    .load("src/main/resources/data/guitarPlayers/guitarPlayers.json")

  guitarPlayers.show()
  val bands = spark.read
    .option("inferSchema","true" )
    .json("src/main/resources/data/bands/bands.json") // short hand notation

  bands.show()

  val guitaristBand = guitarPlayers.join(bands,
    guitarPlayers.col("band") === bands.col("id"))

  /*
  join types
  -- inner : only the matching rows are kept
  -- left/right/ full outer join
  -- semi/anti joins
   */

  // datasets = typed distributed collection of objects
  case class GuitarPlayers(id:Long,name:String,guitars:Seq[Long],band:Long)

  val guitarPlayersDS = guitarPlayers.as[GuitarPlayers] // needs spark.implicits
  guitarPlayersDS.map(_.name)

  // spark SQL
  cars.createOrReplaceTempView("cars")
  val americaCars = spark.sql(
    """
      |select Name from cars where Origin = 'USA'
      |""".stripMargin
  )

  // low level -API - RDD

  val sc = spark.sparkContext
  val numRDD:RDD[Int] = sc.parallelize(1 to 1000)

  // functional operator
  val doubles = numRDD.map(_ * 2)

  // RDD -> DF
  val numDF = numRDD.toDF("Number") // you loose type info , you get SQL capability

  // RDD -> DS
  val numDS = spark.createDataset(numRDD)

  // DS -> RDD
  val guitarPlayersRDD = guitarPlayersDS.rdd

  // DF -> RDD
  val carsRDD = cars.rdd // RDD[Row - Untyped collection of information]

}
