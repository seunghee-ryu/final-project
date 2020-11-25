package bitcamp.acv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.DriverManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import bitcamp.acv.domain.Movie;
public class App {


  public static void main(String[] args) {

    try (java.sql.Connection con = DriverManager.getConnection(
        "jdbc:mariadb://localhost:3306/studydb?user=study&password=1111")) {
      //      MovieDaoImpl movieDao = new MovieDaoImpl(con);


      File file = new File("./movie.txt");
      try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));) {
        String id = null;
        int num = 1;
        while ((id = in.readLine()) != null) {

          System.out.println(num + " : ["+ id + "]");

          // Jsoup를 이용해서 http://www.cgv.co.kr/movies/ 크롤링
          String url = "https://movie.naver.com/movie/bi/mi/basic.nhn?code=" + id; //크롤링할 url지정
          Document doc = null;        //Document에는 페이지의 전체 소스가 저장된다

          doc = Jsoup.connect(url).get();


          Movie movie = new Movie();

          // 영화제목
          System.out.println("영화 제목 입력");
          movie.setTitle(doc.select("div.mv_info_area").select("div.mv_info").select("div.mv_info").select("h3.h_movie").text().toString());

          // 영화 영문명
          System.out.println("영화 영문제목 입력");
          movie.setEnglishTitle(doc.select("h3.h_movie").select("strong").text());

          // 영화 감독
          System.out.println("영화 감독 입력");
          movie.setDirectors(doc.select("dl.info_spec").select("dd").get(1).select("p").text());

          // 상영시간
          System.out.println("상영시간 입력");
          movie.setRuntime(Integer.parseInt(doc.select("dl.info_spec").select("dd").select("span").get(2).text().replace("분", "")));

          // 개봉일
          System.out.println("개봉일 입력");
          Elements element = doc.select("dl.info_spec").select("dd").select("span").get(3).select("a");
          if (element.size() > 2) {
            movie.setOpenDate(Date.valueOf(element.get(2).text().replace(" ", "").replace(".", "-")+element.get(3).text().replace(" ", "").replace(".", "-")));
          } else if (element.size()  == 2) {
            movie.setOpenDate(Date.valueOf(element.text().replace(" ", "").replace(".", "-")));
          }
          // 출연
          System.out.println("출연진 입력");
          element = doc.select("div.section_group.section_group_frst").select("div.obj_section").select("div.people").select("ul").select("li");

          StringBuilder actors = new StringBuilder();
          for (int i = 1; i < element.size(); i++) {
            actors.append(element.get(i).select("a").attr("title"));
            if (i < element.size() - 1) {
              actors.append(",");
            }
          }
          movie.setActors(actors.toString());

          //    시놉시스
          System.out.println("시놉시스 입력");
          StringBuilder synopsis = new StringBuilder();
          element = doc.select("div.section_group.section_group_frst").select("div.story_area").get(0).select("h5.h_tx_story");
          if (element.size() > 0) {
            synopsis.append(element.get(0).wholeText());
          }
          synopsis.append(doc.select("div.section_group.section_group_frst").select("div.story_area").get(0).select("p.con_tx").get(0).wholeText());
          movie.setSynopsis(synopsis.toString());

          //    국가명
          System.out.println("국가명 입력");
          movie.setNation(doc.select("dl.info_spec").select("dd").select("span").get(1).select("a").text());

          System.out.println();
          System.out.println("*****영화 입력 결과*****");
          System.out.println();
          System.out.print("제목 : ");
          System.out.println(movie.getTitle());
          System.out.print("영문제목 : ");
          System.out.println(movie.getEnglishTitle());
          System.out.print("감독 : ");
          System.out.println(movie.getDirectors());
          System.out.print("상영시간 : ");
          System.out.println(movie.getRuntime());
          System.out.print("개봉일 : ");
          System.out.println(movie.getOpenDate());
          System.out.print("출연진 : ");
          System.out.println(movie.getActors());
          System.out.println();
          System.out.println("[시놉시스]");
          System.out.println(movie.getSynopsis());
          System.out.println();
          System.out.print("국가 : ");
          System.out.println(movie.getNation());

          System.out.println();
          System.out.println("===========================================");

          //movieDao.insert(movie);

          num++;
          if (num == 69) {
            break;
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  } 
}



