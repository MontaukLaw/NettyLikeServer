package com.wulala.websocketserver;

import java.net.InetSocketAddress;

import com.wulala.websocketserver.util.MachineChannelListMap;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;

public class ChatServer {
	private final ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
	private final EventLoopGroup group = new NioEventLoopGroup();
	private final MachineChannelListMap channelMap = new MachineChannelListMap();

	private Channel channel;

	public ChannelFuture start(InetSocketAddress address) {
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(group).channel(NioServerSocketChannel.class).childHandler(createInitializer(channelGroup, channelMap));
		ChannelFuture future = bootstrap.bind(address);
		future.syncUninterruptibly();
		channel = future.channel();
		return future;
	}

	protected ChannelInitializer<Channel> createInitializer(ChannelGroup group, MachineChannelListMap channelMap) {
		return new ChatServerInitializer(group, channelMap);
	}

	public void destroy() {
		if (channel != null) {
			channel.close();
		}
		channelGroup.close();
		group.shutdownGracefully();
	}

	public static void main(String[] args) {
		int port;
		if (args.length != 1) {
			// System.err.println("Please input port as argument");
			// System.exit(1);
			port = 9999;
		} else {
			port = Integer.parseInt(args[0]);
		}
		final ChatServer endpoint = new ChatServer();
		ChannelFuture future = endpoint.start(new InetSocketAddress(port));
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				endpoint.destroy();
			}
		});

		future.channel().closeFuture().syncUninterruptibly();
	}

}
