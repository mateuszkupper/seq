import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class encoder {
	public static void main(String[] args) throws IOException {
        try {
            BufferedReader br = new BufferedReader( new FileReader("/home/mateusz/ml/joyce/num.txt"));
            String strLine = null;
            StringTokenizer st = null;
            int lineNumber = 0;
            int tokenNumber = 0;
            List<Integer> list = new ArrayList<Integer>();
            String line;
            while((line = br.readLine()) != null) {
                lineNumber++;
                String[] result = line.split(",");
                for (int x=0; x<result.length; x++) {
                    list.add(Integer.parseInt(result[x]));
                }
                System.out.println(line);
            }
            String text = "";
            for(int token : list) {
            	text += Character.toString ((char) token);
            	System.out.println(Character.toString ((char) token));
            }
			PrintWriter out = new PrintWriter("/home/mateusz/ml/joyce/text-dec.txt");
			 out.println(text);
			 out.close();            
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
