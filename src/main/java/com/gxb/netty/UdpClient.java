package com.gxb.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @Author: gxb
 * @Date: 2019/11/18 11:50
 * @Description: UDP客户端
 */
public class UdpClient {
    private static final Logger log = LoggerFactory.getLogger(UdpClient.class);

    protected String name;
    protected String host;
    protected int port;
    protected ChannelHandler handler;
    protected int localPort;

    private Channel channel;
    private InetSocketAddress addr;

    public UdpClient() {
        this.localPort = 0;
    }

    public UdpClient(String host, int port) {
        this();
        this.host = host;
        this.port = port;
    }

    public UdpClient(String host, int port, ChannelHandler handler) {
        this();
        this.host = host;
        this.port = port;
        this.handler = handler;
    }

    public UdpClient(String name, String host, int port, ChannelHandler handler) {
        this();
        this.name = name;
        this.host = host;
        this.port = port;
        this.handler = handler;
    }

    /**
     * 启动UDP客户端
     *
     * @throws InterruptedException 异常
     */
    public void start() throws InterruptedException {
        if (name == null) {
            name = "UDP-Client-" + host + ":" + port;
        }
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap boot = new Bootstrap();
        boot.group(group).channel(NioDatagramChannel.class).option(ChannelOption.SO_BROADCAST, true).option(
                ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT).handler(handler);
        channel = boot.bind(localPort).sync().channel();
        addr = new InetSocketAddress(host, port);
        log.error(String.format("UDP客户端[%s]:启动成功^_^", name));
    }

    public final void sendMsg(byte[] bytes) throws InterruptedException {
        channel.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(bytes), addr)).sync();
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

    public int getLocalPort() {
        return localPort;
    }

    public void setLocalPort(int localPort) {
        this.localPort = localPort;
    }
}
