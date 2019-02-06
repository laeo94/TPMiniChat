import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class RoomModel {
	String master;
	String roomName;
	final private Map<String, RoomEvents> roomObserverList = new TreeMap<>(); //liste des user
	public RoomModel(String roomName, String master, RoomEvents handler) {
		this.master=master;
		this.roomName=roomName;
		roomObserverList.put(roomName,handler);
	}

	private void notifyUserListChanged() {
		roomObserverList.values().forEach(c -> c.roomUserListChanged(roomName));
	}
	public synchronized void registerUser(String user, RoomEvents handler) {
		roomObserverList.put(user, handler);
		notifyUserListChanged();
	}
	public synchronized void unregisterUser(String user) {
		if (user.equals(master)) {
			master = null;
		}
		roomObserverList.remove(user);
		notifyUserListChanged();
	}
	public synchronized void chatMessage(String from, String message) {
		roomObserverList.values().forEach(c -> c.roomChatMessageSent(roomName,from, message));
	}
	public synchronized boolean canDelete(String name) {
		if(master ==null) return true;
		return name.equals(master);
	}
	public synchronized Collection<String> userList(){
		return roomObserverList.keySet();
	}
	synchronized int userCount() {
		return roomObserverList.size();
	}
	synchronized boolean hasUser(String user) {
		if(roomObserverList.containsKey(user)) return true;
		return false;
	}
	synchronized void userRenamed(String name, String newName) {
		if(hasUser(name) && newName!= "") {
			roomObserverList.put(newName,roomObserverList.get(name));
			roomObserverList.remove(name);
		}
	}

}
