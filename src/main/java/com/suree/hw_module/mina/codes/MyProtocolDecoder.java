package com.suree.hw_module.mina.codes;

import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import java.text.SimpleDateFormat;

/**
 * @author IluckySi
 * @date 20140528
 */
@Slf4j
public class MyProtocolDecoder extends CumulativeProtocolDecoder {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private int socketPrefixLength;
    private int protocolLength;
    private boolean getHeadLength;

    public MyProtocolDecoder(int socketPrefixLength) {
        this.socketPrefixLength = socketPrefixLength;
    }

    @Override
    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) {
        try {
//            System.out.println(in+"------------------------------------------in-------------------------------------------->");
            if (in.remaining() > 0) {
                in.mark();
                byte[] l = new byte[in.remaining()];
                in.get(l);
//                System.out.println("1".getBytes("utf-8"));
                String s = bytesToHexString(l);
                String regex = "(.{2})";
                s = s.replaceAll(regex, "$1 ");
//                log.debug("接收报文【" + session.getRemoteAddress() + "】:" + s);
//                System.out.println(   "接收报文【" + session.getRemoteAddress() + "】:" + s);
//                in.reset();
                out.write(s);
            }

//            if (in.remaining() > 7) {
//                in.mark();
//                byte[] l = new byte[7];
//                in.get(l);
//                int len = 0;
//                String s = bytesToHexString(l);
//                if (s.substring(0, 4).equals("5AA5")) {
//                    len = Integer.valueOf(s.substring(4, 6), 16);
//                    in.reset();
//                }else{
//                    byte[] tempArr = new byte[in.remaining()];
//                    in.get(tempArr, 0, in.remaining());
//                    return false;
//                    //int nStart = s.lastIndexOf("5AA5");
////                    if(nStart>0){
////                        in.reset();
////                        byte[] tempArr = new byte[nStart/2];
////
////                        return false;
////                    }
//                }
////            String s = new String(l, "GBK");
//                if (in.remaining() < len || len == 0) {
//                    //如果消息内容不够，则重置恢复position位置到操作前,进入下一轮, 接收新数据，以拼凑成完整数据
//                    in.reset();
//                    return false;
//                } else {
//                    //消息内容足够
//                    in.reset();//重置恢复position位置到操作前
//                    int sumlen = len;//总长 = 包头+包体
//                    byte[] packArr = new byte[sumlen];
//                    in.get(packArr, 0, sumlen);
//
////                IoBuffer buffer = IoBuffer.allocate(sumlen);
////                buffer.put(packArr);
////                buffer.flip();
//                    //String msg = new String(packArr, "GBK");
//                    String msg = bytesToHexString(packArr);
//                    String regex = "(.{2})";
//                    msg = msg.replaceAll(regex, "$1 ");
//                    out.write(msg);
//                    //buffer.free();
//                    log.debug(df.format(new Date())+": "+msg);
//
//                    if (in.remaining() > 0) {//如果读取一个完整包内容后还粘了包，就让父类再调用一次，进行下一次解析
//                        byte[] tempArr = new byte[in.remaining()];
//                        in.get(tempArr, 0, in.remaining());
//                        return false;
//                        //return true;
//                    }
//                }
//            }
        } catch (Exception e) {
//            log.error("----------------------------------------");
            log.error(e.getMessage());
//            log.error("----------------------------------------");
            return false;
        }
        return false;
    }

    public static final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        byte[] bytes=",".getBytes();
        System.out.println(bytes.length);
        System.out.println(bytes[0]);
        byte[] b={20};
        System.out.println(new String(b));
    }

    public static int bytes2int(byte[] bytes) {
        int result = 0;
        if (bytes.length == 4) {
            int a = (bytes[0] & 0xff) << 24;//说明二
            int b = (bytes[1] & 0xff) << 16;
            int c = (bytes[2] & 0xff) << 8;
            int d = (bytes[3] & 0xff);
            result = a | b | c | d;
        }
        return result;
    }

    public static String ioBufferToString(Object message) {
        if (!(message instanceof IoBuffer)) {
            return "";
        }
        IoBuffer ioBuffer = (IoBuffer) message;
        byte[] b = new byte[ioBuffer.limit()];
        ioBuffer.get(b);
        StringBuffer stringBuffer = new StringBuffer();

        for (int i = 0; i < b.length; i++) {

            stringBuffer.append((char) b[i]);
        }
        return stringBuffer.toString();
    }
} 