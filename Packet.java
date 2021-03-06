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
class Packet extends Panel
{
	Label head;
	JTable table;
	int routers;
	UDPPacket udpPacket;
	ICMPPacket icmpPacket;
	String ipDb[] = 
	{
		"192.168.11.01  ","122.172.0.1    ","125.21.167.29  ",
		"72.14.216.229  ","66.249.94.170  ","72.14.233.204  ",
		"125.62.187.133 ","209.85.241.33  ","213.155.133.149"
	};//Database of IP addresses used to demonstrate Traceroute
	public Packet(int routers)
	{
		setLayout(new GridLayout(0,1));
		udpPacket = new UDPPacket(routers,null);
		icmpPacket = new ICMPPacket(routers,null);
		add(udpPacket);
		add(icmpPacket);
	}
	public void changePacket(String packetName)
	{
		if(packetName.equals("UDP"))
		{
			udpPacket.setBackground(Color.red);
			icmpPacket.setBackground(null);			
		}
		else if(packetName.equals("ICMP"))
		{
			icmpPacket.setBackground(Color.red);
			udpPacket.setBackground(null);
		}
		else 
		{
			icmpPacket.setBackground(null);
			udpPacket.setBackground(null);
		}
	}
	/*public void setUdpSource(int srcIP)
	{
		udpPacket.tableModel.setValueAt(ipDb[srcIP],0,1);
	}
	public void setUdpDest(int destIP)
	{
		udpPacket.tableModel.setValueAt(ipDb[destIP],1,1);
	}*/
	public void setUdpSrcDest(int srcID,int destID)
	{
		udpPacket.tableModel.setValueAt(ipDb[srcID],0,1);
		udpPacket.tableModel.setValueAt(ipDb[destID],1,1);
	}	
	public void setUdpTTL(int TTL)
	{
		udpPacket.tableModel.setValueAt(TTL+"",2,1);
	}
	/*public void setIcmpSource(int srcIP)
	{
		icmpPacket.tableModel.setValueAt(ipDb[srcIP],0,1);
	}
	public void setIcmpDest(int destIP)
	{
		icmpPacket.tableModel.setValueAt(ipDb[destIP],1,1);
	}*/
	public void setIcmpSrcDest(int srcID,int destID)
	{
		icmpPacket.tableModel.setValueAt(ipDb[srcID],0,1);
		icmpPacket.tableModel.setValueAt(ipDb[destID],1,1);
	}
	public void setIcmpTTL(int TTL)
	{
		icmpPacket.tableModel.setValueAt(TTL+"",2,1);
	}
	public void setIcmpType(String icmpType)
	{
		icmpPacket.tableModel.setValueAt(icmpType+"",3,1);
	}
	public void setIcmpCode(String icmpCode)
	{
		icmpPacket.tableModel.setValueAt(icmpCode+"",4,1);
	}
}
