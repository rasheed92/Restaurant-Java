import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main {

	private JFrame frame;
	private JTextField txtName;
	private JTextField txtPrice;
	private JTextField txtQuantity;
	private JTextField txtTotal;
	DefaultTableModel tableModel= new DefaultTableModel();
	private  String data[][]={ }; 
	private DefaultListModel<String> list1 = new DefaultListModel<>();
	private JTable table;
	String URL="jdbc:mysql://localhost/main_project";
	private String sCurrentLine;;
	double t=0;
	private	List<String> ListName = new ArrayList<String>();
	private	List<String> ListQuantity = new ArrayList<String>();
	private	List<String> ListTotal = new ArrayList<String>();
	private JTextField txtOverAll;
	private static final String FILENAME = "d:\\test.txt";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
		RedText();
	}

	private void RedText() {
		BufferedReader br = null;
		FileReader fr = null;

		try {


			fr = new FileReader(FILENAME);
			br = new BufferedReader(fr);

		

			while ((sCurrentLine = br.readLine()) != null) {
				list1.addElement(sCurrentLine);
				System.out.println(sCurrentLine);
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 675, 755);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(193, 65, -151, 140);
		frame.getContentPane().add(scrollPane);
		JList<String> list = new JList<>(list1);  
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtQuantity.setText("");
				txtTotal.setText("");
				String xString=list.getSelectedValue();
				String[] s = xString.split(" # ");
				txtName.setText(s[0]);
				txtPrice.setText(s[1]);
				System.out.println(s[1]);
				
			}
		});
		
		
		list.setBorder(UIManager.getBorder("EditorPane.border"));
		
		list.setBounds(10, 48, 214, 597);
		
		frame.getContentPane().add(list);
		
		JLabel lblItemName = new JLabel("Item Name");
		lblItemName.setBounds(271, 65, 74, 14);
		frame.getContentPane().add(lblItemName);
		
		txtName = new JTextField();
		txtName.disable();
		txtName.setBounds(355, 65, 86, 20);
		frame.getContentPane().add(txtName);
		txtName.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Price");
		lblNewLabel.setBounds(271, 122, 46, 14);
		frame.getContentPane().add(lblNewLabel);
		
		txtPrice = new JTextField();
		txtPrice.setHorizontalAlignment(SwingConstants.LEFT);
		txtPrice.setBounds(355, 119, 86, 20);
		txtPrice.disable();
		frame.getContentPane().add(txtPrice);
		txtPrice.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Quantity");
		lblNewLabel_1.setBounds(271, 181, 60, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		txtQuantity = new JTextField();
		txtQuantity.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String Quantity=txtQuantity.getText();
				double QuantityDouble = Double.parseDouble(Quantity);
				txtQuantity.setText(Quantity);
				System.out.println(QuantityDouble);
				String price=txtPrice.getText();
				double priceDouble = Double.parseDouble(price);
				double Total=priceDouble*QuantityDouble;
				String total2 = Double.toString(Total);
				if (Quantity !=" "&& !Quantity.isEmpty()) {
					txtTotal.setText(total2);	
				}
				
			}
		});

		txtQuantity.setHorizontalAlignment(SwingConstants.LEFT);
		txtQuantity.setBounds(355, 178, 86, 20);
		frame.getContentPane().add(txtQuantity);
		txtQuantity.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Total Price");
		lblNewLabel_2.setBounds(271, 238, 74, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		txtTotal = new JTextField();
		txtTotal.setBounds(355, 238, 86, 20);
		txtTotal.disable();
		frame.getContentPane().add(txtTotal);
		txtTotal.setColumns(10);
		
		JButton btnAddToCart = new JButton("Add To Cart");
		btnAddToCart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String name=txtName.getText();
				String Quantity	=txtQuantity.getText();
				String Total=txtTotal.getText();
				Double foo = Double.parseDouble(Total);
		    	   String rowData[]= {name,Quantity,Total};
		    	   t=t+foo;
		    	   System.out.println(t);
		    	   tableModel.addRow(rowData);
		   		String total2 = Double.toString(t);
				txtOverAll.setText(total2);
				ListName.add(name);
				ListQuantity.add(Quantity);
				ListTotal.add(Total);
				System.out.println(ListQuantity);
			}
		});
		btnAddToCart.setBounds(326, 314, 116, 23);
		frame.getContentPane().add(btnAddToCart);
   

		table = new JTable();
		tableModel.addColumn("name");
		tableModel.addColumn("Quantity");
		tableModel.addColumn("Total");
		table.setModel(tableModel);
		table.setBounds(234, 390, 394, 251);
		frame.getContentPane().add(table);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(664, 466, -603, 157);
		frame.getContentPane().add(scrollPane_1);
		
		JButton btnNewButton = new JButton("Send To Server");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String[] stringArrayName = ListName.toArray(new String[0]);
				String[] stringArrayQuantity = ListQuantity.toArray(new String[0]);
				String[] stringArrayPrice = ListTotal.toArray(new String[0]);
				System.out.println(stringArrayName[0]);
				for (int i = 0; i < ListName.size(); i++) {
					
				
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection con=DriverManager.getConnection(URL, "root", "root");
	
					String query="INSERT INTO orders (Name,Quantity,price) Values (?,?,?)";
					PreparedStatement stm=con.prepareStatement(query);
					stm.setString(1, stringArrayName[i]);
					stm.setString(2, stringArrayQuantity[i]);
					stm.setString(3, stringArrayPrice[i]);
					int nb=stm.executeUpdate();
					if(nb==1) {
						JOptionPane.showMessageDialog(frame, "New Record is Inserted For Item "+stringArrayName[i]);
					
						
						}
					else {
						JOptionPane.showMessageDialog(frame, "ERROR during Insertion");
					}
				} catch (ClassNotFoundException | SQLException  e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}

			}
		});
		btnNewButton.setBounds(234, 686, 125, 23);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_3 = new JLabel("Total Price Cart");
		lblNewLabel_3.setBounds(397, 690, 107, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		txtOverAll = new JTextField();
		txtOverAll.setBounds(499, 687, 86, 20);
		txtOverAll.disable();
		frame.getContentPane().add(txtOverAll);

		txtOverAll.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(234, 364, 415, 2);
		frame.getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(223, 652, 436, 2);
		frame.getContentPane().add(separator_1);
		
		JLabel lblLl = new JLabel("L.L");
		lblLl.setBounds(595, 690, 46, 14);
		frame.getContentPane().add(lblLl);
		
		JLabel label = new JLabel("L.L");
		label.setBounds(451, 241, 46, 14);
		frame.getContentPane().add(label);
	}
}
