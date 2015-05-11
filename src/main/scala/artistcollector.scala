import java.io.{ File, BufferedWriter, FileWriter }
import scala.collection.mutable.{Set => MSet}
import com.mpatric.mp3agic.{ InvalidDataException, Mp3File }
import play.api.libs.json.Json

object ID3Collector {
  
  val artists = MSet[String]()
  
  def collectArtists(folder: File): MSet[String] = {
    for (fileEntry <- folder.listFiles) {
        if (fileEntry.isDirectory) {
            collectArtists(fileEntry)
        }
        else if (getFileExtension(fileEntry) == "mp3") {
            val artist = readID3Tag(fileEntry)
            if (artist != null)
              artists += artist
        }
    }
    return artists
  }
  
  def getFileExtension(file: File): String = {
    try {
      return file.getPath.split("\\.").last 
    } catch {
      case e: Exception => return ""
    }
  }
  
  def readID3Tag(mp3file: File): String = {
    try {
      val mp3 = new Mp3File(mp3file)
      if (mp3.hasId3v2Tag)
        return mp3.getId3v2Tag().getArtist()
      else if (mp3.hasId3v1Tag)
        return mp3.getId3v1Tag().getArtist()
      else
        return ""
    } catch {
      case e: InvalidDataException => return ""
    }
  }
}

object ArtistCollector extends App {
  val f = new File("/media/More_Storage/Mukke2/47 Million Dollars/Unkaputtbar")
  val artists = ID3Collector.collectArtists(f)
  val artists_json = Json.stringify(Json.toJson(artists))
  
  val outfile = new File("out.json")
  val writer = new BufferedWriter(new FileWriter(outfile))
  writer.write(artists_json)
  writer.close()
}