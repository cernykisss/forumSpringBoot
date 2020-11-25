package com.kai.demo.mapper;


import com.kai.demo.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question" +
            "(title, description, gmt_create, gmt_modified, creator) " +
            "values(#{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{creator})")
    void createQuestion(Question question);


//    @Select("select * from question")
//    List<Question> list();


    @Select("select * from question limit #{begin}, #{size}")
    List<Question> list(@Param("begin") Integer begin,
                        @Param("size") Integer size);

    @Select("select count(1) from question")
    Integer count();
}
