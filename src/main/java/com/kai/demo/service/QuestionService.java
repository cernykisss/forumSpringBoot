package com.kai.demo.service;

import com.kai.demo.dto.PaginationDTO;
import com.kai.demo.dto.QuestionDTO;
import com.kai.demo.mapper.QuestionMapper;
import com.kai.demo.mapper.UserMapper;
import com.kai.demo.model.Question;
import com.kai.demo.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

//    public List<QuestionDTO> list() {
//        List<Question> questions = questionMapper.list();
//        List<QuestionDTO> questionDTOS = new ArrayList<>();
//        for (Question question : questions) {
//            User user = userMapper.findById(question.getCreator());
//            QuestionDTO questionDTO = new QuestionDTO();
//            //快速拷贝属性值
//            BeanUtils.copyProperties(question, questionDTO);
//            questionDTO.setUser(user);
//            questionDTOS.add(questionDTO);
//        }
//        return questionDTOS;
//    }

    public PaginationDTO list(Integer page, Integer size) {
        List<Question> questions = questionMapper.list((page - 1) * size, size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOS);
        Integer totalCount = questionMapper.count();
        paginationDTO.setPagination(totalCount, page, size);
        return paginationDTO;
    }
}
