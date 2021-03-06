package bitcamp.acv.web.tag;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.acv.service.TagService;


@WebServlet("/tag/multipleDelete")
public class TagMultipleDeleteServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ServletContext ctx = request.getServletContext();
    TagService tagService =
        (TagService) ctx.getAttribute("tagService");

    String[] tagNoList = request.getParameterValues("tags");

    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta http-equiv='Refresh' content='1;list'>");
    out.println("<title>태그 선택 삭제</title></head>");
    out.println("<body>");
    try {
      out.println("<h1>태그 선택 삭제</h1>");

      try {
        int count = 0;

        if (tagNoList != null) {
          for (String tagNo : tagNoList) {
            count += tagService.delete(Integer.parseInt(tagNo));
          }
        }

        if (count == 0) {
          out.printf("<p>해당 태그가 존재하지 않습니다.</p>\n");
        } else {
          out.printf("<p>태그를 삭제하였습니다.</p>\n");
        }
      } catch (Exception e) {
        out.println("태그 삭제 중 오류 발생!");
        e.printStackTrace();
      }

    } catch (Exception e) {
      out.printf("<h2>작업 처리 중 오류 발생!</h2>");
      out.printf("<pre>%s</pre>\n", e.getMessage());

      StringWriter errOut = new StringWriter();
      e.printStackTrace(new PrintWriter(errOut));

      out.printf("<h3>상세 오류 내용</h3>");
      out.printf("<pre>%s</pre>\n", errOut.toString());
    }
    out.println("</body>");
    out.println("</html>");
  }
}
