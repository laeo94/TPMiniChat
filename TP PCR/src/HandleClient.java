import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

public class HandleClient implements Runnable, ChatProtocol, ChatModelEvents{
	private final Socket s;
	private ChatOutput cho;
	private ChatInput chi;
	private String name = "";
	private IChatLogger logger = null;
	private enum ClientState {
		ST_INIT, ST_NORMAL
	};
	private ClientState state = ClientState.ST_INIT;
	private boolean stop = false;
	public HandleClient(Socket s, IChatLogger logger) throws IOException {
		this.s = s;
		this.logger = logger;
	}

	public void run() {
		try (Socket s1 = s) {
			cho = new ChatOutput(s1.getOutputStream());
			chi = new ChatInput(s1.getInputStream(), this);
			chi.doRun();
		} catch (IOException ex) {
			if (!stop) {
				finish();
			}
		}
	}
	public synchronized void finish(){
		if (!stop) {
			stop =true;
			try {
				s.close();
			} catch (IOException ex) { ex.printStackTrace(); }
			if (name != null)
				ChatModel.unregisterUser(name);
			logger.clientDisconnected(s.toString(), name);
		}
	}
	@Override
	public void sendName(String name) {
		String newName = name;
		if (ChatModel.existUserName(newName)) {
			cho.sendNameBad();
		} else {
			if (state == ClientState.ST_INIT) {
				ChatModel.registerUser(newName, this);
				state = ClientState.ST_NORMAL;
			} else {
				ChatModel.renameUser(name, newName, this);
			}
			this.name = newName;
			cho.sendNameOK();
			logger.clientGotName(s.toString(), name);
		}

	}


	@Override
	public void sendMessage(String user, String msg) {
		if (state == ClientState.ST_INIT) return;
		ChatModel.sendChatMessage(name, msg);
		logger.publicChat(name, msg);
	}

	@Override
	public void sendAskUserList() {
		if (state == ClientState.ST_INIT) return;
		cho.sendUserList(ChatModel.getUserNames());
	}

	@Override
	public void sendUserList(Collection<String> ulist) {
		// TODO Auto-generated method stub
		ChatProtocol.super.sendUserList(ulist);
	}

	@Override
	public void sendPrivateMessage(String from, String to, String msg) { //Qu'on envoie
		if(state==ClientState.ST_INIT) return;
		ChatModel.sendPrivateChatMessage(from, to, msg);
		logger.privateChat(from, to, msg);
	}

	@Override
	public void sendQuit() {
		finish();
		cho.sendQuit();
	}

	@Override
	public void userListChanged() {
		cho.sendUserList(ChatModel.getUserNames());

	}

	@Override
	public void chatMessageSent(String from, String message) {
		if (from != name) {
			cho.sendMessage(from, message);
		}

	}

	@Override
	public void privateChatMessageSent(String from, String to, String message) { //Qu'on reçoit,chat modele informe
		if(to.equals(name)) cho.sendPrivateMessage(from,to,message);

	}
	@Override
	public void shutdownRequested() {
		stop=true;
		finish();
	}

	@Override
	public void roomListChanged() {
		if (state == ClientState.ST_INIT)
			return;
		cho.sendRoomList(ChatModel.getRooms());
		// TODO Auto-generated method stub

	}

	@Override
	public void roomUserListChanged(String room) {
		// TODO Auto-generated method stub
		if (state == ClientState.ST_INIT) return;
		cho.sendRoomUsersList(room,ChatModel.roomGetUserList(room));

	}

	public void sendCreateRoom(String room) {
		if (state == ClientState.ST_INIT) {
			return;
		}
		if(ChatModel.existRoom(room)) cho.sendRoomBad(room);
		else {
			ChatModel.addRoom(room, name);
			cho.sendRoomOK(room);
		}
	}

	public void sendRoomMessage(String room, String from, String message)
	{
		if (state == ClientState.ST_INIT) {
			cho.sendEror( "Not initialized…");
		}
		if (ChatModel.roomHasUser(room, name)) {
			ChatModel.roomSendChatMessage(room, name, message);
			cho.sendRoomMessage(room,name,message);
		} else {
			cho.sendEror( "Not in room…");
		}
	}



	@Override
	public void sendEnterRoom(String room) {
		if(ChatModel.existRoom(room)) {
			ChatModel.enterRoom(room,name);
			cho.sendEnterRoom(room);
		}else {
			cho.sendEror("Not existing");
		}
	}

	@Override
	public void sendLeaveRoom(String room) {
		if(ChatModel.existRoom(room)) {
			if(ChatModel.existUserName(name)) {
				ChatModel.leaveRoom(room,name);
				cho.sendLeaveRoom(room);
			}else {
				cho.sendEror("Not in room");
			}
		}else {
			cho.sendRoomBad(room);
		}
	}

	@Override
	public void roomChatMessageSent(String room, String from, String message) {
		if(ChatModel.roomHasUser(room,from)) {
			cho.sendRoomMessage(room,from,message);
		}

	}

	@Override
	public void sendDelete(String room) {
		if(state != ClientState.ST_INIT) {
			ChatModel.deleteRoom(room,name);
			cho.sendDelete(room);
		}else {
			cho.sendEror("Room not exist");
		}
	}

	@Override
	public void sendRoomUsersList(String room, Collection<String> ulist) {
		ChatModel.roomGetUserList(room);
		cho.sendRoomUsersList(room, ulist);
	}


	/*************FILE****************/

	public void sendFile(String to, String fName, File f) {
		ChatModel.sendFile(name, to, fName, f);
		f.delete();
	}
	public void fileSent(String from, String fName, File f) {
		cho.sendFile(from, fName, f); 
	}
	
	@Override
	public void sendProposeFile(String to, String fName) {
		ChatModel.sendProposeFile(name, to, fName);
	}
	public void proposeFileSent(String from, String fName) {
		cho.sendProposeFile(from, fName);
	}
	
	@Override
	public void sendAcceptFile(String to, String fName) {
		ChatModel.sendAcceptFile(name,to,fName);
	}
	public void acceptFileSent(String from, String fName) {
		cho.sendAcceptFile(from, fName);
	}
	@Override
	public void sendRefuseFile(String to, String fName) {
		ChatModel.sendRefuseFile(name,to,fName);
	}
	public void refuseFileSent(String from, String fName) {
		cho.sendRefuseFile(from, fName);
	}


}
