package com.wulala.websocketserver;

import com.wulala.websocketserver.util.MachineChannel;
import com.wulala.websocketserver.util.MachineChannelListMap;
import com.wulala.websocketserver.util.MachineChannels;
import com.wulala.websocketserver.util.PushAction;
import com.wulala.websocketserver.util.TextTool;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
	private final ChannelGroup group;
	private Object WebSocketServerProtocalHandler;
	private final MachineChannelListMap channelMap;

	public TextWebSocketFrameHandler(ChannelGroup _group, MachineChannelListMap _channelMap) {
		this.group = _group;
		this.channelMap = _channelMap;

	}
	//
	// @Override
	// public void channelRead(ChannelHandlerContext ctx, Object msg) throws
	// Exception {
	// WebSocketFrame frame = (WebSocketFrame) msg;
	// if (frame instanceof PingWebSocketFrame) {
	// System.out.println("Ping Pong....");
	// ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
	// }
	// }

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
			ctx.pipeline().remove(HttpRequestHandler.class);
			group.writeAndFlush(new TextWebSocketFrame("Client " + ctx.channel() + " joined"));
			group.add(ctx.channel());
		} else {
			super.userEventTriggered(ctx, evt);
		}
	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) throws Exception {
		// System.out.println("ctx name is" + ctx.name() + " msg is:" +
		// msg.toString());
		System.out.println("channelRead0");
		if (!(frame instanceof TextWebSocketFrame)) {
			throw new UnsupportedOperationException(String.format("%s frame types not supported", frame.getClass().getName()));
		}

		String request = ((TextWebSocketFrame) frame).text();
		TextTool textTool = new TextTool();
		PushAction pushAction = textTool.msgHandler(request);
		
		if (pushAction.getCommand() != null) {
			if (pushAction.getCommand().equals("REG")) {

				String machineID = pushAction.getMachineID();
			
				if (channelMap.ifMachineChannelListExist(machineID)) {
					
					MachineChannels machineChannels = channelMap.getMachineChannels(machineID);
					
					if (machineChannels.ifChannelExist(ctx.channel())) {
						
						ctx.channel().writeAndFlush(new TextWebSocketFrame("MACHINE_REG:FAIL;REASON:Channel Already Exist"));
					} else {
						
						MachineChannel machineChannel = new MachineChannel(machineID, "ip", ctx.channel());
						machineChannels.addMachineChannel(machineChannel);
						ctx.channel().writeAndFlush(new TextWebSocketFrame("MACHINE_REG:OK"));
					}
				} else {
					
					MachineChannel machineChannel = new MachineChannel(machineID, "ip", ctx.channel());

					MachineChannels machineChannels = new MachineChannels(machineID);
					machineChannels.addMachineChannel(machineChannel);
					channelMap.addMachineChannelMap(machineID, machineChannels);
					ctx.channel().writeAndFlush(new TextWebSocketFrame("RESTAURANT_REG:OK;MACHINE_REG:OK"));
				}
			}
		}
		if (pushAction.getLike() != null) {
			if (pushAction.getLike().equals("YES")) {
				String targetMachineId = pushAction.getMachineID();
				if (channelMap.ifMachineChannelListExist(targetMachineId)) {
					MachineChannels targetChannels = channelMap.getMachineChannels(targetMachineId);
					for (int i = 0; i < targetChannels.getMachineChannelMap().size(); i++) {
						Channel channel = targetChannels.getMachineChannelMap().get(i).getChannel();
						// System.out.println(list.get(i));
						channel.writeAndFlush(new TextWebSocketFrame("Like" + System.currentTimeMillis()));

						// group.find(channel.id()).writeAndFlush(new
						// TextWebSocketFrame("Like" +
						// System.currentTimeMillis()));
					}

				}
			}
		}

		// ������:
		// channelMap.listAllMachineID();
		// ctx.writeAndFlush(msg.retain());

		// for (Iterator it = group.iterator(); it.hasNext();) {
		// System.out.println("value=" + it.next().toString());
		// }
		// group.writeAndFlush(msg.retain());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
