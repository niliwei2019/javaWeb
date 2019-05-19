package com.person.shop.action;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.jspsmart.upload.*;
import com.person.shop.bean.Article;
import com.person.shop.bean.ArticleType;
import com.person.shop.service.ShopService;
import org.apache.taglibs.standard.lang.jstl.test.PageContextImpl;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.servlet.jsp.PageContext;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "listServlet" ,urlPatterns = "/list")
public class listServlet extends HttpServlet {
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

        request.setCharacterEncoding("UTF-8");
        this.request=request;
        this.response=response;
        String method=request.getParameter("method");
        switch (method){
            case "getAll":
                getAll();
                break;
            case "deleteById":
                deleteById();
                break;
            case "preArticle":
                preArticle();
                break;
            case "showUpdate":
                showUpdate();
                break;
            case "updateArticle":
                updateArticle();
                break;
            case "addArticle":
                addArticle();
                break;

        }

    }

    private void addArticle() {
        try {
            SmartUpload su=new SmartUpload();
            su.initialize(this.getServletConfig(),request,response);
            su.setMaxFileSize(1*1024*1024);
            su.setTotalMaxFileSize(10*1024*1024);
            su.setAllowedFilesList("jpg,png,JPG");
            su.upload();
            Request tRequest =su.getRequest();


            String code = tRequest.getParameter("code");
            String title = tRequest.getParameter("titleStr");
            String supplier = tRequest.getParameter("supplier");
            String locality = tRequest.getParameter("locality");
            String price = tRequest.getParameter("price");
            String storage = tRequest.getParameter("storage");
            String description = tRequest.getParameter("description");
            String picUrl = tRequest.getParameter("picUrl"); // 物品旧封面
            String putawayDate = tRequest.getParameter("putawayDate");

            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Article article=new Article();
            article.setPutawayDate(sdf.parse(putawayDate));

            Files files=su.getFiles();
            String fileName=files.getFile(0).getFileName();
            files.getFile(0).saveAs("/resources/images/article/"+fileName,File.SAVEAS_VIRTUAL);

            if(!StringUtils.isEmpty(fileName)) {
                article.setImage(fileName);
            }
            ArticleType type = new ArticleType();
            type.setCode(code);
            article.setArticleType(type);
            article.setTitle(title);
            article.setSupplier(supplier);
            article.setLocality(locality);
            article.setPrice(Double.parseDouble(price));
            article.setStorage(Integer.parseInt(storage));
            article.setDescription(description);
            shopService.saveArticle(article);
            request.setAttribute("tip","添加商品成功");
            getAll(tRequest);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SmartUploadException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void updateArticle() {
//        smartUpload.getRequest().getParameter("")
//        SmartUpload su=new SmartUpload().upload();
        try{
        SmartUpload su=new SmartUpload();
        su.initialize(this.getServletConfig(),request,response);
        su.setMaxFileSize(1*1024*1024);
        su.setTotalMaxFileSize(10*1024*1024);
        su.setAllowedFilesList("jpg,png,JPG");
        su.upload();
        Request tRequest =su.getRequest();

        String code = tRequest.getParameter("code");
        String title = tRequest.getParameter("titleStr");
        String supplier = tRequest.getParameter("supplier");
        String locality = tRequest.getParameter("locality");
        String price = tRequest.getParameter("price");
        String storage = tRequest.getParameter("storage");
        String description = tRequest.getParameter("description");
        int id = Integer.valueOf(tRequest.getParameter("id")); // 物品编号

        Files files=su.getFiles();
        String fileName=files.getFile(0).getFileName();
        //下载成功了
        files.getFile(0).saveAs("/resources/images/article/"+fileName, File.SAVEAS_VIRTUAL);

        Article article= new Article();

        ArticleType type = new ArticleType();
        type.setCode(code);
        if(!StringUtils.isEmpty(fileName)) {
            article.setImage(fileName);
        }
        article.setId(id);
        article.setArticleType(type);
        article.setTitle(title);
        article.setSupplier(supplier);
        article.setLocality(locality);
        article.setPrice(Double.parseDouble(price));
        article.setStorage(Integer.parseInt(storage));
        article.setDescription(description);

        shopService.update(article);
        request.setAttribute("tip","修改成功");
        request.setAttribute("article",article);
        showUpdate(tRequest);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    private void showUpdate() throws ServletException, IOException {
        int id=Integer.parseInt(request.getParameter("id"));
        System.out.print("showUpdate"+id);
        Article article=shopService.preArticle(id);
        List<ArticleType> types=shopService.getArticleTypes();
        request.setAttribute("article",article);
        request.setAttribute("types",types);
        request.getRequestDispatcher("WEB-INF/jsp/updateArticle.jsp").forward(request,response);
    }

    private void showUpdate(Request trequest) throws ServletException, IOException {
        int id=Integer.parseInt(trequest.getParameter("id"));
        System.out.print("showUpdate"+id);
        Article article=shopService.preArticle(id);
        List<ArticleType> types=shopService.getArticleTypes();
        request.setAttribute("article",article);
        request.setAttribute("types",types);
        request.getRequestDispatcher("WEB-INF/jsp/updateArticle.jsp").forward(request,response);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    private void preArticle() throws ServletException, IOException {
        int id=Integer.parseInt(request.getParameter("id"));
        System.out.print("perArticle"+id);
        Article article=shopService.preArticle(id);
        request.setAttribute("article",article);
        request.getRequestDispatcher("WEB-INF/jsp/preArticle.jsp").forward(request,response);
    }
    private void deleteById() throws ServletException, IOException {
        try {
            String id=request.getParameter("id");
            shopService.deleteById(id);
            request.setAttribute("tip","删除成功");
        } catch (Exception e) {
            request.setAttribute("tip","删除失败");
            e.printStackTrace();
        }
        request.getRequestDispatcher("/list?method=getAll").forward(request,response);
    }

    private void getAll() throws ServletException, IOException {
        String code=request.getParameter("typeCode");
        String secondType=request.getParameter("secondType");
        String title=request.getParameter("title");
        String id=request.getParameter("deleteById");
        String pageIndex=request.getParameter("pageIndex");
        if(StringUtils.isEmpty(pageIndex)){
            pageIndex = "1";
        }

        System.out.println(title);
        if(!StringUtils.isEmpty(code)){
            List<ArticleType> articleTypes=shopService.getSecondeArticleTypes(code);
            request.setAttribute("secondType",secondType);
            request.setAttribute("secondTypes",articleTypes);
            request.setAttribute("typeCode",code);
            request.setAttribute("title",title);
        }
        List<ArticleType> firstArticleTypes = shopService.getFirstArticleTypes();
        request.setAttribute("firstArticleTypes", firstArticleTypes);
        PageHelper.startPage(Integer.parseInt(pageIndex),9);
        List<Article> articles = shopService.getArticles(code,secondType,title);
        PageInfo<Article> pageInfo=new PageInfo<Article>(articles);
        request.setAttribute("page",pageInfo);
        request.setAttribute("articles",articles);
        request.setAttribute("articleTypes",shopService.getArticleTypes());
        request.getRequestDispatcher("WEB-INF/jsp/list.jsp").forward(request, response);
    }
    private void getAll(Request tRequest) throws ServletException, IOException {
        String code=tRequest.getParameter("typeCode");
        String secondType=tRequest.getParameter("secondType");
        String title=tRequest.getParameter("title");
        String id=tRequest.getParameter("deleteById");
        String pageIndex=tRequest.getParameter("pageIndex");
        if(StringUtils.isEmpty(pageIndex)){
            pageIndex = "1";
        }

        if(!StringUtils.isEmpty(code)){
            List<ArticleType> articleTypes=shopService.getSecondeArticleTypes(code);
            request.setAttribute("secondType",secondType);
            request.setAttribute("secondTypes",articleTypes);
            request.setAttribute("typeCode",code);
            request.setAttribute("title",title);
        }
        List<ArticleType> firstArticleTypes = shopService.getFirstArticleTypes();
        request.setAttribute("firstArticleTypes", firstArticleTypes);
        PageHelper.startPage(Integer.parseInt(pageIndex),9);
        List<Article> articles = shopService.getArticles(code,secondType,title);
        PageInfo<Article> pageInfo=new PageInfo<Article>(articles);
//        System.out.print();
//        System.out.print();
//        System.out.print(pageInfo);
        request.setAttribute("page",pageInfo);
        request.setAttribute("articles",articles);
        request.setAttribute("articleTypes",shopService.getArticleTypes());
        request.getRequestDispatcher("WEB-INF/jsp/list.jsp").forward(request, response);
    }
}
