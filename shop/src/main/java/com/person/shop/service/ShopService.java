package com.person.shop.service;

import com.person.shop.bean.Article;
import com.person.shop.bean.ArticleType;

import java.util.List;
import java.util.Map;

public interface ShopService {


    List<ArticleType> getArticleTypes();

    Map<String, Object> login(String name, String password);

    List<ArticleType> getFirstArticleTypes();

    List<Article> getArticles(String codeType,String secondType,String title);

    List<ArticleType> getSecondeArticleTypes(String code);

    void deleteById(String id);
    Article preArticle(int id);

    void update(Article article);

    void saveArticle(Article article);
}
