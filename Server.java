package ServerClient;
//Conor Donohue 13404068
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class Server {
	private ServerSocket servsock;
	private static Socket Clientconnection;
	//create your constructor
	public Server() throws UnknownHostException, IOException{
		servsock = new ServerSocket(4242);//connect to port 4242
		ClientHandler handler = new ClientHandler(new Socket("127.0.0.1",4242),this);
		Clientconnection = servsock.accept();//create a client and complete the connection
		System.out.println("Server is connected, True of False: "+Clientconnection.isConnected());//check to see if server is connected
		handler.start();// start the thread of the client to connect
		//Server is initialised with one client to complete the connection
	}
	public static void main(String args[]) throws UnknownHostException, IOException{
		Server s = new Server();
		/*ClientHandler handled = new ClientHandler(new Socket("127.0.0.1",4242),s);
		handled.start();*/
		
		//as many more clients as wished can be added to the same server by including the server as an argument
	}
	
	public synchronized void upload(String n) throws IOException{//pass the file name as an argument
		DataInputStream in = new DataInputStream(Clientconnection.getInputStream());//use the socket to read in what has been passed through it
		BufferedOutputStream outf = new BufferedOutputStream(new FileOutputStream("C:\\Users\\Conor\\Documents\\"+n));
		//create your i/o streams
        // count the available bytes form the input stream
		int count = in.readInt();
        byte[] bs = new byte[count];
        in.readFully(bs,0,count);//read the data into the byte array
        outf.write(bs);		   //write to the file
		outf.close();
		in.close();
		//close your i/o streams
	}
	public synchronized void download(String n,Thread t) {
		DataOutputStream out;
		try {
			out = new DataOutputStream(Clientconnection.getOutputStream());
		 
	    // Open the stream.
		FileInputStream fis =	new FileInputStream("C:\\Users\\Conor\\Documents\\"+n);
		BufferedInputStream in = new BufferedInputStream(fis);
		byte[] bs = new byte[(int) fis.getChannel().size()];// count the available bytes from the input stream and create a byte array
		fis.read(bs);
    //    System.out.println(fis.getChannel().size());
		out.writeInt(bs.length);
        out.write(bs);
      //  System.out.println(fis.getChannel().size());
		out.flush();//flush to make sure everything has been sent and then close the i.o streams
	    in.close();
	    out.close();
		}
	    catch (IOException e) {
	    	JOptionPane.showMessageDialog(null, "There is no file of that name on record. Please try again.");
			t.run();
			//if the file doesn't exist let the user know and start the thread again
			}
	}
}
