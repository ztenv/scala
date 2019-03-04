package core.kafka
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.ByteArrayDeserializer
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.LocationStrategies._
import com.xunce.pb.Quote

class kafkar_consume_protobuf(conf:SparkConf) {
    private val params = Map[String, Object](
        "bootstrap.servers" -> "192.168.0.159:9092", //指定Kafka的集群地址
        "key.deserializer" -> classOf[StringDeserializer], //指定key的反序列化器
        "value.deserializer" -> classOf[ByteArrayDeserializer], //指定值的反序列化器
        "group.id" -> "test-consumer-group", //consumer的分组id
        "auto.offset.reset" -> "latest", //从新定义消费者以后，不从头消费分区里的数据，只消费定义消费者以后的数据
        "enable.auto.commit" -> (false: java.lang.Boolean) //是否自动提交offsets，也就是更新kafka里的offset，表示已经被消费过了
    )
    private val topics=Array("xc_qoc")
    private val sc=new StreamingContext(conf,Seconds(2))
    private var stream:InputDStream[ConsumerRecord[String,Array[Byte]]]=null
    
    
    def start()={
        stream=KafkaUtils.createDirectStream[String,Array[Byte]](sc,PreferConsistent,Subscribe[String,Array[Byte]](topics,params))
        stream.start()
        stream.foreachRDD(rdd=>{
            if(!rdd.isEmpty()){
                rdd.foreach(record=>{
                    val key=record.key()
                    val topic=record.topic()
                    val value=record.value()
                    val timeStamp=record.timestamp()
                    val snapshot=Quote.SnapShot.parseFrom(value)
        
                    Console.println(timeStamp,topic,snapshot.toString())
                })
            }else{
                Console.println("rdd is empty,continue...")
            }
        }
        )
        sc.start()
    }
    
    def waiting()={
        sc.awaitTermination()
    }
}
