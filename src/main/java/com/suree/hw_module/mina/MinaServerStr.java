package com.suree.hw_module.mina;

import com.suree.hw_module.mina.codes.MyProtocolCodecFactory;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

@Component
@Order(value = 1200)
public  class MinaServerStr extends Thread{
    private int PORT=28899;//18899
    private IoAcceptor ioAcceptor=null;

    public IoAcceptor getIoAcceptor() {
        return ioAcceptor;
    }

    public void setIoAcceptor(IoAcceptor ioAcceptor) {
        this.ioAcceptor = ioAcceptor;
    }

    @Override
    public void run() {
        ioAcceptor=new NioSocketAcceptor();
        //设置过滤器,编解码
        ioAcceptor.getFilterChain().addLast("codecFilter",new ProtocolCodecFilter(new MyProtocolCodecFactory(6)
        ));
        //设置缓冲区
        ioAcceptor.getSessionConfig().setReadBufferSize(1024);
        ioAcceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE,10);
        ServiceHandlerStr serviceHandlerStr = new ServiceHandlerStr();
        String name = serviceHandlerStr.getName();
        if(name==null){
            serviceHandlerStr.setName(Thread.currentThread().getName());
        }
        ioAcceptor.setHandler(serviceHandlerStr);
        try {
            ioAcceptor.bind(new InetSocketAddress(Integer.parseInt(Thread.currentThread().getName())));
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("IO异常！！！！！！！！！");
        }

        System.out.println("Server=>"+Integer.parseInt(Thread.currentThread().getName()));
    }

    public static void main(String[] args) {

    }
}
