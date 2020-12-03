package com.kai.demo.cache;

import com.kai.demo.dto.TagDTO;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagCache {

    public static List<TagDTO> get() {
        List<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO language = new TagDTO();
        language.setCategoryName("开发语言");
        language.setTags(Arrays.asList("js", "php", "java", "html"));
        TagDTO framework = new TagDTO();
        framework.setCategoryName("开发框架");
        framework.setTags(Arrays.asList("springBoot", "spring", "springMVC"));
        tagDTOS.add(language);
        tagDTOS.add(framework);
        return tagDTOS;
    }

    public static String filterInvalid(String tags) {
        String[] splitTags = StringUtils.split(tags, ",");
        List<TagDTO> allTags = TagCache.get();
        List<String> tagList = allTags.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(splitTags).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }
}
