package main
import java.util.Properties
import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame,SparkSession}

object main {
    
    def main(args:Array[String])={
        val conf=new SparkConf().setMaster("local").setAppName("oracle_example")
        val spark=SparkSession.builder().config(conf).getOrCreate()
        val connectionStr="jdbc:oracle:thin:@192.168.0.189:1521:ogg1" //ogg1是数据库
        val properties=new Properties()
        properties.put("user","ogg") //用户名
        properties.put("password","xc_ogg@2019")//密码
        
        val df:DataFrame=spark.sqlContext.read.jdbc(connectionStr,"snapshot",properties) //表名
        df.printSchema()
        df.show()
    
        Console.println(df.first())
        Console.println("row count="+df.count())
    
    }
    
}
