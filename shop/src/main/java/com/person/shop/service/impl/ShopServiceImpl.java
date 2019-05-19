package com.person.shop.service.impl;

import com.github.pagehelper.PageHelper;
import com.person.shop.bean.Article;
import com.person.shop.bean.ArticleType;
import com.person.shop.bean.User;
import com.person.shop.repository.ArticleMapper;
import com.person.shop.repository.ArticleTypeMapper;
import com.person.shop.repository.UserMapper;
import com.person.shop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("shopService")
public class ShopServiceImpl implements ShopService {

    @Resource
    UserMapper userMapper;
    @Resource
    ArticleMapper articleMapper;
    @Override
    public Map<String, Object> login(String name, String password) {
        Map<String,Object> result = new HashMap<String,Object>();
        if(StringUtils.isEmpty(name) || StringUtils.isEmpty(password)){
            result.put("code",-1);
            result.put("msg","参数不完整");
        }
        else {
            User user=userMapper.login(name);
            if(user.getPassword().equals(password)){
                result.put("code","0");
                result.put("msg",user);
            }
            else {
                result.put("code","1");
                result.put("msg","密码不正确");
            }
        }
        return result;
    }

    @Resource
    ArticleTypeMapper articleTypeMapper;

    @Override
    public List<ArticleType> getFirstArticleTypes() {
        List<ArticleType> articleTypes=articleTypeMapper.getFirstArticleTypes();
        return articleTypes;
    }
    @Override
    public List<ArticleType> getSecondeArticleTypes(String code) {
        return articleTypeMapper.getSecondTypes(code);
    }

    @Override
    public void deleteById(String id) {
        int id1=Integer.parseInt(id);
        articleMapper.deleteById(id1);
    }

    @Override
    public List<Article> getArticles(String codeType,String secondType,String title) {
        return articleMapper.getArticles(codeType,secondType,title);
    }

    @Override
    public void saveArticle(Article article) {
        articleMapper.saveArticle(article);
    }

    @Override
    public Article preArticle(int id) {
        return  articleMapper.preArticle(id);
    }

    @Override
    public void update(Article article) {
        articleMapper.update(article);
    }

    @Override
    public List<ArticleType> getArticleTypes() {
        return  articleTypeMapper.getArticleTypes();
    }
}
