package com.xunce.mms.tests

import com.xunce.pb.Quote
import com.xunce.pb.TypeDefCls.TypeDef.Exchange

object test_pb {
    
    def main(args:Array[String])={
        Console.println(Serialize())
    }
    
    def Serialize():String={
        var msg=Quote.SnapShot.newBuilder()
        msg.setCode("000001")
        msg.setExchange(Exchange.EX_SZ)
        msg.setCnName("中国平安")
        msg.setDate(20190301)
        msg.setHigh(100)
        msg.setLow(50)
        msg.setOpen(55)
        msg.setLastprice(98)
        msg.setSecTypeValue(0)
        return msg.toString()
    }
    
}
