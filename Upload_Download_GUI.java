package ServerClient;
//Conor Donohue 13404068
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
public class Upload_Download_GUI extends JFrame{
	private JLabel heading,filedirectory;
	private JRadioButton upload,download;
	private JTextField filename;
	private ButtonGroup radioGroup;
	private GridBagLayout layout;
	private GridBagConstraints constraints;
	private Container container;
	private String upd,filenametext ;
	private JButton submit;
	//create your constructor and add all the components to it
	public Upload_Download_GUI(){
		super("Connecting to Server");
		 container = getContentPane();
		 layout = new GridBagLayout();
		 container.setLayout( layout );
		 constraints = new GridBagConstraints();
		 //set your layout manager
		 heading = new JLabel("Choose whether To upload or Download a File");
		 addComponent(heading,1,0,1,1);//add instructions
		 filedirectory = new JLabel("Type out the filename and please include file extension: ");
		 filename = new JTextField(100);//add space for the file name to be typed
		 constraints.fill = GridBagConstraints.BOTH;
		 addComponent(filedirectory,4,0,1,1);
		 addComponent(filename,5,0,1,1);
		 upload = new JRadioButton("Upload");
		 download = new JRadioButton("Download");
		 radioGroup = new ButtonGroup();
		 radioGroup.add(upload);//use radio buttons to select whether to upload or download a file
		 constraints.fill = GridBagConstraints.BOTH;
		 addComponent(upload,2,0,1,1);
		 addComponent(download,2,1,1,1);
		 radioGroup.add(download);
		 RadioButtonHandlers rbh = new RadioButtonHandlers();
		 upload.addActionListener(rbh);
		 upload.setActionCommand("Upload");
		 download.addActionListener(rbh);
		 download.setActionCommand("Download");//add action listeners to respond when they are clicked
		 submit = new JButton("Submit");
		 addComponent(submit,6,0,1,1);
		 ButtonHandlers handler = new ButtonHandlers();
		 submit.addActionListener(handler);
		 setSize(800,200);
		 setVisible(true);
	}
	private void addComponent(Component component, int row, int column, int width, int height){
		//create a specific function which can be re used for every component and specifies 
		// exactly where you want it placed and the size of which you want them
		constraints.gridx = column;
		constraints.gridy = row;
		constraints.gridwidth = width;
		constraints.gridheight = height;
		 layout.setConstraints(component, constraints);
		 container.add(component);
	}
	private class ButtonHandlers implements ActionListener{
		public void actionPerformed(ActionEvent event){
			filenametext = filename.getText();			
			JOptionPane.showMessageDialog(null,"File name is "+ filenametext);// It informs you of which you have chosen
			terminate();//once all the info has been submitted you have no further need for the gui so it can be terminated
		}
	}
	private class RadioButtonHandlers implements ActionListener{
		public void actionPerformed(ActionEvent event){
			upd = event.getActionCommand();//find out the value chosen when the event occured
		}
	}
	public static void main( String args[] )
	 {
		 Upload_Download_GUI application = new Upload_Download_GUI();
		
		 application.setDefaultCloseOperation(
		 JFrame.EXIT_ON_CLOSE );
	 }
	//create some getters to help pass the file details around
	public String getFileName(){
		String filenames = filenametext;
		return filenames;
	}
	public String UploadOrDownload(){
		String updown = upd;
		return updown;
	}
	//stop the gui once completed
	public void terminate(){
		this.dispose();
	}
}
