/*
 *    This Traceroute applet was designed to be used in conjunction with
 *    "Computer Networking: A Top Down Approach"
 *    by James Kurose & Keith Ross.
 *
 *    Applet coded by Ashish Jain as project assigned by 
 *    Dr. Ram Prakash Rustagi, PESIT, Bangalore, India
 *
 */

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

public class TraceRoute extends Applet implements Runnable
{
	GridBagLayout gridBag = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
	Button start,reset,packet;
	static Button pause;
	static Choice hops,client,speed;
	Label hopsLabel,clientLabel,speedLabel;
	static TextArea output;
	ImgPanel imgp;
	Legend legend;
	Image unit1,unit2,unit4,pack;
	int nhops;
	Thread animate;
	boolean freeze = true;
	int frameNumber = 0;
	int curSpeed = 1;
	static final int speedDB[] = {10,20,1};
	static boolean animation = false;
	static Packet packetView;
	@Override
	public void init()
	{
		setLayout(gridBag);
		resetGBC(c);
		//Start button
		start = new Button("Start");
		//c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.EAST;
		gridBag.setConstraints(start,c);
		start.enable();
		add(start);
		resetGBC(c);
		//Pause button
		pause = new Button("Pause");
		c.fill = GridBagConstraints.HORIZONTAL;
		gridBag.setConstraints(pause,c);
		pause.disable();
		add(pause);
		resetGBC(c);
		//Reset Button
		reset = new Button("Reset");
		c.fill = GridBagConstraints.HORIZONTAL;
		gridBag.setConstraints(reset,c);
		reset.disable();
		add(reset);
		resetGBC(c);
		//Hops label
		hopsLabel = new Label("Hops :");
		c.ipadx = -8;
		gridBag.setConstraints(hopsLabel,c);
		add(hopsLabel);
		resetGBC(c);
		//Hops choice box
		hops = new Choice();
		for(int i = 2;i<=8;i++)
			hops.addItem(i+"");
		c.ipadx = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		gridBag.setConstraints(hops,c);
		add(hops);
		resetGBC(c);
		//client label
		clientLabel = new Label("Client :");
		c.ipadx = -2;
		gridBag.setConstraints(clientLabel,c);
		add(clientLabel);
		resetGBC(c);
		//Choice of client
		client = new Choice();
		client.addItem("Linux");
		client.addItem("Windows");
		c.fill = GridBagConstraints.HORIZONTAL;
		gridBag.setConstraints(client,c);
		add(client);
		resetGBC(c);
		//Speed label
		speedLabel = new Label("Speed :");
        c.ipadx = -7;
		gridBag.setConstraints(speedLabel,c);
		add(speedLabel);
		resetGBC(c);
		//Speed controller		
		speed = new Choice();
		speed.addItem("Fast");
		speed.addItem("Slow");
		speed.addItem("Very Fast");
		curSpeed = speed.getSelectedIndex();	
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.ipadx = 13;
		c.anchor =  GridBagConstraints.LINE_START;
		gridBag.setConstraints(speed,c);
		add(speed);
		resetGBC(c);
		//Fetching images
		unit1 = getImage(getCodeBase(),"./img/UUnit1.gif");
		unit2 = getImage(getCodeBase(),"./img/UUnit2.gif");
		unit4 = getImage(getCodeBase(),"./img/UUnit4.gif");
		nhops = Integer.parseInt(hops.getSelectedItem());
		imgp = new ImgPanel(this);
		setImage();
		resetGBC(c);
		//Displaying packet content
		packetView = new Packet(nhops-1);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.ipadx = 15;
		c.gridheight = 2;
		gridBag.setConstraints(packetView,c);
		add(packetView);		
		//Legend
		legend = new Legend();
		c.gridwidth  = GridBagConstraints.RELATIVE;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.ipadx = -5;
		gridBag.setConstraints(legend,c);
		add(legend);
		resetGBC(c); 
		//Displays output 
		output = new TextArea();
		output.setEditable(false);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.weighty = 0.5;
        c.gridwidth  = GridBagConstraints.REMAINDER;
		gridBag.setConstraints(output,c);
		add(output);
		resetGBC(c);
		validate();
	}	
	@Override
	public void start()
	{
		if(freeze)
		{
			//Do nothing
		}
		else
		{
			//Start Animating
			if(animate == null)
			{
				animate = new Thread(this);
			}	
			animate.start();
		}
	}	
	@Override
	public void stop()
	{
		animate = null;
		repaint();
	}	
	@Override
	public void run()
	{
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		//Animation Loop
		while(Thread.currentThread() == animate && frameNumber < 100000)
		{
			animation = true;
			frameNumber++;
			repaint();
			imgp.repaint();
			try
			{
				Thread.sleep(speedDB[speed.getSelectedIndex()]);
			}
			catch (InterruptedException e)
			{
				break;
			}	
		}
	}
	protected void setImage()
    {
		c.weightx = 0.0;
        c.ipady = 250;
		c.gridy = 2;
		imgp.routers = nhops - 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;
		gridBag.setConstraints(imgp,c);
		add(imgp);
		repaint();
		resetGBC(c);
    }
	private void resetGBC(GridBagConstraints c) 
	{
   		//  default constraints
    	c.gridx = GridBagConstraints.RELATIVE;
    	c.gridy = GridBagConstraints.RELATIVE;
    	c.gridwidth = 1;
    	c.gridheight = 1;
    	c.weightx = 0.5;
    	c.weighty = 0.0;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill = GridBagConstraints.NONE;
    	c.ipadx = 0;
   		c.ipady = 0;
  	}
	@Override
	public boolean action(Event e,Object arg)
	{
		if(e.target == start)
		{			
			start.disable();
			pause.enable();
			reset.enable();
			hops.disable();
			client.disable();
			imgp.pause = false;
			animation = true;
			if(freeze)
			{
				freeze = false;	
			}
			start();
			return true;
		}
		if(e.target == pause)
		{
			animation = true;
			pause.disable();
			start.enable();
			reset.enable();
			hops.disable();
            client.disable();
			imgp.pause = true;
			if(!freeze)
			{
				freeze = true;
				stop();
			}	
			return true;
		}
		if(e.target == reset)
		{
			packetView.setUdpSrcDest(0,nhops);
			packetView.setIcmpSrcDest(0,nhops);	
			packetView.setUdpTTL(1);
			packetView.setIcmpTTL(1);	
			packetView.changePacket("None");		
			output.setText("");
			animation = false;
			imgp.reset();
			animate = null;
			reset.disable();
			start.enable();
			pause.disable();
			hops.enable();
            client.enable();
			frameNumber = 0;
			imgp.pause = false;
			stop();
			return true;
		}
		if (e.target == hops)
		{
			nhops = Integer.parseInt(hops.getSelectedItem());
			packetView.setUdpSrcDest(0,nhops);
			packetView.setIcmpSrcDest(0,nhops);
			imgp.reset();
			setImage();			
			return true;
		}
		if (e.target == client)
		{
			legend.repaint();
			return true;
		}
		return false;
	}
}
