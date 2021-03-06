package mud
import akka.actor.ActorSystem
import akka.actor.Props
import java.net.ServerSocket
import java.io.PrintStream
import java.io.BufferedReader
import java.io.InputStreamReader
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.collection.mutable._
/**
This is a stub for the main class for your MUD.
*/
object Main extends App {
	
		println("Welcome to my MUD.")
		
		
		 val system = ActorSystem("MUD")

  

  implicit val ec = system.dispatcher
  
  val playMng = system.actorOf(Props[PlayerManager], "playMng")
  val roomMng = system.actorOf(Props[RoomManager], "roomMng")
  val npcMng = system.actorOf(Props[NPCManager], "npcMng")
  val actMng = system.actorOf(Props[ActivityManager], "actMng")
  var plList: Set[String] = Set.empty
  system.scheduler.schedule(1.second, 0.1.second, playMng, PlayerManager.CheckAllInput)
  system.scheduler.schedule(1.second, 0.1.second, actMng, ActivityManager.CheckQueue)

    val ss = new ServerSocket(4041) 
    while(true) {
      val sock = ss.accept()
     Future {
      val out = new PrintStream(sock.getOutputStream())
      out.println("What is your name?")
      val in = new BufferedReader(new InputStreamReader(sock.getInputStream()))
      val name = in.readLine()
      if (!plList.contains(name)) {      
        out.println(name + " has connected.")
        playMng ! PlayerManager.CreatePlayer(name, sock, out, in)
        plList += name
      }
      else out.println("name already exists")
     }
    }
  }
  //   }
  // }

// 		val player = new Player()
		
// 		/* description of the room
// 		 */
// 	   var command = readLine
// // 	   while (command != "exit") {
// // 		 player.processCommand(command)
// // 		 command = readLine   
// // 	}
// // 	}
// // }
// 	}

