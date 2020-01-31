package cn.yi.bookstore.util.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //处理响应编码
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String methodName = request.getParameter("method");
        try {
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            String result = (String) method.invoke(this, request, response);
            if (result != null && !result.isEmpty()) {
                int colon = result.indexOf(":");
                //默认结果，使用转发
                if (colon == -1) {
                    request.getRequestDispatcher(result).forward(request, response);
                    return;
                }
                //转发或重定向
                String action = result.substring(0, colon);
                String address = result.substring(colon + 1);
                if (action.equals("f")) {
                    request.getRequestDispatcher(address).forward(request, response);
                } else if (action.equals("r")) {
                    response.sendRedirect(address);
                } else {
                    throw new RuntimeException("只支持转发（f）或重定向（r）！");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
