lazy val root = (project in file(".")).
  settings(
    name := "artistscollector",
    version := "1.0",
    scalaVersion := "2.11.6",
    resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/",
    libraryDependencies +=  "com.typesafe.play" %% "play-json" % "2.3.4",
    libraryDependencies +=  "com.mpatric" % "mp3agic" % "0.8.3"
  )