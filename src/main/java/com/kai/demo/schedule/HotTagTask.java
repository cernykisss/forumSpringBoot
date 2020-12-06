package com.kai.demo.schedule;

import com.kai.demo.cache.HotTagCache;
import com.kai.demo.mapper.QuestionMapper;
import com.kai.demo.model.Question;
import com.kai.demo.model.QuestionExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class HotTagTask {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private HotTagCache hotTagCache;

    @Scheduled(fixedRate = 20000)
    public void hotTagSchedule() {
        int offset = 0;
        int limit = 20;
        List<Question> list = new ArrayList<>();
        Map<String, Integer> priorities = new HashMap<>();
        while (offset == 0 || list.size() == limit) {
            QuestionExample example = new QuestionExample();
            list = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, limit));
            for (Question question : list) {
                String[] tags = StringUtils.split(question.getTag(), ",");
                if (tags == null) continue;
                for (String tag : tags) {
                    if (priorities.containsKey(tag)) {
                        Integer priority = priorities.get(tag);
                        priorities.put(tag, priority + 5 + question.getCommentCount());
                    } else {
                        priorities.put(tag, 5 + question.getCommentCount());
                    }
                }
            }
            offset += limit;
        }
        hotTagCache.updateTags(priorities);
    }
}
