package com.lyq.cookie;


import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class cookieServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //中文乱码问题
        resp.setContentType("text/html;charset=UTF-8");

        Cookie[] cookies = req.getCookies(); //获取cookie
        PrintWriter writer = resp.getWriter();
        writer.print("您上次访问时间为：");
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("lastLoginTime")) {
                    writer.print(cookie.getValue());
                    break;
                }
            }
        } else {
            writer.print("这是您第一次访问！");
        }
        Cookie cookie = new Cookie("lastLoginTime", LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE));
        // 负值表示cookie不会持久存储，并且在Web浏览器退出时将被删除
        // cookie.setMaxAge(-6);
        // 零值将导致cookie被删除。可以利用此方法删除对应cookie
        // cookie.setMaxAge(0);
        // 正值表示cookie将在经过许多秒后过期。请注意，该值为Cookie到期的最长期限，而不是Cookie的当前期限
        cookie.setMaxAge(24 * 60 * 60);
        resp.addCookie(cookie);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
