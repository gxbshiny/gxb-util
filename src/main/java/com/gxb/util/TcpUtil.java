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

    /**
     * 用tcp协议向目标主机端口发送数据 采用短连接 每发送一次数据建立断开一次连接
     *
     * @param host  目标主机
     * @param port  目标主机的tcp端口
     * @param bytes 需要发送的数据
     * @throws IOException IO异常
     */
    public static void send(String host, int port, byte[] bytes) throws IOException {
        try (Socket socket = new Socket(host, port); BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream())) {
            bos.write(bytes);
        }
    }


}
