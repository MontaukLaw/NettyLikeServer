package com.wulala.test;
import com.wulala.websocketserver.util.MachineChannel;
import com.wulala.websocketserver.util.MachineChannelListMap;
import com.wulala.websocketserver.util.MachineChannels;

public class MachineChannelTest {

	public static void main(String[] args) {
		MachineChannelListMap map = new MachineChannelListMap();
		String machineID = "MID001";
		String ipAddr = "0:0:0:0:0:0:0:1:8966";
		MachineChannel machineChannel1 = new MachineChannel(machineID, ipAddr);
		ipAddr = "0:0:0:0:0:0:2:1:8966";
		MachineChannel machineChannel2 = new MachineChannel(machineID, ipAddr);
		ipAddr = "0:0:0:0:0:0:3:1:8966";
		MachineChannel machineChannel3 = new MachineChannel(machineID, ipAddr);
		MachineChannels channels = new MachineChannels(machineID);
		channels.addMachineChannel(machineChannel1);
		channels.addMachineChannel(machineChannel2);
		channels.addMachineChannel(machineChannel3);

		String machineID2 = "MID002";
		String ipAddr2 = "1:0:0:0:0:0:0:1:8966";
		MachineChannel machineChannel4 = new MachineChannel(machineID2, ipAddr2);
		ipAddr = "2:0:0:0:0:0:2:1:8966";
		MachineChannel machineChannel5 = new MachineChannel(machineID2, ipAddr2);
		ipAddr = "3:0:0:0:0:0:3:1:8966";
		MachineChannel machineChannel6 = new MachineChannel(machineID2, ipAddr2);
		MachineChannels channels2 = new MachineChannels(machineID2);
		channels2.addMachineChannel(machineChannel4);
		channels2.addMachineChannel(machineChannel5);
		channels2.addMachineChannel(machineChannel6);

		map.addMachineChannelMap(machineID2, channels2);
		map.addMachineChannelMap(machineID, channels);
		map.listAllMachineID();
		
	}

}
