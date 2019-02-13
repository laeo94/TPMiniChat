import java.io.File;
import java.util.Collection;

public interface ChatProtocol {
	default void sendName(String name) {};
	default void sendNameOK() {};
	default void sendNameBad() {};
	default void sendMessage(String user, String msg) {};
	default void sendAskUserList() {};
	default void sendUserList(Collection <String> ulist) {};
	default void sendPrivateMessage(String from, String to,String msg) {};
	default void sendQuit() {};
	
	default void sendCreateRoom(String room) {};
	default void sendRoomOK( String room) {};
	default void sendRoomBad(String room) {};
	default void sendDelete(String room) {};
	default void sendRoomList(Collection <String> rlist) {};
	default void sendRoomMessage(String room,String user, String msg) {};
	default void sendEnterRoom(String room) {};
	default void sendLeaveRoom(String room) {};
	default void sendAskRoomList(String room) {};
	default void sendRoomUsersList(String room,Collection <String> ulist) {};
	default void sendEror(String msg) {};
	 
	default void sendFile(String to, String fName, File f) {};
	default void sendProposeFile(String to, String fName) {};
	default void sendAcceptFile(String to, String fName) {};
	default void sendRefuseFile(String to, String fName) {};
}
