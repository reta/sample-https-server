import sbt._
import Keys._
import com.typesafe.sbteclipse.core.EclipsePlugin._

object ProjectBuild extends Build {
  override val settings = super.settings ++ Seq(
    organization := "com.example",    
    name := "sample-https-server",    
    version := "0.0.1-SNAPSHOT",    
    
    scalaVersion := "2.11.7",
    scalacOptions ++= Seq( "-encoding", "UTF-8", "-target:jvm-1.8" ),    
    javacOptions ++= Seq( "-encoding", "UTF-8", "-source", "1.8", "-target", "1.8" ),
    outputStrategy := Some( StdoutOutput ),
    compileOrder := CompileOrder.ScalaThenJava,
    
    resolvers ++= Seq( 
        Resolver.mavenLocal,        
        Resolver.sonatypeRepo( "sonatype" ), 
        Resolver.typesafeRepo( "typesafe" ),
        "spray repo" at "http://repo.spray.io"
    ),        

    crossPaths := false,            
    fork in run := true,
    connectInput in run := true,
    
    EclipseKeys.executionEnvironment := Some(EclipseExecutionEnvironment.JavaSE18),
    EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource,
    EclipseKeys.withJavadoc := true,
    EclipseKeys.withSource := true
  )
    
  lazy val main = Project( 
    id = "sample-https-server",  
    base = file("."), 
    settings = Project.defaultSettings ++ Seq(
      mainClass in (Compile, run) := Some("com.example.https.HttpsServerRunner"),
        
      initialCommands in console += """
	      import com.example.https._
      """,
	      
      libraryDependencies ++= Seq(
          "io.spray" %% "spray-http" % "1.3.3",
          "io.spray" %% "spray-can" % "1.3.3",
          "io.spray" %% "spray-routing" % "1.3.3",
          "io.spray" %% "spray-io" % "1.3.3",
          "com.typesafe.akka" %% "akka-actor" % "2.3.12",
          "com.typesafe.akka" %% "akka-slf4j" % "2.3.12",
          "com.jayway.restassured" % "rest-assured" % "2.5.0" % "test",
          "junit" % "junit" % "4.12" % "test",
          "com.novocode" % "junit-interface" % "0.11" % "test"
        )
      ) 
  )  
}
