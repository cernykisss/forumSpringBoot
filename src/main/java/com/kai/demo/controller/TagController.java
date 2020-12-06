package com.kai.demo.controller;

import com.kai.demo.cache.HotTagCache;
import com.kai.demo.dto.QuestionDTO;
import com.kai.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class TagController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private HotTagCache hotTagCache;

    @GetMapping("/tag/{tag}")
    public String tag(@PathVariable("tag") String tag,
                      Model model) {
//        List<String> hots = hotTagCache.getHots();
        List<QuestionDTO> questions = questionService.getByTag(tag);
        model.addAttribute("questions", questions);
        model.addAttribute("tag", tag);
        return "tag";
    }
}
