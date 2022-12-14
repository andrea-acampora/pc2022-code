package pc.rl.examples

import pc.rl.model.QMatrix

object TryQMatrix extends App:

  import pc.rl.model.QMatrix.Action.*
  import pc.rl.model.QMatrix.*

  val rl2: QMatrix.Facade = Facade(
    width = 5,
    height = 5,
    initial = (0,0),
    terminal = {case _=>false},
    reward = { case ((1,0),_) => 10; case ((3,0),_) => 5; case _ => 0},
    jumps = { case ((1,0),_) => (1,4); case ((3,0),_) => (3,2) },
    gamma = 0.9,
    alpha = 0.5,
    epsilon = 0.3,
    v0 = 1
  )

  val zigzagObstacles: QMatrix.Facade = Facade(
    width = 20,
    height = 2,
    initial = (0, 0),
    terminal = {case _ => false},
    reward = { case((19, _), _) => 1; case((4,0),_) => -5; case((8,0),_) => -5; case _ => -1},
    jumps = PartialFunction.empty,
    gamma = 0.9,
    alpha = 0.5,
    epsilon = 0.3,
    v0 = 1
  )

  val labirinth: QMatrix.Facade = Facade(
    width = 10,
    height = 10,
    initial = (0, 0),
    terminal = {/*case (9,9) => true;*/ case _ => false},
    reward = { case((9, 9), _) => 1; case _ => -1},
    jumps = { case ((3,0),_) => (0,0); case ((3,3),_) => (0,0); case ((6,0),_) => (0,0);case ((6,6),_) => (0,0)},
    gamma = 0.9,
    alpha = 0.5,
    epsilon = 0.3,
    v0 = 1
  )
  val q0 = labirinth.qFunction
  println(labirinth.show(q0.vFunction,"%2.1f"))
  val q1 = labirinth.makeLearningInstance().learn(20000,100,q0)
  println(labirinth.show(q1.vFunction,"%2.1f"))
  println(labirinth.show(s => actionToString(q1.bestPolicy(s)),"%7s"))
