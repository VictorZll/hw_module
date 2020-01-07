package com.suree.hw_module.mina.codes;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

/** 
 * @author IluckySi 
 * @date 20140528 
 */  
public class MyProtocolEncoder extends ProtocolEncoderAdapter {

    private int socketPrefixLength;

    public MyProtocolEncoder(int socketPrefixLength) {
        this.socketPrefixLength = socketPrefixLength;  
    }
  
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        CharsetEncoder charsetEncoder = Charset.forName("UTF-8").newEncoder();
        System.out.println(message+"------------------------------encodemsg");
        String value = ((String) message);
        System.out.println("value---------------"+value);
        IoBuffer buffer = IoBuffer.allocate(64);
        buffer.setAutoExpand(true);
//        buffer.putPrefixedString(value, charsetEncoder);
        buffer.putString(value, charsetEncoder);
        buffer.flip();
        out.write(buffer);  
    }
}