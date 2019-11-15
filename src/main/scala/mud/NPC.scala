package mud
import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props
import mud.Room.GetDescription
import Player._
import mud.NPC.Move
class NPC(val name: String) extends Actor {
  var loc: ActorRef = null
  //Main.actMng ! ActivityManager.ScheduleActivity(5000, self, Move)
  def receive = {

    case Player.TakeExit(oroom) => {
      if (loc != null) {
        loc ! Room.RemovePlayer()
        }
        oroom match {
          case None       => 
          case Some(room) => loc = room
        }
        loc ! Room.AddPlayer()
        Main.actMng ! ActivityManager.ScheduleActivity(5000, self, NPC.Move)
      }
      
      case Move => loc ! Room.GetExit(util.Random.nextInt(6)) // Working on NPC moving
    case m => println("Unhandled msg in NPC:" + m)
  }
}

object NPC {
    case object Move
}