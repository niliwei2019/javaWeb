package com.person.shop.repository;

import com.person.shop.bean.Article;
import com.person.shop.bean.ArticleType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * ArticleMapper 数据访问类
 * @author xlei @qq 251425887 @tel 13352818008
 * @Email dlei0009@163.com
 * @date 2019-05-09 11:03:02
 * @version 1.0
 */
public interface ArticleMapper {



    @Select("select * from ec_article_type where code=#{da}")
    ArticleType gertArticleType(String id);
//    @Select("select * from ec_article")
    List<Article> getArticles(@Param("codeType") String code, @Param("secondType") String secondType,@Param("title") String title);

    @Select("delete from ec_article where id=#{dasd}")
    void deleteById(int id);

    @Select("select * from ec_article where id=#{dasd}")
    @ResultMap("articleResultMap")
    Article preArticle(int id);

    void update(Article article);

    void saveArticle(Article article);
}