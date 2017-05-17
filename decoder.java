package main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class decoder {

	public static void main(String[] args) throws IOException {
		InputStream is;
		try {
			is = new FileInputStream("/home/mateusz/ml/joyce/text.txt");
			BufferedReader buf = new BufferedReader(new InputStreamReader(is)); 
			String line = buf.readLine(); StringBuilder sb = new StringBuilder(); 
			while(line != null){ 
				sb.append(line).append("\n"); 
			line = buf.readLine(); 
			}
			String fileAsString = sb.toString();
			buf.close();
			//fileAsString = fileAsString.replace("\n", "").replace("\r", "");
			fileAsString = fileAsString.replace("", "^");
			
		    char[] ch  = fileAsString.toCharArray();
		    String encrypted = "";
		    for(char c : ch)
		    {
		        int temp = (int)c;
		        if(temp!=94) {
		        	encrypted+=temp;
		        } else {
		        	encrypted+=c;
		        }
		        System.out.println(c);
		    }
		    System.out.print(encrypted);
		    fileAsString = encrypted.replace("^", ",").trim();
		    fileAsString = fileAsString.substring(1);
			String fileAsStringY = fileAsString;
			fileAsStringY+="40,";
			int index = fileAsStringY.indexOf(",");
			fileAsStringY = fileAsStringY.substring(index+1);
			PrintWriter out = new PrintWriter("/home/mateusz/ml/joyce/num.csv");
			 out.println(fileAsString);
			 out.close();
			PrintWriter outy = new PrintWriter("/home/mateusz/ml/joyce/numy.csv");
			 outy.println(fileAsStringY);
			 outy.close(); 
			 
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 			
	}
}
