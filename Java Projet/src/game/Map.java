package game;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import block.GoldMine;
import block.Home;
import block.Mud;
import block.Teleporter;
import block.Tree;
import unit.Unit;

public class Map {
	private int mapW;
	private int mapH;
	private char[][] carte;
	private ArrayList<GoldMine> listGoldMine;
	private ArrayList <Home> listHome1;
	private ArrayList<Home> listHome2;
	private ArrayList <Teleporter> listTeleporter;
	private ArrayList <Tree> listTree;
	private ArrayList <Mud> listMud;

	public Map() { 
		this.mapW =45;
		this.mapH =23;
		carte =new char[mapW][mapH];
		listGoldMine = new ArrayList<>();
		listHome1 = new ArrayList<>();
		listHome2 = new ArrayList<>();
		listTeleporter = new ArrayList<>();
		listMud = new ArrayList<>();
		listTree = new ArrayList<> ();
		createMap("carte.txt");
	}

	//Load the file map and create the map
	public void createMap(String name) { 
		File f = new File(name);
		FileReader fr;
		long taille = f.length();
		String buffer = "";
		try {
			fr = new FileReader(f);
			try {

				for (int i = 0; i < taille; i++) {
					buffer += Character.toString((char) fr.read()); //read lit en int donc transforme en char puis en string

				}
				String[] tab = buffer.split("\n");
				for (int i = 0; i <mapH; i++) { // Constitute the map
					String tabi = tab[i];
					for (int j = 0; j < mapW; j++) {
						carte[j][i] = tabi.charAt(j);
					}
				}
				fr.close();
			} catch (IOException exception) {
				System.out.println("Erreur lors de la lecture : " + exception.getMessage());
			}

		} catch (FileNotFoundException exception) {
			System.out.println("Le fichier n'a pas ete trouve");
		}
		setListObstacle();
	}

	//set list of obstacles
	private void setListObstacle() {
		for (int i = 0; i <carte.length; i++) {
			for (int j = 0; j <carte[i].length; j++) {
				Pos p = new Pos(i,j);
				switch(carte[i][j]) {
				case 'g':GoldMine g = new GoldMine(p,randomRanger(1,4));listGoldMine.add(g); break;
				case 'a':Tree t = new Tree(p);listTree.add(t); break;
				case 'b':Mud m = new Mud(p);listMud.add(m); break;
				case 'L':Home h = new Home(p,'L'); listHome1.add(h); break;
				case 'N':Home h1 = new Home(p,'N'); listHome2.add(h1); break;
				case 't':Teleporter te = new Teleporter(p,posTeleout(p));listTeleporter.add(te); break;
				default: break;
				}
			}
		}
	}
	
	//GoldMine random of number of pieces
	private int randomRanger(int min, int max) {
		return (int) (Math.random() * (max - min) + min);
	}
	//Random definition for the teleporter out position
	private Pos posTeleout(Pos t) {
		if(t.getX()<=mapW/2 && t.getY()<=mapH/2) return new Pos(t.getX()+10,t.getY()+10);
		if(t.getX()<=mapW/2 && t.getY()>mapH/2) return new Pos(t.getX()+10,t.getY()-10);
		if(t.getX()>mapW/2 && t.getY()<=mapH/2) return new Pos(t.getX()-10,t.getY()+10);
		else return new Pos(t.getX()-10,t.getY()-10);
	}
	
	//Return if in the position p it is an obtacles or not
	public boolean isObstacle(Unit u,Pos p) {
		for(int i=0 ;i< listTree.size();i++) {
			if(listTree.get(i).getPos().equals(p)) {
				return listTree.get(i).isObstacle(u);
			}
		}
		for(int i=0 ;i< listHome1.size();i++) {
			if(listHome1.get(i).getPos().equals(p)) {
				return listHome1.get(i).isObstacle(u);
			}
		}
		for(int i=0 ;i< listHome2.size();i++) {
			if(listHome2.get(i).getPos().equals(p)) {
				return listHome2.get(i).isObstacle(u);
			}
		}
		for(int i=0 ;i< listGoldMine.size();i++) {
			if(listGoldMine.get(i).getPos().equals(p)) {
				if(u.canMine()) {
					if(u.getObjectif()!=null) {
						if(listGoldMine.get(i).getPos().equals(u.getObjectif())) {
							return false;
						}else {
							if(listGoldMine.get(i).getPos().equals(u.getPosActual())) return false;
							return true;
						}
					}
					return false;
				}
				return listGoldMine.get(i).isObstacle(u);
			}
		}
		for(int i=0 ;i< listTeleporter.size();i++) {
			if(listTeleporter.get(i).getPos().equals(p)) {
				return listTeleporter.get(i).isObstacle(u);
			}
		}
		for(int i=0 ;i< listMud.size();i++) {
			if( listMud.get(i).getPos().equals(p)) {
				return  listMud.get(i).isObstacle(u);
			}
		}

		return false;
	}

	/*------------------------------------------Getter && Setter-------------------------------------------------*/
	public int getMapH() {
		return mapH;
	}
	public int getMapW() {
		return mapW;
	}
	
	public ArrayList<GoldMine> getListGoldMine(){
		return listGoldMine;
	}
	public ArrayList<Home> getListHome1(){
		return listHome1;
	}
	public ArrayList<Home> getListHome2(){
		return listHome2;
	}
	public ArrayList<Teleporter> getListTeleporter() {
		return listTeleporter;
	}

	public ArrayList<Tree> getListTree() {
		return listTree;
	}

	public ArrayList<Mud> getListMud() {
		return listMud;
	}

}