import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JOptionPane;

public class Main {
	
	public static void main(String[] args){
		String path = GetExecutionPath();
		File pathfolder = new File(path);
		File filesfolder = new File(pathfolder, "insert_files");
		
		if(pathfolder.exists() && pathfolder.isDirectory()) {
			if(!filesfolder.exists()) {
				filesfolder.mkdirs();
				JOptionPane.showMessageDialog(null, "Folder was created. Insert all your files into it.");
				System.exit(0);
			}
			if(filesfolder.listFiles().length == 0) {
				JOptionPane.showMessageDialog(null, "Folder is empty.");
				return;
			} else {
				File[] files = filesfolder.listFiles();
				Set<String> lines = new HashSet<>();
				for(File f : files) {
					System.out.println(f.getAbsolutePath());
				}
				for(File f : files) {
					try (BufferedReader br = new BufferedReader(new FileReader(f))) {
						for (String line; (line = br.readLine()) != null;) {
							lines.add(line);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				JOptionPane.showMessageDialog(null, "Loaded " + lines.size() + " lines");
				String input = JOptionPane.showInputDialog("Enter file ending without (.)");
				if(input == null) {
					input = "txt";
				}
				try {
					PrintStream w = new PrintStream(filesfolder.getAbsolutePath() + "//output." + input);
					for(String s : lines) {
						w.println(s);
					}
					w.close();
					JOptionPane.showMessageDialog(null, lines.size() + " lines left.");
					JOptionPane.showMessageDialog(null, "Program finished.");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}
	
	
	private static String GetExecutionPath(){
	    String absolutePath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	    absolutePath = absolutePath.substring(0, absolutePath.lastIndexOf("/"));
	    absolutePath = absolutePath.replaceAll("%20"," "); // Surely need to do this here
	    return absolutePath;
	}

}
