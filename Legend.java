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
class Legend extends Canvas
{
	public final int packetWidth = 8;
	public final int packetHeight = 15;
	Dimension d;
	public Legend()
	{
		super();		
		//setBackground(Color.white);
	}
	public void paint(Graphics g) 
	{
			d = size();
			int w = (int)d.getWidth()-10;
			int h = (int)d.getHeight()/2;
			g.draw3DRect(0,0,w,h,true);
			if(TraceRoute.client.getSelectedIndex()==0)
			{
				g.setColor(Color.blue);
				g.fillRect(10,10,packetWidth,packetHeight);
				g.drawString("UDP packet",20+packetWidth,20);
				g.setColor(Color.red);
		        g.fillRect(10,20+packetHeight,packetWidth,packetHeight);	
				g.drawString("ICMP Time Exceeded Error Message",20+packetWidth,30+packetHeight);
				g.setColor(Color.black);
		        g.fillRect(10,30+packetHeight+packetHeight,packetWidth,packetHeight);
				g.drawString("ICMP Destination Unreachable Error Message",20+packetWidth,40+packetHeight+packetHeight);
			}
			else
			{
				g.setColor(Color.blue);
				g.fillRect(10,10,packetWidth,packetHeight);
				g.drawString("ICMP Echo Request",20+packetWidth,20);
				g.setColor(Color.red);
		        g.fillRect(10,20+packetHeight,packetWidth,packetHeight);	
				g.drawString("ICMP Time Exceeded Error Message",20+packetWidth,30+packetHeight);
				g.setColor(Color.black);
		        g.fillRect(10,30+packetHeight+packetHeight,packetWidth,packetHeight);
				g.drawString("ICMP Reply Message",20+packetWidth,40+packetHeight+packetHeight);
			}
			g.setColor(Color.black);
			//g.draw3DRect(0,h+10,w,h-32,true);
			g.draw3DRect(0,h,w,h,true);
			String s = "Traceroute Applet written by Ashish Jain & Afaaq Alam";
			g.drawString(s,10,h+40);
			s = "Advised by Dr.Ram Prakash Rustagi, rprustagi@pes.edu";
            g.drawString(s,10,h+60);
            s = "PESIT, Bangalore, India";
            g.drawString(s,10,h+80);
    }
}
