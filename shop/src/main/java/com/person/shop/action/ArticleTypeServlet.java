package com.person.shop.action;

import com.person.shop.bean.ArticleType;
import com.person.shop.service.ShopService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet( name = "ArticleTyPeServlet" ,urlPatterns = "/ArticleType")
public class ArticleTypeServlet extends HttpServlet {
    ShopService shopService;

    @Override
    public void init() throws ServletException {
        //servelet容器不是Spring管理，所以不能用AutoWeird自动注入。
        //先获取spring容器，在获取bean类
        ServletContext servletContext=this.getServletContext();
        WebApplicationContext webApplicationContext= WebApplicationContextUtils.getWebApplicationContext(servletContext);
        shopService=(ShopService)webApplicationContext.getBean("shopService");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        System.out.println("request come");
        List<ArticleType> articleTypes=shopService.getArticleTypes();
        System.out.println(articleTypes);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
