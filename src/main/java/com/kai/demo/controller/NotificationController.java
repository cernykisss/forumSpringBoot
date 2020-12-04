package com.kai.demo.controller;

import com.kai.demo.dto.NotificationDTO;
import com.kai.demo.enums.NotificationTypeEnum;
import com.kai.demo.mapper.NotificationMapper;
import com.kai.demo.model.User;
import com.kai.demo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String notification(@PathVariable(name = "id") Integer id,
                               HttpServletRequest request,
                               Model model) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) return "redirect:/index";
        NotificationDTO notificationDTO = notificationService.read(id, user);
        if (NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType()
                || NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()) {
            return "redirect:/question/" + notificationDTO.getOuterid();
        } else {
            return "redirect:/";
        }
    }


}
