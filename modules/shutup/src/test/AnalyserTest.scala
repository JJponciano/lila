package lila.shutup

import org.specs2.mutable._
import org.specs2.specification._

class DetectTest extends Specification {

  private def find(t: String) = Analyser(t).badWords
  private def ratio(t: String) = Analyser(t).ratio

  "detector" should {
    "find one bad word" in {
      find("fuck") must_== List("fuck")
      find("well fuck me") must_== List("fuck")
    }
    "find many bad words" in {
      find("fuck that shit") must_== List("fuck", "shit")
      find("Beat them cunting nigger faggots with a communist dick") must_==
        List("cunting", "nigger", "faggots", "dick")
    }
    "find no bad words" in {
      find("") must_== Nil
      find("hello there") must_== Nil
      find("A sonnet is a poetic form which originated in Italy; Giacomo Da Lentini is credited with its invention.") must_== Nil
      find("computer analysis") must_== Nil
    }
    "find badly spelled words" in {
      find("fuk") must_== List("fuk")
      find("well fuk me") must_== List("fuk")
      find("foo ashole bar fukd") must_== List("ashole", "fukd")
    }
    "find variants" in {
      find("cunt kunt cunting kawa kunting") must_== List("cunt", "kunt", "cunting", "kunting")
      find("ass as ashole") must_== List("ass", "ashole")
    }
    "find plurals" in {
      find("cunts kunts cuntings kawas kuntings") must_== List("cunts", "kunts", "cuntings", "kuntings")
    }
    "fucks" in {
      find("fuck fffuuk fektard feak fak phuk") must_== List("fuck", "fffuuk", "fektard", "fak", "phuk")
    }
    "compute ratio" in {
      ratio("fuck that shit") must_== 2d/3
      ratio("Beat them cunting nigger faggots with a communist dick") must_== 4d/9
      ratio("hello there") must_== 0
      ratio("") must_== 0
    }
  }
}
