package rmiConnections;

import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.*;
import java.io.*;

/**
 * Class of the RMI Server
 */
public class RMI_Server extends UnicastRemoteObject implements Interface_Server_Methods{
	private static final long serialVersionUID = 1L;
	
	private static ArrayList<Object_User> List_Users=new ArrayList<Object_User>();
	private static ArrayList<Object_User> List_User_Online=new ArrayList<Object_User>();

	/**
	 * Class constuctor
	 */
	public RMI_Server() throws RemoteException {
		super();
	}
	
	/**
	 * Method Read Users Data File
	 */
	public static void Read_User_List_on_File() throws IOException{
		
		Manage_Data_Files FILE=new Manage_Data_Files();
		FILE.abreLeitura();
		try{
			List_Users=FILE.lerObjectoArray();
		}catch (Exception e){
			System.out.println(e);
		}
		FILE.fecharLeitura();
	}
	
	/**
	 * Method Write Users Data File
	 */
	private void Save_Users_List_on_File(ArrayList<Object_User> lista) throws IOException{
    	
		Manage_Data_Files FILE=new Manage_Data_Files();
		FILE.abreEscrita();		
		try{
			FILE.escreveObjectoArray(lista);
		}catch (Exception e){
			System.out.println("ERROR(guardarObject_UseresFicheiro):"+e.getMessage());
		}
		FILE.fecharEscrita();
		System.out.println("SYSTEM: lista de Object_Useres guardada no ficheiro.");
    }
	
	/**
	 * Method get List of Users On-Line
	 */
	public ArrayList<Object_User> Users_OnLine() throws RemoteException{
		System.out.println("Retornou lista de Object_Useres online");
		return List_User_Online;
	}
	
	/**
	 * Method for Print List of Users
	 */
	private static void Print_List(){
		System.out.println("\n\n .: LISTA DE List of Users :.");
		for (int i=0; i<List_Users.size(); i++){
			List_Users.get(i).printCliente();
		}
	}
	
	/**
	 * Check if the User exists
	 * */
	public boolean Check_User(String user) throws RemoteException {
		boolean verif=false;
		
		for(int i=0;i<List_Users.size();i++){
			if(user.equals(List_Users.get(i).getNome()) == true){
				verif=true;
				break;
			}
		}
		return verif;
	}

	/**
	 * Register a new user
	 * */
	public boolean Register_User(Object_User user) throws RemoteException {
		System.out.println(".................");
		boolean verif=true;
		List_Users.add(user);
		System.out.print("Object_User "+user.getNome()+" Registado");
		try{
			Save_Users_List_on_File(List_Users);
		}catch (Exception e){
			System.out.println(e);
			verif=false;
		}
		return verif;
	}

	/**
	 * check if the User Password is correct
	 * */
	public Object_User Check_Password(String nome, String pass) throws RemoteException {
		Object_User obj=null;
		for(int i=0;i<List_Users.size();i++){
			obj=List_Users.get(i);
			
			if (nome.equals(obj.getNome()) == true && pass.equals(obj.getPassword()) == true){
				System.out.println("\nPassword confirmada do Object_User: "+nome);
				break;
			}
		}
		return obj;
	}

	/**
	 * Edit the User Data
	 * */
	public boolean Edit_User(Object_User user, String nome) throws RemoteException {
		boolean verif=true;
		Object_User ut=null;
		for (int i=0; i<List_Users.size(); i++){
			String nomeUt=List_Users.get(i).getNome();
			if (nome.equals(nomeUt) == true){
				ut=List_Users.get(i);
				break;
			}
		}
		List_Users.remove(ut);
		
		for (int i=0; i<List_User_Online.size(); i++){
			String nomeUt=List_User_Online.get(i).getNome();
			if (nome.equals(nomeUt) == true){
				ut=List_User_Online.get(i);
				break;
			}
		}
		List_User_Online.remove(ut);
		
		List_Users.add(user);
		List_User_Online.add(user);
		System.out.println("\n->dados alterados!! ");
		Print_List();
		
		try {
			Save_Users_List_on_File(List_Users);
		} catch (IOException e) {
			System.out.println("\nERROR(edit_userIO): "+e.getMessage());
			verif=false;
		}
		return verif;
	}

	/**
	 * Add New User
	 * */
	public void Add_User(Object_User obj) throws RemoteException {
		List_User_Online.add(obj);
		System.out.println("Object_User "+obj.getNome()+" ficou online");
	}
	
	/**
	 * User Exit the Server
	 * */
	public void Exit(Object_User obj){
		int pos=0;
		for(int i=0; i<List_User_Online.size(); i++){
			Object_User ut=List_User_Online.get(i);
			if (obj.getNome().equals(ut.getNome()) == true){
				pos=i;
				break;
			}
		}
		List_User_Online.remove(pos);
		
		//imprimir os Object_Useres que ainda ficaram ligados
		System.out.println("\n- Object_User: "+obj.getNome()+" saiu da aplicacao.");
		if (List_User_Online.size() > 0){
			System.out.println("\n\n .: LISTA DE Object_UserES ONLINE :.");
			for (int i=0; i<List_User_Online.size(); i++){
				List_User_Online.get(i).printCliente();
			}
		}else{
			System.out.println("\n- Nenhum Object_User esta ligado.");
		}
	}
	
	/**
	 * User Send a Msg
	 * */
	public boolean Send_Msg(String nome, String msg, String deQuem){
		boolean verif=false;
		System.out.println("MSG para "+nome+": "+msg);
		if (nome.equals("all") != true){
			for (int i=0; i<List_User_Online.size(); i++){
				Object_User user=List_User_Online.get(i);
				if (user.getNome().equals(nome) == true){
					Interface_Client_Methods met = user.getMetodos();
					try {
						met.Receive_Msg(msg, deQuem);
						verif=true;
						break;
					} catch (RemoteException e) {
						System.out.println("\nERRO: remote, enviar msg"+e.getMessage());
					}
				}
			}
		}else{
			for (int i=0; i<List_User_Online.size(); i++){
				Object_User ut=List_User_Online.get(i);
				Interface_Client_Methods met=ut.getMetodos();
				try {
					met.Receive_Msg(msg, deQuem);
					verif=true;
				} catch(RemoteException e){
					System.out.println("\nERRO: remote, enviar msgALL"+e.getMessage());
				}
			}
		}
		return verif;
	}
	
	/**
	 * Main Method for the RMI Server
	 * */
	public static void main(String args[]) {
		
		/* This might be necessary if you ever need to download classes:
		System.getProperties().put("java.security.policy", "policy.all");
		System.setSecurityManager(new RMISecurityManager());
		*/
		
		try{
			Read_User_List_on_File();
		}catch (Exception e){
			System.out.println(e);
		}
		Print_List();
		
		try {
			RMI_Server s = new RMI_Server();
			Registry r = LocateRegistry.createRegistry(7001);
			r.rebind("Discover_Valencia_Server", s);
			System.out.println("\n\nServer ready.");
			
		} catch (RemoteException re) {
			System.out.println("Exception in HelloImpl.main: " + re);
		}
	}
}
