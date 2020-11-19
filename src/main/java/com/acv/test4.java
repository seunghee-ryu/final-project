package com.acv;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class test4 {

  public static void main(String[] args) throws IOException {
    Document doc = Jsoup.connect("https://movie.naver.com/movie/bi/mi/basic.nhn?code=136900").get();

    Elements image = doc.select("img");
    String url =

        if (url.equalsIgnoreCase("movie-phinf")) {

          System.out.println(url);
        }
  }
}