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
			int h = (int)d.getHeight()-10;
            int startx = 10;
            int starty = 30;
			g.draw3DRect(0,0,w,h,true);
            g.drawString("LEGEND:",startx,starty);
			if(TraceRoute.client.getSelectedIndex()==0)
			{
				g.setColor(Color.blue);
				g.fillRect(startx+10,starty+20,packetWidth,packetHeight);
				g.drawString("UDP packet",startx+20+packetWidth,starty+30);
				g.setColor(Color.red);
		        g.fillRect(startx+10,starty+30+packetHeight,packetWidth,packetHeight);	
				g.drawString("ICMP Time Exceeded Error Message",startx+20+packetWidth,starty+40+packetHeight);
				g.setColor(Color.black);
		        g.fillRect(startx+10,starty+40+packetHeight+packetHeight,packetWidth,packetHeight);
				g.drawString("ICMP Destination Port Unreachable Error Message",startx+20+packetWidth,starty+50+packetHeight+packetHeight);
			}
			else
			{
				g.setColor(Color.blue);
				g.fillRect(startx+10,starty+20,packetWidth,packetHeight);
				g.drawString("ICMP Echo Request",startx+20+packetWidth,starty+30);
				g.setColor(Color.red);
		        g.fillRect(startx+10,starty+30+packetHeight,packetWidth,packetHeight);	
				g.drawString("ICMP Time Exceeded Error Message",startx+20+packetWidth,starty+40+packetHeight);
				g.setColor(Color.black);
		        g.fillRect(startx+10,starty+40+packetHeight+packetHeight,packetWidth,packetHeight);
				g.drawString("ICMP Reply Message",startx+20+packetWidth,starty+50+packetHeight+packetHeight);
			}
    }
}
