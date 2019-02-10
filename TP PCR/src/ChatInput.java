import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ChatInput {
	ChatProtocol handler;
	InputStream in;
	boolean stop =false;
	public ChatInput(InputStream in, ChatProtocol handler) throws IOException{
		this.in=in;
		this.handler=handler;
	}  

	public void doRun() throws IOException {
		String strMsg, strName, strTo, strRoom, fName;
		ArrayList <String> userList, roomList;

		try (LineBufferedInputStream is = new LineBufferedInputStream(in)) {
			while (!stop) {
				String line = is.readLine();
				if(line == null) return;
				switch (line) {
				case "NAME":
					strName = is.readLine();
					handler.sendName(strName);
					break;
				case "MESSAGE":
					strName = is.readLine();
					strMsg = is.readLine();
					handler.sendMessage(strName, strMsg);
					break;
				case "ULIST":
					userList = new ArrayList<>();
					String x;
					while (!(x = is.readLine()).equals(".")) {
						userList.add(x);
					}
					handler.sendUserList(userList);
					break;
				case "AULIST":
					handler.sendAskUserList();
					break;
				case "NAME OK":
					handler.sendNameOK();
					break;
				case "NAME BAD":
					handler.sendNameBad();
					break;
				case "QUIT":
					handler.sendQuit();
					break;
				case "PRIVATE MESSAGE": 
					strName = is.readLine();
					strTo= is.readLine();
					strMsg = is.readLine();
					handler.sendPrivateMessage(strName,strTo,strMsg);
					break;
				case "CREATE ROOM":
					strRoom= is.readLine();
					handler.sendCreateRoom(strRoom);
					break;
				case "ROOM OK":
					strRoom= is.readLine();
					handler.sendRoomOK(strRoom);
					break;
				case "ROOM BAD":
					strRoom= is.readLine();
					handler.sendRoomBad(strRoom);
					break;
				case "DELETE ROOM":
					strRoom= is.readLine();
					handler.sendDelete(strRoom);
					break;
				case "RLIST":
					roomList = new ArrayList<>();
					String y;
					while (!(y = is.readLine()).equals(".")) {
						roomList.add(y);
					}
					handler.sendUserList(roomList);
					break;
				case "ROOM MESSAGE":
					strRoom = is.readLine();
					strName =is.readLine();
					strMsg= is.readLine();
					handler.sendRoomMessage(strRoom, strName,strMsg);
					break;
				case "ENTER ROOM":
					strRoom=is.readLine();
					handler.sendEnterRoom(strRoom);
					break;
				case "LEAVE ROOM":
					strRoom=is.readLine();
					handler.sendLeaveRoom(strRoom);
					break;
				case "ARULIST":
					strRoom=is.readLine();
					handler.sendAskRoomList(strRoom);
					break;
				case "RULIST":
					strRoom=is.readLine();
					userList = new ArrayList<>();
					String z;
					while (!(z = is.readLine()).equals(".")) {
						userList.add(z);
					}
					handler.sendRoomUsersList(strRoom, userList);
					break;
				case "ERR":
					strMsg=is.readLine();
					handler.sendEror(strMsg);
					break;
				case "SEND FILE":
					strName = is.readLine();
					fName= is.readLine();
					int FSize = Integer.parseInt(is.readLine());
					File f = File.createTempFile(fName, "txt");
					try (FileOutputStream fo = new FileOutputStream(f)) {
						byte buf[] = new byte[8192];
						int len = 0;
						int reste = FSize;
						while (reste > 0 && len != -1) {
							int toRead = buf.length;
							if (toRead > reste) toRead = reste;
							len = is.read(buf, 0, toRead);
							fo.write(buf, 0, len);
							reste -= len;
						}
					}
					handler.sendFile(strName, fName, f);
					break;
				case"PROPOSE FILE":
					strName= is.readLine();
					fName= is.readLine();
					handler.sendProposeFile(strName, fName);
					break;
				case "ACCEPT FILE":
					strName= is.readLine();
					fName= is.readLine();
					handler.sendAcceptFile(strName, fName);
					break;
				
				case "REFUSE FILE":
					strName= is.readLine();
					fName= is.readLine();
					handler.sendRefuseFile(strName, fName);
					break;
				default:
					throw new ChatProtocolException("Invalid input");
				}
			}
		}
	}
}

