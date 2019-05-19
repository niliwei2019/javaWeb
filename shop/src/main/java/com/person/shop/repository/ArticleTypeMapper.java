package com.person.shop.repository;

import com.person.shop.bean.ArticleType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * ArticleTypeMapper 数据访问类
 * @author xlei @qq 251425887 @tel 13352818008
 * @Email dlei0009@163.com
 * @date 2019-05-09 11:03:02
 * @version 1.0
 */
public interface ArticleTypeMapper {


    @Select("select * from ec_article_type")
    List<ArticleType> getArticleTypes();


    @Select("select * from ec_article_type where LENGTH(CODE)=4")
    List<ArticleType> getFirstArticleTypes();


    List<ArticleType> getSecondTypes(String code);
}