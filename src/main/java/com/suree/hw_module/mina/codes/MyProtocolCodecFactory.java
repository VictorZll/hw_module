package com.suree.hw_module.mina.codes;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/** 
 * @author IluckySi 
 * @date 20140528 
 */  
public class MyProtocolCodecFactory implements ProtocolCodecFactory {

    private final MyProtocolEncoder encoder;

    private final MyProtocolDecoder decoder;

    public MyProtocolCodecFactory(int socketPrefixLength) {
        encoder = new MyProtocolEncoder(socketPrefixLength);
        decoder = new MyProtocolDecoder(socketPrefixLength);
    }

    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        return encoder;  
    }  
  
    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
        return decoder;  
    }  
}  