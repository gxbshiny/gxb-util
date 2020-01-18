package com.gxb.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: gxb
 * @Date: 2019/11/18 11:15
 * @Description: TCP服务端
 */
public class TcpServer {
    private static final Logger log = LoggerFactory.getLogger(TcpServer.class);

    protected String name;
    protected int port;
    protected ChannelInitializer<SocketChannel> initializer;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workGroup;
    private ServerBootstrap boot;

    public TcpServer() {

    }

    public TcpServer(int port) {
        this();
        this.port = port;
    }

    public TcpServer(int port, ChannelInitializer<SocketChannel> initializer) {
        this();
        this.port = port;
        this.initializer = initializer;
    }

    public TcpServer(String name, int port, ChannelInitializer<SocketChannel> initializer) {
        this();
        this.name = name;
        this.port = port;
        this.initializer = initializer;
    }

    public void start() throws InterruptedException {
        if (name == null) {
            name = "TCP-Server-" + port;
        }
        bossGroup = new NioEventLoopGroup(1);
        workGroup = new NioEventLoopGroup();
        boot = new ServerBootstrap();
        boot.group(bossGroup, workGroup).channel(NioServerSocketChannel.class).childOption(ChannelOption.ALLOCATOR,
                PooledByteBufAllocator.DEFAULT).childHandler(initializer);
        ChannelFuture future = boot.bind(port).sync();
        if (future.isSuccess()) {
            log.error(String.format("TCP服务端[%s]启动成功^_^ 端口:%d", name, port));
        } else {
            log.error(String.format("TCP服务端[%s]启动失败... 端口:%d", name, port));
        }
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ChannelInitializer<SocketChannel> getInitializer() {
        return initializer;
    }

    public void setInitializer(ChannelInitializer<SocketChannel> initializer) {
        this.initializer = initializer;
    }
}
