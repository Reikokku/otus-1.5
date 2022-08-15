import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}

import java.net.URI


object HDFSHomework extends App {


  val conf = new Configuration()
  val fs = FileSystem.get(new URI("hdfs://namenode:9000"), conf, "root")

  val destDir = "/ods"
  fs.mkdirs(new Path(destDir))

  val dirs = fs.listStatus(new Path("/stage"))

  dirs.foreach { dir =>
    fs.mkdirs(new Path(destDir + Path.SEPARATOR + dir.getPath.getName))
    val filesArray = fs.listStatus(dir.getPath)
      .map(_.getPath)
      .filter(file =>
         !file.getName.endsWith(".inprogress")
      && !file.getName.endsWith(".DS_Store")
      && fs.getFileStatus(file).getLen != 0)

    if (!filesArray.isEmpty) {
      val sumFile = new Path(dir.getPath + Path.SEPARATOR + "res.csv")
      fs.createFile(sumFile).build().close()
      fs.concat(sumFile, filesArray)
      fs.rename(sumFile, new Path(destDir + Path.SEPARATOR + dir.getPath.getName + Path.SEPARATOR + "part-0000.csv"))
    }

  }



}
