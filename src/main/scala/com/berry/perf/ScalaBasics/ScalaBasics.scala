package com.berry.perf.ScalaBasics

import scala.concurrent.Future
import scala.util.{Failure, Success}

object ScalaBasics extends App{

  //values and variables
  val aBoolean:Boolean = false

  // expressions
  val anIfExpression = if(4 > 5) "big" else "small"

  // instruction vs expression
  val theUnit = println("Berry") //Unit = "no meaningful value" = void

  //functions
  def myfun(x:Int) = 42

  //OOP
  // Single class inheritance
  
  class Animal
  class Dog extends Animal 

  trait Carnivore{
    def eat(animal: Animal): Unit
  }

  class Crocodile extends Animal with Carnivore{
    override def eat(animal: Animal) :Unit = print("Croc-crunch")
  }

  // Singleton object -pattern
  object  MySingleton

  //companion -- class or trait and object with the same name
  object Carnivore

  //Generics - Contravariance, covariance
  trait Mylist[A]

  // method notation
  val x =  1+2
  val y = 1.+(2)

  // Functional Programming
  val incrementer: Function1[Int, Int] = new Function1[Int, Int]{
    override def apply(x: Int): Int = x + 1
  }
  //anonymous function or lambda
  val incrementer1: Int => Int =  x => x + 1
  val incr = incrementer1(2)

  //map, flatmap, filter-- HOF -- we can pass functions as arguments
  val processList = List(1,2,3,4).map(incrementer1)

  // Pattern Matching
  val unknown: Any = 45
  val ordinal = unknown match {
    case 1 => "f"
    case 2 => "s"
    case _ => "unkown"
  }

  // try -catch

  try {
    throw new NullPointerException
  }catch {
    case _: NullPointerException => "Some value"
    case _ : Throwable => "Everything"
  }

  // Future

  import scala.concurrent.ExecutionContext.Implicits.global
  val aFuture =  Future{
    // Expensive computation , runs on another thread
    43
  }

  aFuture.onComplete{
    case Success(meaningOFLife) => println(s"Found $meaningOFLife")
    case Failure(e) => println(s"Failed $e")
  }

  // Partial Functions
  val aPartialFun: PartialFunction[Int,Int] = {
    case 1 => 34
    case 5 => 543
    case _ => 999
  }

  // Implicits

  // auto -injection by the compiler
  def methodWithImplicitArguments(implicit x: Int) =  x + 43
  implicit val implicitInt = 7

  val implicitCall = methodWithImplicitArguments // 7

  // implicit conversion -- implicit defs

  case class Person(name: String){
    def greet = println(s"Hi name is $name")
  }

  implicit def fromStringToPerson(name: String)  = Person(name)
  "Berry".greet //fromStringToPerson("Bob").greet

    // implicit conversion  - implicit classes
  implicit class Cat(name: String){
      def meow = println(s"Mewow!!!")
    }
  "Jessi".meow

  /*
  - local scope
  - imported scope
  - companion object of the types involved in the method call
  */


}
