package com.gxb.util;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @Author: gxb
 * @Time: 2020/1/15 16:29
 * @Description: TCP工具
 */
public final class TcpUtil {

    private TcpUtil() {

    }

    // 发送数据
    public static void send(String host, int port, byte[] bytes) throws IOException {
        Socket socket = null;
        BufferedOutputStream bos = null;
        try {
            socket = new Socket(host, port);
            bos = new BufferedOutputStream(socket.getOutputStream());
            bos.write(bytes);
        } finally {
            if (bos != null) bos.close();
            if (socket != null) socket.close();
        }
    }


}
