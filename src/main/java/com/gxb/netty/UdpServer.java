package com.gxb.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: gxb
 * @Date: 2019/11/18 14:43
 * @Description: UDP服务端
 */
public class UdpServer {
    private static final Logger log = LoggerFactory.getLogger(UdpServer.class);

    protected String name;
    protected String host;
    protected int port;
    protected ChannelHandler handler;
    private NioEventLoopGroup group;
    private Bootstrap boot;

    public UdpServer() {

    }

    public UdpServer(int port) {
        this();
        this.port = port;
    }

    public UdpServer(int port, ChannelHandler handler) {
        this();
        this.port = port;
        this.handler = handler;
    }

    public UdpServer(String name, int port, ChannelHandler handler) {
        this();
        this.name = name;
        this.port = port;
        this.handler = handler;
    }

    /**
     * 启动UDP服务端
     *
     * @throws InterruptedException 启动异常
     */
    public void start() throws InterruptedException {
        if (name == null) {
            name = "UDP-Server-" + ":" + port;
        }
        if (host == null) {
            host = "0.0.0.0";
        }
        group = new NioEventLoopGroup();
        boot = new Bootstrap();
        boot.group(group).channel(NioDatagramChannel.class).option(ChannelOption.SO_BROADCAST, true).option(
                ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT).handler(handler);
        ChannelFuture future = boot.bind(host, port).sync();
        if (future.isSuccess()) {
            log.error(String.format("UDP服务端[%s]:启动成功^_^", name));
        } else {
            log.error(String.format("UDP服务端[%s]:启动失败...", name));
        }
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

    public ChannelHandler getHandler() {
        return handler;
    }

    public void setHandler(ChannelHandler handler) {
        this.handler = handler;
    }
}
