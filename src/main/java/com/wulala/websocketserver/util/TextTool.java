package com.wulala.websocketserver.util;

public class TextTool {
	private PushAction pushAction;
	private String inputText;

	public String getInputText() {
		return inputText;
	}

	public void setInputText(String inputText) {
		this.inputText = inputText;
	}

	public PushAction msgHandler(String text) {
		pushAction = new PushAction();

		System.out.println("origin text is " + text);
		String subString[] = text.split(";");
		for (int i = 0; i < subString.length; i++) {
			// System.out.println("subString is " + subString[i]);
			String commandString[] = subString[i].split(":");
			String command = commandString[0];
			// System.out.println("command is " + command);
			String commandContent = commandString[1];
			// System.out.println("command content is " + commandContent);

			commandHandler(command, commandContent);

		}
		// test part
		System.out.println(pushAction.toString());
		return pushAction;

	}

	private void commandHandler(String command, String commandContent) {
		switch (command) {
		case "MID":
			pushAction.setMachineID(commandContent);
			break;
		case "TIM":
			pushAction.setTime(commandContent);
			break;
		case "LIK":
			pushAction.setLike(commandContent);
			break;
		case "SUB":
			pushAction.setSubtitle(commandContent);
			break;
		case "CMD":
			pushAction.setCommand(commandContent);
			break;
		default:
			break;
		}

	}

}
