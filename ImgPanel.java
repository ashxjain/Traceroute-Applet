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
import java.util.Random;

class ImgPanel extends Panel
{
	Image unit1,unit2,unit4;
	int routers;
	Dimension offDimension;
	Image offImage;
	Graphics offGraphics;
	int initpos,st,dest,routerstart,k;
	int xpos,ypos;
	int x,y,x1,y1,routersToBeVisited,nextRouterPos;	
	int iwidth1,iwidth2,iwidth4,totwidth;
	int iheight1,iheight2,iheight4;
	public final int packetWidth = 8;
	public final int packetHeight = 15;
	int counter=3,destip,currentip=1;
	double timeDelay;
	String ipDb[] = 
	{
		"192.168.11.01  ","122.172.0.1    ","125.21.167.29  ",
		"72.14.216.229  ","66.249.94.170  ","72.14.233.204  ",
		"125.62.187.133 ","209.85.241.33  ","213.155.133.149"
	};//Database of IP addresses used to demonstrate Traceroute
	boolean lastCompl=false,roundCompl=false,left = false,pause = false,dropCompl = false;
	TraceRoute trace;
	int TTL = 1,TTLcount=1;
	
	ImgPanel(TraceRoute tr)
	{
		super();
		trace = tr;
		unit1 = tr.unit1;
		unit2 = tr.unit2;
		unit4 = tr.unit4;
		this.routers = tr.nhops-1;
		setBackground(Color.white);
	}//End of constructor	
	@Override
	public void paint(Graphics g)
	{
		update(g);
	}//End of paint
	@Override
	public void update(Graphics g)	
	{
		Dimension d = size();
		if ( (offGraphics == null) || (d.width != offDimension.width) || (d.height != offDimension.height) )
		{
    		offDimension = d;
    		offImage = createImage(d.width, d.height);
    		offGraphics = offImage.getGraphics();
		}
		offGraphics.setColor(getBackground());
		offGraphics.fillRect(0, 0, d.width, d.height);
		offGraphics.setColor(Color.black);
		drawOn(offGraphics);
		animOn(offGraphics);
		g.drawImage(offImage, 0, 0, this);
	}//End of update
	public void reset()
	{
		st = initpos + 90;
		dest = xpos-10;
		routerstart = initpos + iwidth1;
		routersToBeVisited = routers;
		nextRouterPos = iwidth2;
		x = st;
		k = 0;
		TTL = 1;
		TTLcount = 1;
		x1 = 0;
		left = false;
		currentip =1;
		//nextRouterPos = 0;
		counter = 3;
		roundCompl = false;
		dropCompl = false;
		lastCompl = false;
		pause = false;
    }//End of reset
	public boolean isBetween(int val,int min,int max)
	{
		if(val>=min && val<max)
			return true;
		return false;
	}//End of isBetween
	public void drawOn(Graphics g)
	{
		iwidth1 = unit1.getWidth(this)/2;
        iheight1 = unit1.getHeight(this)/2;
		iwidth2 = unit2.getWidth(this)/4;
        iheight2 = unit2.getHeight(this)/4;
        iwidth4 = unit4.getWidth(this)/2;
        iheight4 = unit4.getHeight(this)/2;
        totwidth = iwidth1 + iwidth4;
		int i = routers;
		while(i>0)
		{
			totwidth += iwidth2;
				i--;
		}//End of while loop
		initpos = size().width/2 - totwidth/2 ;
		xpos = initpos ;		
		ypos = 30;			
	    g.drawImage(unit1,xpos,ypos+25,iwidth1,iheight1,this);
		xpos = xpos + iwidth1;
		g.drawString("1", xpos-7, ypos+85);
		i = routers;
		while(i>0)
		{
			g.drawImage(unit2,xpos,ypos+75,iwidth2,iheight2,this);
			xpos = xpos + iwidth2;
			g.drawString((routers-i+2)+"", xpos-8, ypos+85);
			destip = routers-i+2;
			i--;
		}//End of while loop		
		g.drawImage(unit4,xpos,ypos+25,iwidth4,iheight4,this);		
    }//End of drawOn
	public void animOn(Graphics g)
	{		
		if(trace.animate==null && !pause)//Initial Picture initialization
		{
			st = initpos + 90;
			dest = xpos-10;
			routerstart = initpos + iwidth1;
			routersToBeVisited = routers;
			nextRouterPos = iwidth2;
			x = st;
			k = 0;
			TTL = 1;
			TTLcount = 1;
		}
		if(TraceRoute.animation)
		{
			//PART 1
			//Moving packet from start to routerstart
			if(x == st)//Initializing console output
			{
				if(TraceRoute.client.getSelectedIndex()==0)
				{
					TraceRoute.packetView.changePacket("UDP");
					TraceRoute.packetView.setUdpTTL(TTL);
					//TraceRoute.packetView.setUdpSrcDest(0,routers+1);
				}//End of inner if
				else
				{
					TraceRoute.packetView.changePacket("ICMP");
					TraceRoute.packetView.setIcmpTTL(TTL);
					//TraceRoute.packetView.setIcmpSrcDest(0,routers+1);
				}//End of inner else
				timeDelay = 0;
				String t = "traceroute to "+ipDb[routers+1]+",8 hops max ,60 byte packets";
				TraceRoute.output.setText(t);
			}//End of outer if	
			if(isBetween(x,st,routerstart))//Checking if packet is between start and routerstart
			{
				y = ypos + 60;
				g.setColor(Color.blue);
				g.fillRect(x,y,packetWidth,packetHeight);					
				x = x+1;
				timeDelay++;
				if(x==routerstart-1)
				{						
					--TTL;					
					if(TraceRoute.client.getSelectedIndex()==0)
						TraceRoute.packetView.setUdpTTL(TTL);
					else
						TraceRoute.packetView.setIcmpTTL(TTL);			
				}//End of inner if   
				if(x==routerstart)
				{										
					try
					{
						Thread.sleep(300);
					}
					catch (InterruptedException e)
					{
						//To catch exception caused when interrupt is raised while thread is sleeping
					}				
				}//End of inner if 
				g.drawString("TTL = "+TTL,x,y-10);//Displaying TTL value				
			}//End of outer if
			//PART 2
			//Moving from routerstart to dest
			else if(routersToBeVisited>0 && isBetween(x,routerstart,dest))
			{
				if(!dropCompl)
				{
					if(y1==0)
						y1 = y;
					packetDrop(g);
				}//End of inner if
				else if(!roundCompl)
				{						
					if(x1==0)
					{
						x1 = x;
						TraceRoute.packetView.changePacket("ICMP");
						//Setting ICMPs for Time Exceeded error message
						//Set ICMP Type
						TraceRoute.packetView.setIcmpType("11");//"TimeExT");
						//Set ICMP Code
						TraceRoute.packetView.setIcmpCode("0");//"TimeExC");
						TraceRoute.packetView.setUdpSrcDest(currentip,0);
						TraceRoute.packetView.setIcmpSrcDest(currentip,0);
						if(TraceRoute.client.getSelectedIndex()==0)
						{
							TraceRoute.packetView.setIcmpTTL(128);
							TraceRoute.packetView.setUdpTTL(128);
						}			
						else
						{
							TraceRoute.packetView.setIcmpTTL(64);
							TraceRoute.packetView.setUdpTTL(128);
						}	
					}//End of innermost if
					roundTrip(g);
				}//End of inner else if
				else if(x< routerstart+nextRouterPos)
				{
					y = ypos + 60;
					g.setColor(Color.blue);
					g.fillRect(x,y,packetWidth,packetHeight);		
					x = x+1;
					timeDelay++;
					if(x == routerstart+nextRouterPos-1 || x == dest-1)
					{
						--TTL;
						if(TraceRoute.client.getSelectedIndex()==0)
							TraceRoute.packetView.setUdpTTL(TTL);
						else
							TraceRoute.packetView.setIcmpTTL(TTL);	
					}//End of innermost if
					if(x==routerstart+nextRouterPos || x==dest)
					{
						try
						{
							Thread.sleep(300);
						}
						catch (InterruptedException e)
						{
						}
					}//End of innermost if
					if(x>dest-40)
						g.drawString("TTL = "+TTL,dest-40,y-10);
					else
						g.drawString("TTL = "+TTL,x,y-10);
				}///End of inner else if
				else
				{
					roundCompl = false;
					dropCompl = false;
					nextRouterPos = nextRouterPos + iwidth2;
					routersToBeVisited--;			
				}//End of inner else
			}//End of outer else if
			else
			{
					if(x1==0)
					{
							x1 = x;
							TraceRoute.packetView.changePacket("ICMP");
							if(TraceRoute.client.getSelectedIndex()==0)
							{
								//Setting ICMPs for destination unreachable
								//Set ICMP Type
								TraceRoute.packetView.setIcmpType("3");//"DestUnrT");
								//Set ICMP Code
								TraceRoute.packetView.setIcmpCode("3");//"DestUnrC");							
							}//End of innermost if
							else
							{
								//Setting ICMPs for ICMP Echo Reply
								//Set ICMP Type
								TraceRoute.packetView.setIcmpType("0");//"ICMPRepT");								
								//Set ICMP Code
								TraceRoute.packetView.setIcmpCode("0");//"ICMPRepC");
							}//End of innermost else
							TraceRoute.packetView.setUdpSrcDest(currentip,0);
							TraceRoute.packetView.setIcmpSrcDest(currentip,0);				
					}//End of inner if
					if(!lastCompl)
						roundTrip(g);
					else
					{
						TraceRoute.pause.disable();
						TraceRoute.packetView.changePacket("None");
						TraceRoute.animation = false;	
					}//End of inner else					
			}//End of outer else if
      	}//end of outermost if
	}//end of animOn
	public void roundTrip(Graphics g)
	{
		if(x1!=st && !left)//Going left
		{ 
			if(x==dest)
			{
				g.setColor(Color.black);
			}			
			else
			{	
				g.setColor(Color.red);
			}            
			y = ypos + 110;
            g.fillRect(x1,y,packetWidth,packetHeight);
			if(TraceRoute.client.getSelectedIndex()==0)
			{
				g.drawString("TTL = 128",x1,y+30);
			}			
			else
			{	
				g.drawString("TTL = 64",x1,y+30);
			}			
			x1 = x1 - 1;
			timeDelay++;
		}
		else if(x1 == st && !left)//checking if reached start
		{
			TraceRoute.packetView.changePacket("UDP");
			TTL = TTLcount;
			k = 0;
			if(counter==1)//Start to goto next router
			{
				TTL = ++TTLcount; 
				currentip++;                            
			}
			if(counter==3)//First info(ip addr & first delay) from router
			{
				timeDelay = (double)Math.round((timeDelay/100.0 + new Random().nextDouble())*100)/100 - 1.0;
				TraceRoute.output.append("\n"+currentip+"  "+ipDb[currentip]+"\t"+timeDelay+"ms");
				//currentip++;
			}
			else//Next 2 delays from router
			{
				timeDelay = (double)Math.round((timeDelay/100.0 + new Random().nextDouble())*100)/100;
				TraceRoute.output.append("\t"+timeDelay+"ms");
			}
			TraceRoute.packetView.setUdpSrcDest(0,routers+1);
			TraceRoute.packetView.setIcmpSrcDest(0,routers+1);
			if(TraceRoute.client.getSelectedIndex()==0)
				TraceRoute.packetView.setUdpTTL(TTL);
			else
				TraceRoute.packetView.setIcmpTTL(TTL);		 
			timeDelay = 0;
			left = true;
			if(counter == 1 && x==dest)
				lastCompl = true;
			if(TraceRoute.client.getSelectedIndex()==0)
			{
				TraceRoute.packetView.changePacket("UDP");
			}			
			else
			{
				TraceRoute.packetView.changePacket("ICMP");
				//Setting ICMPs for ICMP Echo request
				//TraceRoute.packetView.tableModel.setValueAt("EchoReqT",3,1);//Set ICMP Type
				TraceRoute.packetView.setIcmpType("8");//"EchoReqT");
				//TraceRoute.packetView.tableModel.setValueAt("EchoReqC",4,1);//Set ICMP Code
				TraceRoute.packetView.setIcmpCode("0");//"EchoReqC");
			}		
		}
		else if(isBetween(x1,st,x) && left)//After reaching start go back to router
		{
			g.setColor(Color.blue);
            y = ypos + 60;
            
			if(x1==((x==routerstart||x1>x-5)?0:routerstart+iwidth2*k))
            {
				try
				{
					Thread.sleep(300);
				}
				catch (InterruptedException e)
				{
				}
                k++;
            }
			g.fillRect(x1,y,packetWidth,packetHeight);	
			if(x1==((x==routerstart||x1>x-5)?0:routerstart+iwidth2*k-1))
			{
				--TTL;
				if(TraceRoute.client.getSelectedIndex()==0)
					TraceRoute.packetView.setUdpTTL(TTL);
				else
					TraceRoute.packetView.setIcmpTTL(TTL);		
			}
			if(x1==(x-2))
			{
				--TTL;
				if(TraceRoute.client.getSelectedIndex()==0)
					TraceRoute.packetView.setUdpTTL(TTL);
				else
					TraceRoute.packetView.setIcmpTTL(TTL);			
			}
			if(x1==(x-1))
			{
				try
				{
					Thread.sleep(300);
				}
				catch (InterruptedException e)
				{
				}
			}			
			if(x1>dest-40)
				g.drawString("TTL = "+TTL,dest-40,y-10);
			else	
				g.drawString("TTL = "+TTL,x1,y-10);	
			x1 = x1+1;
			if(counter == 0)
				dropCompl = true;
		}
		else//Doing it three times for each router
		{			
			if(counter == 1)
			{
					roundCompl = true;
					counter = 3;
					dropCompl = true;
					left = false;
					x1 = 0;
					k = 0;
			}
			else 
			{
				TraceRoute.packetView.setUdpSrcDest(currentip,0);
				TraceRoute.packetView.setIcmpSrcDest(currentip,0);
				counter--;
				left = false;
				x1 = 0;
				k = 0;
				dropCompl = false;
			}
		}
	}//End of roundTrip
	public void packetDrop(Graphics g)
	{
		if(y1!=y+60)
		{
			y1 += 2;
			g.setColor(Color.blue);
			g.fillRect(x,y1,packetWidth,packetHeight);
		}
		else
		{
			y1 = 0;
			dropCompl = true;
		}		
	}//End of packetDrop
}//End of ImgPanel
