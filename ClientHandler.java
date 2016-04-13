package ServerClient;
//Conor Donohue 13404068
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ClientHandler extends Thread {
	private Socket connection;
	private Server server;
	public ClientHandler(Socket s,Server serve){
		connection=s;
		server=serve;
	}
	//initialise your variables and create a constructor
	public void run(){
		try {
			System.out.println("Client is connected, True of False: "+connection.isConnected());
			//Check to see if the client is connected
			Upload_Download_GUI gud = new Upload_Download_GUI();//Create a GUI to decide whether to upload or download
			while(gud.UploadOrDownload()==null || gud.getFileName()==null){
				Thread.sleep(1000);
				//use this while loop to ensure that the thread doesn't progress any further until all information is entered from the gui
			}
			//read and write to the sockets using the streams as seen in the notes
			System.out.println(gud.UploadOrDownload());
			if(gud.UploadOrDownload() == "Upload"){
				DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			    // Open the stream.
				FileInputStream fis = new FileInputStream(gud.getFileName());
				BufferedInputStream in = new BufferedInputStream(fis);
			    
			    // To read chars from it, use new InputStream Reader
			    // and specify the encoding.
				byte[] bs = new byte[(int) fis.getChannel().size()];
		        // count the available bytes from the input stream
				fis.read(bs);//put the data into the byte stream
				//System.out.println(fis.getChannel().size());
				out.writeInt(bs.length);//set the size of the transfer and sent the data through the socket
		        out.write(bs);
			    server.upload(gud.getFileName());//allow the server to finish the upload
				in.close();
		        out.close();
		        //close all i/o streams
			}
			else{
				//create your i/o streams
				DataInputStream in = new DataInputStream(connection.getInputStream());
				BufferedOutputStream outf = new BufferedOutputStream(new FileOutputStream(gud.getFileName()));
				server.download(gud.getFileName(),this);//get the server to initialise the download
				
		        // count the available bytes from the input stream
		        int count = in.readInt();
		        byte[] bs = new byte[count];
		        in.readFully(bs,0,count);//read in from the socket
		       // System.out.println(count + " "+ bs.length);
		        outf.write(bs);		         
				outf.close();
				in.close();
				//close the i/o streams
				}
				
			} catch (IOException e) {
				//if the file doesn't exist let the user know and start the thread again
				JOptionPane.showMessageDialog(null, "There is no file of that name on record. Please try again.");
				this.run();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
	}
}
