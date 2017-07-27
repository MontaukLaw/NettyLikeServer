package com.wulala.websocketserver.util;

import io.netty.channel.Channel;

public class MachineChannel {
	private String machineID, ipAddr;
	private Channel channel;

	public String getChannelID() {
		return channel.id().asShortText();
	}

	public String getMachineID() {
		return machineID;
	}

	public void setMachineID(String machineID) {
		this.machineID = machineID;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public MachineChannel(String _machineID, String _ipAddr, Channel _channel) {
		super();
		this.machineID = _machineID;
		this.ipAddr = _ipAddr;
		this.channel = _channel;
	}

	// this constructor just for test map and list
	public MachineChannel(String _machineID, String _ipAddr) {
		super();
		this.machineID = _machineID;
		this.ipAddr = _ipAddr;
	}

	@Override
	public String toString() {
		return "MachineChannel [machineID=" + machineID + ", ipAddr=" + ipAddr + ", channel=" + channel + "]";
	}

}
