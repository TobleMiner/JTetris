package de.toble.tetris.network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import de.toble.tetris.gui.view.lighthouse.Lighthouse;

public class LighthouseNetwork implements Runnable
{
	private Socket lightSocket;
	private BufferedReader input;
	private DataOutputStream output;
	private Thread nwThread;

	private Lighthouse lighthouse;

	private boolean connected = false;

	public void connect(String host, int port) throws IOException
	{
		this.lightSocket = new Socket(host, port);
		this.input = new BufferedReader(
				new InputStreamReader(this.lightSocket.getInputStream()));
		this.output = new DataOutputStream(lightSocket.getOutputStream());
		this.nwThread = new Thread(this);
		this.nwThread.start();
		this.connected = true;
	}

	private boolean checkConnenction()
	{
		if(this.lightSocket == null) return false;
		if(this.lightSocket.isClosed() || !this.lightSocket.isConnected()) return false;
		return true;
	}

	@Override
	public void run()
	{
		while(true)
		{
			try
			{
				String str = input.readLine();
				if(str.equals("bufLen=0")) this.lighthouse.sendFrame();
			}
			catch(IOException e)
			{
				e.printStackTrace();
				if(!checkConnenction())
				{
					System.err.println("Lighthouse closed the connection");
					this.connected = false;
					break;
				}
			}
			this.lighthouse.sendFrame();
		}
	}

	public boolean sendFrame(byte[] frame)
	{
		if(!this.connected) return false;
		try
		{
			this.output.write(frame, 0, frame.length);
			return true;
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public void setLighthouse(Lighthouse lh)
	{
		this.lighthouse = lh;
	}
}
