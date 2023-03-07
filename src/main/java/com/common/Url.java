package com.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Url {
	public String getHtml(String urlPath, String fmt){
		StringBuffer stringBuffer = new StringBuffer();;
        try{
            URL url = new URL(urlPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream inStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream,fmt));
            String line="";
           
            while ((line = reader.readLine()) !=null ){
                stringBuffer.append(line + "n");
            }
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("取得網頁html時發生錯誤");
        }
        return stringBuffer.toString();
    }
	public void jsoupUrl(String urlPath) throws IOException {
		Document document = Jsoup.connect(urlPath).get();
	}
}
