/*	Ofek Gila
	March 2_th, 2014
	MediaPlayer.java
	This program will create a panel :O
*/	

import java.awt.*;			// Imports
import java.awt.event.*;
import javax.swing.*;
import java.util.Scanner;
import java.io.*;
import javax.sound.sampled.*;
import java.net.MalformedURLException;
import javax.imageio.*;

public class 	MediaPlayer	extends JApplet	{
	public static void main(String[] args)	{
			MediaPlayer Uhm = new 	MediaPlayer();
			Uhm.run();
			//Uhm.makePanel();
	}
	public void run()	{
		JFrame frame = new JFrame("Music Player");	// ask why I extend JApplet or implement all of those things ^_^
		frame.setSize(500, 500);		// Sets size of frame
		frame.setResizable(false);						// Makes it so you can't resize the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new MusicPanel());
		frame.setVisible(true);
	}
}
class MusicPanel extends TemplatePanel	{
	//public String[] files;
	public String[] musicFiles, musicFilesCopy = new String[1];
	public int[] playlist, playlistCopy;
	public String SampleWav = "Sample.wav";
	public int songOn = 1;
	public boolean initial = true;
	
	public MusicPanel()	{
		width = getWidth();
		height = getHeight();	
		
		final File folder = new File(getDirectory());
		getFilesInFolder(folder);
		playlist = new int[1];
		songOn = 0;
		getMusicFiles(files);
		playlist[0] = getSong();
		addSongs();
		setupMusicPlayer();
		playSound(musicFiles[playlist[1]]);
		//AK.outputArray(musicFiles);
		//center.setLabel();
		//center.setBackground(Color.magenta);
	}
	public String extension(String file)	{
		try	{
			return file.substring(file.lastIndexOf("."));
		}
		catch (StringIndexOutOfBoundsException e)	{
			return "null";
		}
	}
	public String AbsoluteName(String name)	{
		return name.substring(name.lastIndexOf("\\")+1, name.lastIndexOf("."));
	}
	public void getMusicFiles(String[] files)	{
		int count = 0;
		//musicFiles = new String[0];
		//musicFilesCopy = new String[1];
		for (int i = 0; i < files.length; i++)	{
			if (extension(files[i]).equals(".wav"))	{
				musicFiles = new String[count + 1];
				for (int a = 0; a < count; a++)	musicFiles[a] = musicFilesCopy[a];
				musicFiles[count] = files[i];
				count++;
				musicFilesCopy = new String[count];
				for (int a = 0; a < count; a++)	musicFilesCopy[a] = musicFiles[a];
			}
		}
	}
	public void generateSongs()	{
		if (songOn == 0)	{
			playlistCopy = new int[playlist.length + 1];
			for (int a = 0; a < playlist.length; a++)	playlistCopy[a+1] = playlist[a];
			playlist = new int[playlistCopy.length];
			for (int a = 0; a < playlist.length; a++)	playlist[a] = playlistCopy[a];
			playlist[0] = getSong(playlist[1]);
			songOn++;
		}
		if (songOn == playlist.length - 1)	{
			playlistCopy = new int[playlist.length + 1];
			for (int a = 0; a < playlist.length; a++)	playlistCopy[a] = playlist[a];
			playlist = new int[playlistCopy.length];
			for (int a = 0; a < playlist.length; a++)	playlist[a] = playlistCopy[a];
			playlist[playlist.length - 1] = getSong(playlist[playlist.length-2]);
		}
	}
	public void addSongs()	{
		if (songOn == 0)	{
			playlistCopy = new int[playlist.length + 1];
			for (int a = 0; a < playlist.length; a++)	playlistCopy[a+1] = playlist[a];
			playlist = new int[playlistCopy.length];
			for (int a = 0; a < playlist.length; a++)	playlist[a] = playlistCopy[a];
			playlist[0] = (playlist[1] + musicFiles.length - 1) % musicFiles.length;
			songOn++;
		}
		if (songOn == playlist.length - 1)	{
			playlistCopy = new int[playlist.length + 1];
			for (int a = 0; a < playlist.length; a++)	playlistCopy[a] = playlist[a];
			playlist = new int[playlistCopy.length];
			for (int a = 0; a < playlist.length; a++)	playlist[a] = playlistCopy[a];
			playlist[playlist.length - 1] = (playlist[playlist.length-2] + musicFiles.length + 1) % musicFiles.length;
		}
	}
	public int getSong()	{
		return (int)(Math.random() * musicFiles.length);
	}
	public int getSong(int songNextToIt)	{
		int ran1;
		do {
			ran1 = (int)(Math.random() * musicFiles.length);
		}while (ran1 == songNextToIt);
		return ran1;
	}
	public void paintComponent(Graphics a)	{
		super.paintComponent(a);
		g = a;
		width = getWidth();
		height = getHeight();	
		if (initial)	{
			constructor();
			initial = false;
		}
	}
	public void setupMusicPlayer()	{
		flowLayout = new FlowLayout();
		setLayout(flowLayout);
		top = new SongData();
		center = new SongName();
		scrollbar = new Scrollbar();
		repeatIcon = new ImageIcon(getClass().getResource("repeat.png"));
		shuffleIcon = new ImageIcon(getClass().getResource("shuffleIcon.jpg"));
		songListIcon = new ImageIcon(getClass().getResource("dropdown.png"));
		timeLeftIcon = new ImageIcon(getClass().getResource("timeleft.jpg"));
		lengthBar = new LengthBar();
		repeatButton = new JToggleButton(repeatIcon);
		shuffleButton = new JToggleButton(shuffleIcon);
		timeLeftButton = new JToggleButton(timeLeftIcon);
		songList = new JButton(songListIcon);
		addSongListButton();
		top.setPreferredSize(new Dimension(500, 50));
		center.setPreferredSize(new Dimension(500, 100));
		scrollbar.setPreferredSize(new Dimension(500, 200));
		repeatButton.setPreferredSize(new Dimension(50, 40));
		shuffleButton.setPreferredSize(new Dimension(50, 40));
		timeLeftButton.setPreferredSize(new Dimension(50, 40));
		songList.setPreferredSize(new Dimension(50, 40));
		lengthBar.setPreferredSize(new Dimension(500, 20));
		add(top, "top");
		add(center, "center");
		add(scrollbar, "scrollbar");
		if (!(musicFiles.length == 1))	{
			add(repeatButton, "repeat button");
			add(shuffleButton, "shuffleButton");
			add(songList, "Song List");
		}
		add(timeLeftButton, "timeLeft");
		add(lengthBar, "length bar");
	}
	public void addSongListButton()	{
		songList.addActionListener(new ActionListener()	{
			public void actionPerformed(ActionEvent e)	{
				JFrame frame = new JFrame("Choose a song!");	// ask why I extend JApplet or implement all of those things ^_^
				frame.setSize(500, 60);		// Sets size of frame
				frame.setResizable(false);						// Makes it so you can't resize the frame
				frame.setVisible(true);
				frame.setLocation(getWidth() + 10, 0);
				SongListBox = new ComboBox();
				frame.add(SongListBox);
				boxMade = true;
			}
		});
	}
	private class ComboBox	extends JComboBox	{
		private int currentSongIndex;
		ComboBox()	{
			setPreferredSize(new Dimension(200, 50));
			for (int i = 0; i < musicFiles.length; i++)	addItem(AbsoluteName(musicFiles[i]));
			addItem("Current Song");
			setFont(new Font("Arial", Font.PLAIN, 20));
			selectCurrentSong();
			addActionListener(this);
		}
		public void selectCurrentSong()	{
			setSelectedIndex(playlist[songOn]);
			currentSongIndex = playlist[songOn];
		}
		public void actionPerformed(ActionEvent e)	{
			if (getSelectedIndex() == musicFiles.length) selectCurrentSong();
			else if (getSelectedIndex() == playlist[songOn]);
			else changeToSong(getSelectedIndex());
		}
	}
	private class SongData extends JPanel	{
		private Graphics a;
		SongData()	{
			setBackground(Color.yellow);
		}
		public void paintComponent(Graphics G)	{
			super.paintComponent(G);
			a = G;
			a.setFont(new Font("Arial", Font.BOLD, 30));
			a.setColor(Color.blue);
			a.drawString(timeString+"", 20, getHeight()/2 + 10);
			if (timeLeftButton.isSelected())	a.drawString(timeLeftString+"", getWidth() - 75, getHeight()/2 + 10);
			else	a.drawString(endTimeString+"", getWidth() - 75, getHeight()/2+10);
		}
	}
	private class SongName extends JPanel	{
		private boolean labelCreated = false;
		SongName()	{
			setBackground(Color.green);
			setLabel();
			
		}
		public void paintComponent(Graphics G)	{
			super.paintComponent(G);
			if (labelCreated)	songName.setText(AbsoluteName(musicFiles[playlist[songOn]]));
		}
		public void setLabel()	{
			songName = new JLabel(AbsoluteName(musicFiles[playlist[songOn]]));
			songName.setFont(new Font("Arial", Font.ITALIC, 30));
			add(songName);
			labelCreated = true;
		}
	}
	private class Scrollbar extends JPanel	{
		private GridLayout grid;
		private JPanel leftScroll, rightScroll, center;
		Scrollbar()	{
			grid = new GridLayout(1, 3);
			setLayout(grid);
			leftScroll = new Scroll(-1);
			rightScroll = new Scroll(1);
			center = new Options();
			add(leftScroll);
			add(center);
			add(rightScroll);
		}
		private class Scroll extends TemplatePanel	{
			private int scrollDirection;
			private Graphics g;
			Scroll(int sD)	{
				scrollDirection = sD;
				setBackground(Color.magenta);
			}
			public void mousePressed (MouseEvent e) {
				changeSong(scrollDirection);
			}
			public void paintComponent(Graphics G)	{
				super.paintComponent(G);
				g = G;
				drawSkip();
			}
			public void drawSkip()	{
				int[] x = new int[3];
				int[] y = new int[3];
				g.setColor(Color.black);
				for (int i = 0; i < 3; i+=1)	{
					if (scrollDirection == -1)	{
						x[0] = i*scrollDirection*20 + getWidth();
						x[1] = i*scrollDirection*20 + getWidth();
						x[2] = i*scrollDirection*40 + getWidth();
					}
					else {
						x[0] = i*scrollDirection*20;
						x[1] = i*scrollDirection*20;
						x[2] = i*scrollDirection*40;
					}
					y[0] = getHeight() / 2 - 10;
					y[1] = getHeight() / 2 + 10;
					y[2] = getHeight() / 2;
					g.fillPolygon(x, y, 3);
				}
			}
		}
		private class Options extends TemplatePanel	{
			private Graphics g;
			Options()	{
				setBackground(Color.white);
			}
			public void paintComponent(Graphics G)	{
				super.paintComponent(G);
				g = G;
				drawPause();
			}
			public void drawPause()	{
				g.setColor(Color.black);
				if (pause)	{
					g.drawRect(getWidth() / 2 - 40, 10, 20, getHeight() - 20);
					g.drawRect(getWidth() / 2 + 20, 10, 20, getHeight() - 20);
				}
				else {
					g.drawLine(getWidth() / 2 - 40, 10, getWidth() / 2 - 40, getHeight() - 20);
					g.drawLine(getWidth() / 2 - 40, 10, getWidth() / 2 + 50, getHeight() / 2 - 10);
					g.drawLine(getWidth() / 2 - 40, getHeight() - 20, getWidth() / 2 + 50, getHeight() / 2 - 10);
				}
			}
			public void mousePressed(MouseEvent e)	{
				pause = !pause;
				pauseUnpause();
				repaint();
			}
		}
	}
	/*private class RepeatIcon extends TemplatePanel	{
		private Graphics g;
		private boolean hover = false;
		RepeatIcon()	{
			imageLocation = "repeat.png";
		}
		public void paintComponent(Graphics G)	{
			super.paintComponent(G);
			g = G;
			drawImage();
			if (repeat)	setBackground(Color.darkGray);
			else if (hover)	setBackground(Color.lightGray);
			else setBackground(Color.white);
		}
		public void mousePressed(MouseEvent e)	{
			repeat = !repeat;
			repaint();
		}
		public void mouseEntered(MouseEvent e)	{
			hover = true;
			repaint();
		}
		public void mouseExited(MouseEvent e)	{
			hover = false;
			repaint();
		}
	}*/
	public double clipPosition()	{
		try	{
			return clip.getMicrosecondPosition();
		}
		catch (java.lang.NullPointerException e)	{	return 0;	}
	}
	public boolean clipActive()	{
		try {
			return clip.isActive();
		}
		catch (java.lang.NullPointerException e)	{	return false;	}
	}
	private class AudioTimer implements ActionListener	{
		public void actionPerformed(ActionEvent e) {
			if (!(clipActive())) {
				if (repeatButton.isSelected())	repeat = true;
				else   repeat = false;
				audioTimer.stop();
				if (repeat)	changeSong(0);
				else changeSong(1);
			}
			timeString = getTimeString((int)(clipPosition() / 1E6));
			int ts = timeInSeconds;
			timeLeftString = getTimeString((int)(audioLength/1E6 - timeInSeconds));
			timeInSeconds = ts;
			repaint();
		}
	}
	private class LengthBar extends TemplatePanel	{
		private Graphics g;
		private int barWidth;
		private boolean getBarWidth = true;
		private double st;	//start time
		public void paintComponent(Graphics G)	{
			super.paintComponent(G);
			g = G;
			drawBar();
		}
		public void drawBar()	{
			if (getBarWidth)	barWidth = 	(int)(timeInSeconds *(450.0 / (audioLength / 1E6)));
			g.setColor(Color.red);
			g.fillRect(25, 1, barWidth, 18);
			g.setColor(Color.blue);
			g.drawRect(25, 1, 450, 18);
		}
		public void mouseReleased(MouseEvent e)	{
			barWidth(e);
			clipPosition = barWidth / (450.0 / (audioLength / 1E6)) * 1E6;
			changePosition();			
			getBarWidth = true;
		}
		public void mousePressed(MouseEvent e)	{
			st = System.nanoTime();
			getBarWidth = false;
			stopClip();
			barWidth(e);
			clipPosition = barWidth / (450.0 / (audioLength / 1E6)) * 1E6;
			timeString = getTimeString((int)((st / 1E3 - startTime + clipPosition) / 1E6));
			Repaint();
		}
		public void mouseDragged(MouseEvent e)	{
			barWidth(e);
			clipPosition = barWidth / (450.0 / (audioLength / 1E6)) * 1E6;
			timeString = getTimeString((int)((st / 1E3 - startTime + clipPosition) / 1E6));
			Repaint();
		}
		public void barWidth(MouseEvent e)	{
			barWidth = e.getX() - 25;
			if (barWidth < 0) barWidth = 0;
			if (barWidth > 450) barWidth = 450;
		}
	}
	public String getTimeString(int timeInSeconds)	{
		this.timeInSeconds = timeInSeconds;
		int seconds = timeInSeconds%60;
		int minutes = timeInSeconds / 60;
		if (seconds > 9)	return minutes + ":" + seconds;
		else return minutes + ":0" + seconds;
	}
	public void changeSong(int changeBy)	{
		if (shuffleButton.isSelected())	shuffle = true;
		else   shuffle = false;
		if (changeBy == -1 && timeInSeconds > 10);
		else	songOn+=changeBy;
		if (boxMade)	SongListBox.selectCurrentSong();
		//System.out.println(playlist[songOn] + " " + songOn);
		if (shuffle)	generateSongs();
		else addSongs();
		playSound(musicFiles[playlist[songOn]]);
		if (!(pause))	pause();
		timeInSeconds = 0;
		repaint();
	}
	public void changeToSong(int songIndex)	{
		if (shuffleButton.isSelected())	shuffle = true;
		else   shuffle = false;
		songOn = 0;
		playlist = new int[1];
		playlist[0] = songIndex;
		SongListBox.selectCurrentSong();
		if (shuffle)	generateSongs();
		else addSongs();
		pause = true;
		playSound(musicFiles[playlist[songOn]]);
		timeInSeconds = 0;
		repaint();
	}
	public double playSound(String soundLocation) {
		//soundLocation = "music/Led Zepplin-Stairway to Heaven (best quality).wav";
		try	{
			clip.stop();
		}	catch (NullPointerException e)	{}
		try	{
			clip.close();
		}	catch (NullPointerException e)	{	}
		try {										// recycles memory from previous clip
			clip = null;
			Runtime.getRuntime().gc();
		}	catch (NullPointerException e) {}

		try {
			audioTimer.stop();
		}	catch (NullPointerException e)	{}
		audioTimer.start();
    	try {
			audioInputStream = audiosystem.getAudioInputStream(new File(soundLocation).getAbsoluteFile()); 
			clip = audiosystem.getClip();
			try	{
				clip.open(audioInputStream);
			}
			catch (IllegalStateException e)	{
				System.err.println("Clip already open");
			}
			catch (OutOfMemoryError e)	{
				System.err.println("Slow it down!");
				playSound(soundLocation);
				//System.exit(5);
			}
			if (pause)	clip.start();
			else clipPosition = 0;
    	    audioLength = clip.getMicrosecondLength();
    	    initialPosition = 0;
    	    startTime = System.nanoTime()/1E3;
    	    endTimeString = getTimeString((int)(audioLength/1E6));
    	} catch(Exception ex) {
    	    System.out.println("Error with playing sound.");
    	    System.out.println(soundLocation);
    	    ex.printStackTrace();
    	}
    	return audioLength;
	}
	public void Repaint()	{
		repaint();
	}
	public void stopClip()	{
		clip.stop();
		audioTimer.stop();
	}
	public void changePosition()	{
		clip.setMicrosecondPosition((long)clipPosition);
		initialPosition = clipPosition;
		if (!(pause))	return;
		startTime = System.nanoTime()/1E3;
		clip.start();
		audioTimer.start();
	}
	public void pauseUnpause()	{
		if (pause) {
			clip.setMicrosecondPosition((long)clipPosition);
			clip.start();
			initialPosition = clipPosition;
			startTime = System.nanoTime()/1E3;
			audioTimer.start();
		}
		else	{
			try	{
				clip.stop();
				clipPosition = System.nanoTime()/1E3 - startTime + initialPosition;
				audioTimer.stop();
			}
			catch(NullPointerException ev)	{System.err.println("nullPointer");}
		}
	}
	public void pause()	{
		try	{
				clip.stop();
				audioTimer.stop();
			}
			catch(NullPointerException ev)	{System.err.println("nullPointer");}
	}
	public FlowLayout flowLayout;
	private JPanel top, center, scrollbar, lengthBar;
	public ImageIcon repeatIcon, shuffleIcon, timeLeftIcon, songListIcon;
	public JLabel songName;
	public boolean pause = true, repeat = false, shuffle = false;
	public double clipPosition;
	public double startTime, initialPosition;
	public Timer audioTimer = new Timer(100, new AudioTimer());
	public double audioLength;
	public String timeString, endTimeString, timeLeftString;
	public JToggleButton repeatButton, shuffleButton, timeLeftButton;
	public JButton songList;
	public int timeInSeconds;
	public ComboBox SongListBox;
	public boolean boxMade = false;
}