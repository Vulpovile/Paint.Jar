package com.androdome.util.paintdotjar.util;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.androdome.util.paintdotjar.Canvas;
import com.androdome.util.paintdotjar.MainInterfaceAbstractor;
import com.androdome.util.paintdotjar.ui.CanvasContainer;
import com.androdome.util.paintdotjar.util.FileFormatManager;


public final class PDJManager extends FileFormatManager {

	
	
	public PDJManager()
	{
		super("pdj", true);
		this.setDisplayError(true);
	}
	
	@Override
	public boolean accept(File f) {
		// TODO Auto-generated method stub
		return f.isDirectory() || f.getName().toLowerCase().endsWith(".pdj");
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Open Paint.Jar File | *.pdj";
	}

	@Override
	public CanvasContainer loadCanvas(File file, MainInterfaceAbstractor mia) {
		// TODO Auto-generated method stub
		ZipInputStream in = null;
		BufferedReader br = null;
		try {
			in = new ZipInputStream(new GZIPInputStream(new FileInputStream(file)));
			DataInputStream dis = new DataInputStream(in);
			br = new BufferedReader(new InputStreamReader(dis));
			int width = 0;
			int height = 0;
			int lcount = 0;
			ZipEntry ze = in.getNextEntry();
			if(ze == null)
				throw new InvalidMetadataException("Metadata file not found");
			String metaline;
			try{
				while((metaline = br.readLine()) != null)
				{
					if(metaline.startsWith("WIDTH"))
						width = Integer.parseInt(metaline.replace("WIDTH ", ""));
					else if(metaline.startsWith("HEIGHT"))
						height = Integer.parseInt(metaline.replace("HEIGHT ", ""));
					else if(metaline.startsWith("LCOUNT"))
							lcount = Integer.parseInt(metaline.replace("LCOUNT ", ""));
				}
			}
			catch(NumberFormatException ex)
			{
				in.closeEntry();
				throw new InvalidMetadataException("Metadata corrupted");
			}
			in.closeEntry();
			CanvasContainer cc = mia.createCanvas(width, height);
			cc.getLayers().clear();
			if(width < 0)
				throw new InvalidMetadataException("Invalid Width");
			if(height < 0)
				throw new InvalidMetadataException("Invalid Height");
			if(lcount < 0)
				throw new InvalidMetadataException("Invalid Layer Count");
			for(int i = 0; i < lcount; i++)
			{
				ze = in.getNextEntry();
				if(ze == null)
					throw new InvalidMetadataException("Layer metadata not found");
				int layerOpacity = dis.readInt();
				String layername = dis.readUTF();
				in.closeEntry();
				ze = in.getNextEntry();
				if(ze == null)
					throw new InvalidMetadataException("Layer image not found");
				BufferedImage img = ImageIO.read(in);
				in.closeEntry();
				if(img == null)
					throw new InvalidMetadataException("Image corrupt");
				Canvas canvas = new Canvas(img);
				canvas.setOpacity(layerOpacity);
				canvas.setName(layername);
				cc.getLayers().add(canvas);
			}
			if(in != null)
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(br != null)
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			return cc;
		} catch (IOException e) {

			JOptionPane.showMessageDialog(null, "Failed to load:" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (InvalidMetadataException e) {
			JOptionPane.showMessageDialog(null, "Failed to load:" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

			e.printStackTrace();
		}
		finally
		{
			if(in != null)
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(br != null)
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return null;
	}

	@Override
	public boolean saveCanvas(CanvasContainer cc, File file, MainInterfaceAbstractor mia) {
		try {
			ZipOutputStream out = new ZipOutputStream(new GZIPOutputStream(new FileOutputStream(file)));
			DataOutputStream dos = new DataOutputStream(out);
			ZipEntry e = new ZipEntry("basemeta.mta");
			out.putNextEntry(e);
			out.write("PDJ METADATA TYPE 1\r\n".getBytes());
			out.write(("WIDTH " + cc.getImageWidth() + "\r\n").getBytes("US-ASCII"));
			out.write(("HEIGHT " + cc.getImageHeight() + "\r\n").getBytes("US-ASCII"));
			out.write(("LCOUNT " + cc.getLayers().size() + "\r\n").getBytes("US-ASCII"));
			out.closeEntry();
			int i = 0;
			for(Canvas c : cc.getLayers())
			{
				e = new ZipEntry(i+".bmd");
				out.putNextEntry(e);
				dos.writeInt(c.getOpacity());
				dos.writeUTF(c.getName());
				out.closeEntry();
				e = new ZipEntry(i+".png");
				out.putNextEntry(e);
				ImageIO.write(c.getImage(), "png", out);
				out.closeEntry();
			}
			out.close();
			return true;
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Failed to save: File not found", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Failed to save: General Error", "Error", JOptionPane.ERROR_MESSAGE);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
class InvalidMetadataException extends Exception
{

	public InvalidMetadataException(String string) {
		super(string);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
