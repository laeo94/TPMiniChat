
public class TextChatLogger implements IChatLogger {

	@Override
	public void clientConnected(String ip) {
		System.out.println("Client Connected : "+ip);

	}

	@Override
	public void clientDisconnected(String ip, String name) {
		System.out.println("Client disconnected "+name+ "  "+ip);

	}

	@Override
	public void clientGotName(String ip, String name) {
		System.out.println("clientGotName "+name+ " "+ ip);

	}

	@Override
	public void clientGotCommand(String name, int command) {
		System.out.println("clientgotCommand"+name+ " "+command);

	}

	@Override
	public void publicChat(String from, String msg) {
		System.out.println("From "+from+" message :"+ msg);

	}

	@Override
	public void privateChat(String from, String to, String msg) {
		System.out.println("From " +from + "to "+ to+ "message"+msg);

	}

	@Override
	public void systemMessage(String msg) {
		System.out.println(msg);
	}

}
