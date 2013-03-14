package rmiConnections;
import java.io.*;
import java.util.ArrayList;

/**
 * Class for the Write and Read from Files
 */
@SuppressWarnings("serial")
public class Manage_Data_Files implements Serializable{
	
	private FileInputStream InStream;
	private FileOutputStream OutStream;
	private ObjectInputStream ObjIn; 
	private ObjectOutputStream ObjOut;
	private String nomeFicheiro="Users_Data.dat";
	
	/**
	 * Class constructor
	 */
	Manage_Data_Files() throws IOException{
		File FILE = new File(nomeFicheiro);
		if (FILE.exists()) {
			System.out.print("\nFicheiro existe");
		}else{
			System.out.print("\nFicheiro nao existe");
			try{
				FILE.createNewFile();
			}
			catch (Exception e){
			  System.out.println(e);
			}
		}
		System.out.println("\nPath do ficheiro: "+FILE.getCanonicalPath());
	}
	
	/**
	 * Method Open File for Read
	 */
	public void abreLeitura() throws IOException {
		InStream = new FileInputStream(new File(nomeFicheiro));
		ObjIn = new ObjectInputStream(InStream);
	}
	
	/**
	 * Method Open File for Write
	 */
	public void abreEscrita() throws IOException{
		OutStream = new FileOutputStream(new File(nomeFicheiro));
		ObjOut = new ObjectOutputStream(OutStream);
	}
	
	/**
	 * Method Read Data from File
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Object_User> lerObjectoArray() throws IOException, ClassNotFoundException{
		return ((ArrayList<Object_User>)ObjIn.readObject());
	}
	
	/**
	 * Method Write Data on File
	 */
	public void escreveObjectoArray(ArrayList<Object_User> o) throws IOException{
		ObjOut.writeObject(o);
	}
	
	/**
	 * Method Close File in Read Mode
	 */
	public void fecharLeitura() throws IOException{
		ObjIn.close();
	}
	
	/**
	 * Method Close File in Write Mode
	 */
	public void fecharEscrita() throws IOException{
		ObjOut.close();
	}
}
