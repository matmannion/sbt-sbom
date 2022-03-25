import scala.xml.XML

lazy val root = (project in file("."))
  .settings(
    name := "dependencies",
    version := "0.1",
    libraryDependencies ++= Dependencies.library,
    scalaVersion := "2.12.8",
    check := checkTask.value
  )

lazy val check = taskKey[Unit]("check")
lazy val checkTask = Def.task {
  val s: TaskStreams = streams.value
  s.log.info("Verifying bom content...")
  val bomFile = makeBom.value
  val context = thisProject.value
  val expected = XML.loadFile(file(s"${context.base}/etc/bom.xml"))
  val actual = XML.loadFile(bomFile)
  require(expected \ "components" == actual \ "components", s"${context.id} is failed.")
  s.log.info(s"${bomFile.getPath} content verified")
}
