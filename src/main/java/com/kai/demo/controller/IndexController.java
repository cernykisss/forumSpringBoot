package com.kai.demo.controller;

import com.kai.demo.cache.HotTagCache;
import com.kai.demo.dto.PaginationDTO;
import com.kai.demo.schedule.HotTagTask;
import com.kai.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private HotTagCache hotTagCache;

    @RequestMapping({"/", "/index"})
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size,
                        @RequestParam(name = "search", required = false) String search) {
        PaginationDTO pagination = questionService.listAllQuestions(search, page, size);
        List<String> tags = hotTagCache.getHots();
        request.getSession().setAttribute("tags", tags);
        model.addAttribute("pagination", pagination);
//        model.addAttribute("tags", tags);
        return "index";
    }
}
