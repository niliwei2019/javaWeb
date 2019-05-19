package com.person.shop.action;

import com.person.shop.service.ShopService;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "loginServlet",urlPatterns = "/loginServlet")
public class loginServlet extends HttpServlet {

    ShopService shopService;

    HttpServletRequest request;
    HttpServletResponse response;

    @Override
    public void init() throws ServletException {
        //servelet容器不是Spring管理，所以不能用AutoWeird自动注入。
        //先获取spring容易，在获取bean类
        ServletContext servletContext=this.getServletContext();
        WebApplicationContext webApplicationContext= WebApplicationContextUtils.getWebApplicationContext(servletContext);
        shopService=(ShopService)webApplicationContext.getBean("shopService");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.request=request;
        this.response=response;
        String method=request.getParameter("method");
        switch (method){
            case "redirect":
                request.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(request,response);
                break;
            case "login":
                checkUser();
                break;
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    private void checkUser() throws ServletException, IOException {
        String name=request.getParameter("loginName");
        String password=request.getParameter("passWord");
        Map<String,Object> result= shopService.login(name,password);
        if(result.get("code").equals("0")){
            request.setAttribute("user",result.get("msg"));
//            request.getRequestDispatcher("WEB-INF/jsp/list.jsp").forward(request,response);
            response.sendRedirect("list?method=getAll");
        }
        else{
            request.setAttribute("msg",result.get("msg"));
            request.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(request,response);
        }
    }
}
