package com.gxb.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @Author: gxb
 * @Date: 2019/11/18 10:47
 * @Description: TCP客户端
 */
public class TcpClient {
    private static final Logger log = LoggerFactory.getLogger(TcpClient.class);

    protected String name;
    protected String host;
    protected int port;
    protected int localPort;
    protected Channel channel;
    protected ChannelInitializer<SocketChannel> initializer;
    private EventLoopGroup group;
    private Bootstrap boot;
    private int seconds;
    private boolean reconnect;

    public TcpClient() {
        localPort = 0;
        seconds = 0;
        reconnect = true;
    }

    public TcpClient(String host, int port) {
        this();
        this.host = host;
        this.port = port;
    }

    public TcpClient(String host, int port, ChannelInitializer<SocketChannel> initializer) {
        this();
        this.host = host;
        this.port = port;
        this.initializer = initializer;
    }

    public TcpClient(String name, String host, int port, ChannelInitializer<SocketChannel> initializer) {
        this();
        this.name = name;
        this.host = host;
        this.port = port;
        this.initializer = initializer;
    }

    /**
     * 启动TCP客户端
     */
    public void start() {
        if (name == null) {
            name = "TCP-Client-" + host + ":" + port;
        }
        group = new NioEventLoopGroup();
        boot = new Bootstrap();
        boot.group(group).channel(NioSocketChannel.class).option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT).handler(initializer).bind(localPort);
        connect();
    }

    /**
     * 连接到服务器
     */
    public void connect() {
        if (channel != null && channel.isActive()) {
            return;
        }
        ChannelFuture future = boot.connect(host, port);
        future.addListener((ChannelFutureListener) cf -> {
            if (cf.isSuccess()) {
                channel = cf.channel();
                log.error(String.format("TCP客户端[%s]:连接服务器%s:%d成功^_^", name, host, port));
                seconds = 0;
            } else {
                seconds++;
                log.error(String.format("TCP客户端[%s]:连接服务器%s:%d失败 %d秒后重连...", name, host, port, seconds * 10));
                if (reconnect) {
                    cf.channel().eventLoop().schedule(() -> connect(), seconds * 10, TimeUnit.SECONDS);
                }
            }
        });
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getLocalPort() {
        return localPort;
    }

    public void setLocalPort(int localPort) {
        this.localPort = localPort;
    }

    public Channel getChannel() {
        return channel;
    }

    public ChannelInitializer<SocketChannel> getInitializer() {
        return initializer;
    }

    public void setInitializer(ChannelInitializer<SocketChannel> initializer) {
        this.initializer = initializer;
    }

    public boolean isReconnect() {
        return reconnect;
    }

    public void setReconnect(boolean reconnect) {
        this.reconnect = reconnect;
    }
}
