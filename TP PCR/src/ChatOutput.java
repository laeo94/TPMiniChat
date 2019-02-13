import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collection;



public class ChatOutput implements ChatProtocol{
	PrintWriter os;
	OutputStream oso;

	public ChatOutput(OutputStream out) throws IOException{
		this.os=new PrintWriter(out,true);
		this.oso=out;
	}

	public synchronized void sendName(String name) {
		os.println("NAME");
		os.println(name);
	}

	public synchronized void sendMessage(String user, String msg) {
		os.println("MESSAGE");
		os.println(user);
		os.println(msg); 
	}

	public synchronized void sendPrivateMessage(String from, String to, String msg) {
		os.println("PRIVATE MESSAGE");
		os.println(from);
		os.println(to);
		os.println(msg);
	}


	@Override
	public void sendNameOK() {
		os.println("NAME OK");
	}


	@Override
	public void sendNameBad() {
		os.println("NAME BAD");
	}


	@Override
	public void sendAskUserList() {
		os.println("AULIST");
	}


	@Override
	public void sendUserList(Collection<String> ulist) {
		os.println("ULIST");
		ulist.forEach(os::println);
		os.println(".");
	}


	@Override
	public void sendQuit() {
		os.println("QUIT");
	}

	@Override
	public void sendCreateRoom(String room) {
		os.println("CREATE ROOM");
		os.println(room);
	}

	@Override
	public void sendRoomOK(String room) {
		os.println("ROOM OK");
		os.println(room);
	}

	@Override
	public void sendRoomBad(String room) {
		os.println("ROOM BAD");
		os.println(room);
	}

	@Override
	public void sendDelete(String room) {
		os.println("DELETE ROOM");
		os.println(room);
	}

	@Override
	public void sendRoomList(Collection<String> rlist) {
		os.println("RLIST");
		rlist.forEach(os::println);
		os.println(".");

	}

	@Override
	public void sendRoomMessage(String room, String user, String msg) {
		os.println("ROOM MESSAGE");
		os.println(room);
		os.println(user);
		os.println(msg);
	}

	@Override
	public void sendEnterRoom(String room) {
		os.println("ENTER ROOM");
		os.print(room);
	}

	@Override
	public void sendLeaveRoom(String room) {
		os.println("LEAVE ROOM");
		os.println(room);
	}

	@Override
	public void sendAskRoomList(String room) {
		os.println("ARLIST");
		os.print(room);
	}

	@Override
	public void sendRoomUsersList(String room, Collection<String> ulist) {
		os.println("RULIST");
		os.println(room);
		ulist.forEach(os::println);
		os.println(".");
	}

	@Override
	public void sendEror(String msg) {
		os.println("ERR");
		os.println(msg);
	}

	@Override
	public synchronized void sendFile(String to, String fName, File f) {
		System.out.println("aaaa");
		try (FileInputStream fi = new FileInputStream(f)) {
			os.println("SEND FILE");
			os.println(to);
			os.println(fName);
			os.println(f.length());
			os.flush();
			byte buf[] = new byte[8192];
			int len = 0;
			while ((len = fi.read(buf)) != -1) {
				oso.write(buf,0, len);
			}
		} catch (IOException ex) {

		}
	}

	@Override
	public void sendProposeFile(String to, String fName) {
		os.println("PROPOSE FILE");
		os.println(to);
		os.println(fName);
	}

	@Override
	public void sendAcceptFile(String to, String fName) {
		os.println("ACCEPT FILE");
		os.println(to);
		os.println(fName);
	}

	@Override
	public void sendRefuseFile(String to, String fName) {
		os.println("REFUSE FILE");
		os.println(to);
		os.println(fName);
	}
	

}
