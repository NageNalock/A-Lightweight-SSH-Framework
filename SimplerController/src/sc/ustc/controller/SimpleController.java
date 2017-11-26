package sc.ustc.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SimpleController extends HttpServlet {
    @Override
    public void init() throws ServletException {
        System.out.println("SimpleController启动");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");

        PrintWriter out = resp.getWriter();

        out.println("<html>");
        out.println("   <head>");
        out.println("       <title>SimpleController</title>");
        out.println("   </head>");
        out.println("   <body>欢迎使用SimpleController!</body>");
        out.println("</html>");

        out.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
