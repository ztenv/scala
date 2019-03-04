package main
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import core.kafka.kafkar_consume_string
import core.kafka.kafkar_consume_protobuf

//版本详见pom.xml
//kafka地址：192.168.0.159:9092
//消费的topic:xc_qoc

object main {
    
    def main(args:Array[String]):Unit={
        val conf=new SparkConf()
        conf.setMaster("local").setAppName("kafkar")
        //val spark=SparkSession.builder().config(conf).getOrCreate()
        var kaf=new kafkar_consume_protobuf(conf)
        kaf.start()
        kaf.waiting()
    }
}
