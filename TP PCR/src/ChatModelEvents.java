import java.io.File;

public interface ChatModelEvents extends ChatEvents, RoomEvents {
	public void userListChanged();
	public void chatMessageSent(String from, String message);
	public void privateChatMessageSent(String from, String to, String message);
	public void shutdownRequested();
	public void roomListChanged();
	public void fileSent (String from, String fName,File f);
	public void proposeFileSent(String to, String fName);
	public void acceptFileSent(String to, String fName);
	public void refuseFileSent(String to , String fName);   
} 