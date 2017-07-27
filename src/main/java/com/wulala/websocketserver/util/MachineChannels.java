package com.wulala.websocketserver.util;

import java.util.ArrayList;

import io.netty.channel.Channel;

public class MachineChannels {

	private ArrayList<MachineChannel> machineChannelList;
	private String machineID;

	public MachineChannels(String _machineID) {
		if (machineChannelList == null) {
			machineChannelList = new ArrayList();
			machineID = _machineID;
		}
	}

	public ArrayList<MachineChannel> getMachineChannelMap() {
		return machineChannelList;
	}

	public String getMachineID() {
		return machineID;
	}

	public void setMachineID(String machineID) {
		this.machineID = machineID;
	}

	public void addMachineChannel(MachineChannel mChannel) {
		machineChannelList.add(mChannel);
	}

	public void removeMachineChannel(MachineChannel machineChannel) {
		machineChannelList.remove(machineChannel);
	}

	public boolean ifChannelExist(Channel channel) {
		// return machineChannelList.contains(machineChannel);
		boolean ifChannelExist = false;
		for (int i = 0; i < machineChannelList.size(); i++) {
			if (channel.id().asShortText().equals((machineChannelList).get(i).getChannelID())) {
				ifChannelExist = true;
			}
		}
		return ifChannelExist;

	}

	@Override
	public String toString() {

		String result = "";
		for (int i = 0; i < machineChannelList.size(); i++) {
			result = result + machineChannelList.get(i).toString();
		}
		return "MachineChannels [machineChannelList=" + result + ", machineID=" + machineID + "]";
	}

}
