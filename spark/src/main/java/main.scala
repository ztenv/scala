package main
import org.apache.spark.SparkConf
import core.kafka.kafkar

//版本详见pom.xml
//kafka地址：192.168.0.159:9092
//消费的topic:xc_qoc

object main {
    
    def main(args:Array[String])={
        val conf=new SparkConf()
        conf.setMaster("local").setAppName("kafkar")
        //val spark=SparkSession.builder().config(conf).getOrCreate()
        val kafka=new kafkar(conf)
        kafka.start()
        kafka.waiting()
    }
}
