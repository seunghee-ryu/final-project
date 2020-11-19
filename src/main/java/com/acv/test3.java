package com.acv;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class test3 {

  public static void main(String[] args) throws IOException {
    Document doc = Jsoup.connect("https://movie.naver.com/movie/bi/mi/basic.nhn?code=136900").get();

    // 이미지 파일
    Set<String> links = new HashSet<String>();

    Iterator<Element> iterator = doc.select("dl.step2").iterator();
    System.out.println(iterator.next().select("a").text());

    Elements elements = doc.select("img[src]");
    for (Element e : elements) {
      links.add(e.attr("src"));
    }


    System.out.println(links);

  }
}


