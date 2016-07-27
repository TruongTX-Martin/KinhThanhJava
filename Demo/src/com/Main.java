package com;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

	private static final int BACKUP_HEADER_LENGTH = 24;
	
	private List<String> listTanUoc = new ArrayList<String>();
	private List<String> listCuuUoc = new ArrayList<String>();
	private List<String> listURLException = new ArrayList<String>();
	private String textDoan = "";

	public Main() {
		getMainContent();
		if(listTanUoc.size() > 0) {
			for(int i=0; i< listTanUoc.size(); i++){
				String itemTanUoc = listTanUoc.get(i);
				File file = new File("/home/xuantruong/Documents/KinhThanh/" + getNameFileSave(itemTanUoc) + ".txt");
				if(file.exists()){
					continue;
				}
				saveFileBook(itemTanUoc);
			}
		}
		if(listCuuUoc.size() > 0){
			for(int i=0; i< listCuuUoc.size(); i++){
				String itemCuuUoc = listCuuUoc.get(i);
				File file = new File("/home/xuantruong/Documents/KinhThanh/" + getNameFileSave(itemCuuUoc) + ".txt");
				if(file.exists()){
					continue;
				}
				saveFileBook(itemCuuUoc);
			}
		}
		if(listURLException.size() > 0) {
			for (String urlExcep : listURLException) {
				System.err.println(urlExcep);
			}
		}
//		String url = "http://hoithanhsucsongmoi.blogspot.com/2013/12/11-ma-thi-o.html";
//		saveFileBook(url);
//		String folderURL = getNameFileSave(url);
//		System.out.println(folderURL);
	}
	
	private void saveFileBook(String url) {
		//get Detail content 
				System.out.println("Start Save File=======+++>" + url);
				textDoan = "";
				List<String> listUrl = new ArrayList<String>();
				String detail = getContentFromUrl(url);
				String option = detail.substring(detail.indexOf("<option>"), detail.indexOf("</select>"));
				String[] arrayOption = option.split("</option>");
				//get count doan
				int count = arrayOption.length-2;
				if(count > 0) {
					for(int i=1; i<= count;i++) {
						listUrl.add(getFolderUrl(url)+ i + getNameFileUrl(url));
//						System.out.println("Link" +i + ":"+ getFolderUrl(url)+ i + getNameFileUrl(url));
					}
				}else{
					listUrl.add(url);
				}
				for(int i=0; i< listUrl.size() ; i++) {
					getContent(listUrl.get(i));
				}
	}
	private String getFolderUrl(String url) {
		return url.substring(0, url.lastIndexOf("/") + 1);
	}
	private String getNameFileUrl(String url) {
		String nameFile =  url.substring(url.lastIndexOf("/")+1, url.length());
		return  nameFile.substring(nameFile.indexOf("-"), nameFile.length());
	}
	private String getNameFileSave(String url){
		String nameFile =  url.substring(url.indexOf("-")+1, url.length()-5);
		return  nameFile;
	}
	private void getContent(String url) {
		try {
		System.out.println("Doan");
		textDoan += "phandoan" + "\n";
		String detail = getContentFromUrl(url);
		String content = detail.substring(detail.lastIndexOf("<div align=\"left\" class=\"text\">"), detail.length());
		String[] arrayContent = content.split("<span");
		for (int i=0; i< arrayContent.length; i++) {
			String itemContent = arrayContent[i];
			if(itemContent.contains("class=\"verse\" id=")) {
				String[] arrayItem = itemContent.split("</span>");
				if(arrayItem[1].contains("<br />")){
					arrayItem[1] = arrayItem[1].replace("<br />", "");
				}
				textDoan = textDoan + arrayItem[1] +"\nphancau\n";
				//chia cau o đây 
//				System.out.println("Cau " + i + ":"+arrayItem[1]);
			}
		}
			File file = new File("/home/xuantruong/Documents/KinhThanh/" + getNameFileSave(url) + ".txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(textDoan);
			bw.close();
			System.out.println("Done :" + getNameFileUrl(url));
		} catch (Exception e) {
			System.err.println(e.getMessage() + url);
			listURLException.add(url);
		}
	}
	private void getMainContent(){
		//Get all link Tan Uoc va Cuu Uoc
				String mainContent  = getContentFromUrl("http://hoithanhsucsongmoi.blogspot.com/2013/12/oc-kinh-thanh-cuu-uoc-va-tan-uoc.html");
				String listName = mainContent.substring(mainContent.indexOf("<table><tbody>"), mainContent.indexOf("</tbody></table>"));
//				System.out.println(listName);
				String cuuUoc = listName.substring(listName.indexOf("Kinh Thánh Cựu Ước"), listName.indexOf("Kinh Thánh Tân Ước"));
				String tanUoc = listName.substring(listName.indexOf("Kinh Thánh Tân Ước"), listName.length());
				
				String[] arrayTanUoc = tanUoc.split("<span");
				for (int i=0; i< arrayTanUoc.length; i++) {
					String itemTanUoc = arrayTanUoc[i];
					if(itemTanUoc.contains("<a href=")){
						String tagA = itemTanUoc.substring(itemTanUoc.indexOf("<a"), itemTanUoc.indexOf("</a>"));
						String link = tagA.substring(tagA.indexOf("href=") +6, tagA.indexOf("style=")-2);
						String name = tagA.substring(tagA.lastIndexOf(">") +1, tagA.length());
						listTanUoc.add(link);
					}
					if(itemTanUoc.contains("color:=\"\" href=")){
						String tagA = itemTanUoc.substring(itemTanUoc.indexOf("<a"), itemTanUoc.indexOf("</a>"));
						String link = tagA.substring(tagA.indexOf("color:=\"\" href=") +16, tagA.indexOf("none=")-2);
						String name = tagA.substring(tagA.lastIndexOf(">") +1, tagA.length());
						listTanUoc.add(link);
					}
				}
				
				String[] arrayCuuUoc = cuuUoc.split("<span");
				for (int i=0; i< arrayCuuUoc.length; i++) {
					String itemCuuUoc = arrayCuuUoc[i];
					if(itemCuuUoc.contains("<a href=")){
						String tagA = itemCuuUoc.substring(itemCuuUoc.indexOf("<a"), itemCuuUoc.indexOf("</a>"));
						String link = tagA.substring(tagA.indexOf("href=") +6, tagA.indexOf("style=")-2);
						String name = tagA.substring(tagA.lastIndexOf(">") +1, tagA.length());
//						System.out.println("Link " + i + ":" + link);
//						System.out.println("Name " + i + ":" + name);
						listCuuUoc.add(link);
					}
				}
				if(listTanUoc.size() > 0) {
					for(String key : listTanUoc) {
						System.out.println("Tan uoc:" + key);
					}
				}
				System.out.println("====================================");
				if(listCuuUoc.size() > 0) {
					for(String key : listCuuUoc) {
						System.out.println("Cuu uoc:" + key);
					}
				}
	}

	private String getContentFromUrl(String path) {
		URL url;
		String result = "";
		try {
			url = new URL(path);
			URLConnection conn = url.openConnection();

			// open the stream and put it into BufferedReader
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String inputLine;
			while ((inputLine = br.readLine()) != null) {
				// System.out.println(inputLine);
				result = result + inputLine + "\n";
			}
			br.close();
//			System.out.println(result);
//			File file = new File("/home/xuantruong/Documents/KinhThanh/content.txt");
//			if (!file.exists()) {
//				file.createNewFile();
//			}
//			FileWriter fw = new FileWriter(file.getAbsoluteFile());
//			BufferedWriter bw = new BufferedWriter(fw);
//			bw.write(result);
//			bw.close();
//			System.out.println("Write file done");
//			if (result.contains("Sáng thế ký")) {
//				System.out.println("Sangs thee kys");
//			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		new Main();
	}
}
