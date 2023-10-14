package com.mjc.school.controller.impl;

import com.mjc.school.controller.NewsController;
import com.mjc.school.dto.NewsDto;
import com.mjc.school.exception.NotExistThisId;
import com.mjc.school.exception.NotNewDataToUpdate;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.impl.NewsServiceImpl;
import com.mjc.school.validate.NewsValidator;

import java.io.IOException;
import java.util.List;

public class NewsControllerImpl implements NewsController<NewsDto> {
    NewsService<NewsDto> service = new NewsServiceImpl();

    @Override
    public NewsDto createNews(String title, String content, String authorId) throws NotExistThisId, IOException {
        NewsValidator validator = new NewsValidator();
        NewsDto newsDTO = service.createNews(title,content, authorId);
        validator.validator(newsDTO);
        return newsDTO;
    }

    @Override
    public List<NewsDto> getAllNews() throws IOException {
        return service.readAllNews();
    }

    @Override
    public NewsDto getNewsById(String id) {
        return service.readByIdNews(id);
    }

    @Override
    public NewsDto updateNews(String id, String title, String content, String authorId) throws NotNewDataToUpdate, NotExistThisId, IOException {
        return service.updateNews(id,title, content, authorId);
    }

    @Override
    public Boolean deleteNews(String id) throws NotExistThisId, IOException {
        return service.deleteNews(String.valueOf(Long.valueOf(id)));
    }
}
