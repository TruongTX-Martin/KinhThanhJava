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

public class Main {

	private static final int BACKUP_HEADER_LENGTH = 24;

	public Main() {
//		String mainContent  = getContentFromUrl("http://hoithanhsucsongmoi.blogspot.com/2013/12/oc-kinh-thanh-cuu-uoc-va-tan-uoc.html");
//		String listName = mainContent.substring(mainContent.indexOf("<table><tbody>"), mainContent.indexOf("</tbody></table>"));
////		System.out.println(listName);
//		String cuuUoc = listName.substring(listName.indexOf("Kinh Thánh Cựu Ước"), listName.indexOf("Kinh Thánh Tân Ước"));
//		String tanUoc = listName.substring(listName.indexOf("Kinh Thánh Tân Ước"), listName.length());
//		String[] arrayCuuUoc = tanUoc.split("<span");
//		for (int i=0; i< arrayCuuUoc.length; i++) {
//			String itemCuuUoc = arrayCuuUoc[i];
//			if(itemCuuUoc.contains("<a href=")){
//				String tagA = itemCuuUoc.substring(itemCuuUoc.indexOf("<a"), itemCuuUoc.indexOf("</a>"));
//				System.out.println(tagA);
//				String link = tagA.substring(tagA.indexOf("href=") +6, tagA.indexOf("style=")-2);
//				String name = tagA.substring(tagA.lastIndexOf(">") +1, tagA.length());
//				System.out.println("Link " + i + ":" + link);
//				System.out.println("Name " + i + ":" + name);
//			}
//		}
		String detail = getContentFromUrl("http://hoithanhsucsongmoi.blogspot.com/2013/06/1-leviticus-le-vi-ky.html");
		String option = detail.substring(detail.indexOf("<option>"), detail.indexOf("</select>"));
		System.out.println(option);
		String[] arrayOption = option.split("</option>");
		System.out.println(arrayOption.length-2);
		String content = detail.substring(detail.lastIndexOf("<div align=\"left\" class=\"text\">"), detail.indexOf("<br /></div>"));
		String[] arrayContent = content.split("<span");
		System.out.println(arrayContent.length);
		for (int i=0; i< arrayContent.length; i++) {
			String itemContent = arrayContent[i];
			if(itemContent.contains("class=\"verse\" id=")) {
				String[] arrayItem = itemContent.split("</span>");
				if(arrayItem[1].contains("<br />")){
					arrayItem[1] = arrayItem[1].replace("<br />", "");
				}
				System.out.println(arrayItem[1]);
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
