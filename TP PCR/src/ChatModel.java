import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeMap;

public class ChatModel {
	private static final TreeMap<String, ChatModelEvents> clientList = new TreeMap<>();
	private static final TreeMap<String, RoomModel> roomList = new TreeMap<>();
	static TreeMap <String,File> sendfile = new TreeMap <String,File> ();
	static  ArrayList <String> acceptfile = new ArrayList <String>();
	public static synchronized boolean registerUser(String name,HandleClient client){
		if (!existUserName(name) && !name.equals("")) {
			System.out.println("registerUser");
			clientList.put(name, client);
			notifyNewName();
			return true;
		}
		return false;
	}
	public static synchronized void unregisterUser(String name) {
		if (existUserName(name)) {
			clientList.remove(name);
			notifyNewName();
		}
	}

	public static synchronized boolean renameUser(String oldname, String newname,HandleClient client) {
		if(existUserName(oldname)) {
			if(!existUserName(newname) && !newname.equals("")) {
				clientList.put(newname,client);
				clientList.remove(oldname);
				notifyNewName();
				roomList.values().forEach(r->r.userRenamed(oldname, newname));
				return true;
			}

			return false;
		}
		return false;

	}
	public static synchronized boolean existUserName(String name){
		return clientList.containsKey(name);
	}
	public static synchronized Set<String> getUserNames() {
		return clientList.keySet();
	}
	public static void sendChatMessage(String from, String msg) {
		clientList.values().forEach(c->c.chatMessageSent(from, msg));

	}

	public static void sendPrivateChatMessage(String from, String to , String msg) {
		clientList.values().forEach(c->c.privateChatMessageSent(from, to, msg));


	}
	private static void notifyNewName() {
		clientList.values().forEach(ChatModelEvents::userListChanged);
	}
	public static void clearAll() {
		clientList.values().forEach(c->c.shutdownRequested());
		clientList.clear();
	}

	public static synchronized boolean addRoom(String room, String master)
	{
		if (!existRoom(room) && existUserName(master)) {
			roomList.put(room, new RoomModel(room, master,
					clientList.get(master)));
			notifyChangeRooms();
			return true;
		}
		return false;
	}
	public static synchronized boolean existRoom (String name) {
		return roomList.containsKey(name);
	}
	public static synchronized void deleteRoom(String room, String user) {
		if (existRoom(room) && roomList.get(room).canDelete(user)) {
			roomList.remove(room);
			notifyChangeRooms();
		}
	}
	private static synchronized void notifyChangeRooms() {
		clientList.values().forEach(ChatModelEvents::roomListChanged);
	}
	
	public static synchronized void enterRoom(String room, String user) {
		if (!existUserName(user) || !existRoom(room)) {
			return;
		}
		roomList.get(room).registerUser(user, clientList.get(user));
	}
	public static synchronized void leaveRoom(String room, String user) {
		if (!existUserName(user) || !existRoom(room)) {
			return;
		}
		roomList.get(room).unregisterUser(user);
		if (roomList.get(room).userCount() == 0) {
			roomList.remove(room);
		}
	}
	public static synchronized void roomSendChatMessage(String room, String from,String message) {
		if(existRoom(room)) roomList.get(room).chatMessage(from, message);
	}
	public static synchronized Collection<String> getRooms(){
		return roomList.keySet();
	}
	public static synchronized Collection<String> roomGetUserList(String room){
		if(existRoom(room)) return roomList.get(room).userList();
		return null;
	}
	public static synchronized boolean roomHasUser(String room, String user) {
		if(existRoom(room)) return roomList.get(room).hasUser(user);
		return false;
	}
	/*****************FILE****************/
	
	public static void sendFile(String from, String to, String fName, File f) {
		if(existUserName(to) && existUserName(from)) {
		clientList.get(to).fileSent(from, fName, f);
		}
	} 
	 
	public static void sendProposeFile(String from,String to, String fName) {
		if(existUserName(to) && existUserName(from)) {
			clientList.get(to).proposeFileSent(from, fName);
		}
	}
	
	public static void sendAcceptFile(String from,String to, String fName) {
		if(existUserName(to) && existUserName(from)) {
			clientList.get(to).acceptFileSent(to, fName);
		} 
	}
	public static void sendRefuseFile(String from,String to , String fName) {
		if(existUserName(to) && existUserName(from)) {
			clientList.get(to).refuseFileSent(to, fName);
		}
	} 
	
}
