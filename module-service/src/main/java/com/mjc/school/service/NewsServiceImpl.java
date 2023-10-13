package com.mjc.school.service;

import com.mjc.school.dto.NewsDTO;
import com.mjc.school.exception.DoubleAdding;
import com.mjc.school.exception.NotExistThisId;
import com.mjc.school.exception.NotNewDataToUpdate;
//import com.mjc.school.mapping.NewsMapper;
import com.mjc.school.repository.datasource.DataSource;
import com.mjc.school.repository.datasource.impl.AuthorImpl;
import com.mjc.school.repository.datasource.impl.NewsRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.validate.Validator;

import java.time.LocalDateTime;
import java.util.List;

public class NewsServiceImpl implements NewsService{
//    private final NewsMapper newsMapper = NewsMapper.INSTANCE;
    private final DataSource dataSource1 = new AuthorImpl();
    private final DataSource dataSource2 = new NewsRepository();


//    // Example method for mapping NewsModel to NewsDTO
//    public NewsDTO mapNewsToDTO(NewsModel newsModel, Author author) {
//        return newsMapper.newsToNewsDTO(newsModel, author);
//    }

    @Override
    public NewsDTO createNews(String title, String content, String authorId) throws NotExistThisId {
        Validator validator = new Validator();
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setTitle(title);
        newsDTO.setContent(content);
        newsDTO.setAuthorId(Long.valueOf(authorId));
        newsDTO.setCreateDate(LocalDateTime.now());
        Author author = (Author) dataSource1.getById(Long.parseLong(authorId));
        newsDTO.setAuthorName(author.getName());
        validator.validator(newsDTO);
        try {
            dataSource2.save(newsDTO);
        } catch (DoubleAdding e) {
            throw new RuntimeException(e);
        }
        return newsDTO;
    }

    @Override
    public List<NewsDTO> getAllNews() {
        return dataSource2.getAll();
    }

    @Override
    public NewsDTO getNewsById(String id){
        Long idl = Long.valueOf(id);
        try {
            return (NewsDTO) dataSource2.getById(idl);
        } catch (NotExistThisId e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object updateNews(String id, String title, String content, String authorId) throws NotExistThisId, NotNewDataToUpdate {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setTitle(title);
        newsDTO.setContent(content);
        newsDTO.setAuthorId(Long.valueOf(authorId));
        newsDTO.setId(Long.parseLong(id));
        newsDTO.setCreateDate(getAllNews().get(Integer.parseInt(id)).getCreateDate());
        newsDTO.setLastUpdateDate(LocalDateTime.now());
        Author author = (Author) dataSource1.getById(Long.parseLong(authorId));
        newsDTO.setAuthorName(author.getName());
        dataSource2.update(newsDTO);
        return newsDTO;
    }

    @Override
    public boolean deleteNews(String id) throws NotExistThisId {
        dataSource2.delete(Long.parseLong(id));
        return true;
    }

    @Override
    public List getAllAuthors() {
        return dataSource1.getAll();
    }
}
