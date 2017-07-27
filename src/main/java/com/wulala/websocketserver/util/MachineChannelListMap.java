package com.wulala.websocketserver.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MachineChannelListMap {
	private Map<String, MachineChannels> machineChannelListMap;

	public MachineChannelListMap() {
		if (machineChannelListMap == null) {
			machineChannelListMap = new HashMap();
		}
	}

	public void addMachineChannelMap(String machineID, MachineChannels machineChannels) {
		machineChannelListMap.put(machineID, machineChannels);
	}

	public boolean ifMachineChannelListExist(String machineID) {
		return machineChannelListMap.containsKey(machineID);
	}

	public void removeMachineChannelMap(String machineID) {
		machineChannelListMap.remove(machineID);
	}

	public MachineChannels getMachineChannels(String machineID) {
		return machineChannelListMap.get(machineID);
	}

	public void listAllMachineID() {
		Iterator<String> iter = machineChannelListMap.keySet().iterator();
		String key;
		MachineChannels value;
		while (iter.hasNext()) {
			key = iter.next();
			value = machineChannelListMap.get(key);
			System.out.println(key + value.toString());
		}
	}

}
