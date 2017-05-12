package classificador;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;

public class PreProcesso {
	
	public static void main(String[] args) throws IOException {
        URL url = null;
        
        ArrayList<String> listG = getPageText("src\\classificador\\urlsBase\\gLinksToVisit.txt");
        ArrayList<String> listB = getPageText("src\\classificador\\urlsBase\\bLinksToVisit.txt");
        		
        for (int i = 0; i < listG.size(); i++) {
        	try {
            	url = new URL(listG.get(i));
                getPage(url, "src\\classificador\\Examples\\Positives\\PosDoc"+i);
            
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
        
        for (int i = 0; i < listG.size(); i++) {
        	try {
        		url = new URL(listG.get(i));
                getPage(url, "src\\classificador\\Examples\\Negatives\\NegDoc"+i);
            
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
        System.out.println("Capturados e armazenados os exemplos positivos e negativos para a base...");
    }
	// Fun��o pra varrer os arquivos com as urls Bases e retornar um ArrayList com cada Url
	public static ArrayList<String> getPageText(String path) throws IOException{
		ArrayList<String> array = new ArrayList();
		FileReader file = new FileReader(path); 
		BufferedReader leitor = new BufferedReader(file);
		
		String line;
		while((line = leitor.readLine()) != null && (!line.equals(""))){
			array.add(line);
		}
		return array;
	}
	
	// Recebe a url que deseja capturar, retorna a p�gina Html, faz o parser pra string e armazena em um novo arquivo
	public static void getPage(URL url,String path) throws IOException {
        
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
        
        String content = "";
        try {
	        StringBuilder sb = new StringBuilder();
	        String line = in.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = in.readLine();
	        }
	        content = sb.toString();
	    } finally {
	        in.close();
	    }
        
        String document = Jsoup.parse(content).body().text();
        
        PrintWriter writer = new PrintWriter(path, "UTF-8");
		writer.println(document);
   
        in.close();
        writer.flush();
        writer.close();
    }

}
