import java.applet.Applet;
import java.awt.Label;
import java.awt.Button;
import java.awt.Scrollbar;
import java.awt.TextField;
import java.awt.TextArea;
import java.awt.Event;
import java.awt.Choice;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.DefaultTableModel;
class UDPPacket extends Panel
{
	Label head;
	JTable table;
	int routers;
	DefaultTableModel tableModel = new DefaultTableModel(
	new Object[][] 
	{
		{"Source IP", "192.168.11.01"},
        {"Destination IP", "125.21.167.29  "},
        {"TTL","1"},
		{"Destination Port","45536"},
	},
    new Object[] 
	{"Description", "value" })
	{ 
		@Override  
    	public boolean isCellEditable(int row, int column)  
    	{  
			return false;  
    	} 
	};	
	public UDPPacket(int routers,Color c)
	{
		setBackground(c);
		this.routers = routers;
		head =new Label("UDP PACKET");
		head.setBackground(Color.blue);
		head.setForeground(Color.white);
		add(head);
		TableColumn column = null;
		table = new JTable(tableModel);
		column = table.getColumnModel().getColumn(0);
		column.setPreferredWidth(110);
		column = table.getColumnModel().getColumn(1);
        column.setPreferredWidth(120);
		add(table);
	}
}
