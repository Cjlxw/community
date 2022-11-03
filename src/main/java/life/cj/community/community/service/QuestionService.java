package life.cj.community.community.service;

import life.cj.community.community.dto.PaginationDTO;
import life.cj.community.community.dto.QuestionDTO;
import life.cj.community.community.exception.CustomizeErrorCode;
import life.cj.community.community.exception.CustomizeException;
import life.cj.community.community.mapper.QuestionMapper;
import life.cj.community.community.mapper.UserMapper;
import life.cj.community.community.mapper.UsersMapper;
import life.cj.community.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cj
 * @apiNote TODO
 * @date 2022/9/22 14:00
 */
@Service
public class QuestionService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public PaginationDTO list(Page page) {
        Integer count = (int) questionMapper.countByExample(new QuestionExample());
        if (page.getPageNum() < 1) {
            page.setPageNum(1);
        }
        Integer size = count % page.getPageSize() == 0 ?
                count / page.getPageSize() :
                count / page.getPageSize() + 1;
        if (page.getPageNum() > size) {
            page.setPageNum(size);
        }
        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("gmt_create desc");
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds((page.getPageNum() - 1) * page.getPageSize(),page.getPageSize()));
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();

        for (Question question : questionList) {
            UsersExample usersExample = new UsersExample();
            usersExample.createCriteria().
                    andIdEqualTo(question.getCreator());
            Users user = usersMapper.selectByExample(usersExample).get(0);
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setUser(user);
            BeanUtils.copyProperties(question,questionDTO);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setList(questionDTOList);
        paginationDTO.setParam(count, page);
        return paginationDTO;
    }

    public PaginationDTO list(Integer userId, Page page) {
        Integer count = (int) questionMapper.countByExample(new QuestionExample());
        if (page.getPageNum() < 1) {
            page.setPageNum(1);
        }
        Integer size = count % page.getPageSize() == 0 ?
                count / page.getPageSize() :
                count / page.getPageSize() + 1;
        if (page.getPageNum() > size) {
            page.setPageNum(size);
        }
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(userId);
        questionExample.setOrderByClause("gmt_create desc");
        List<Question> questionList =
                questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds((page.getPageNum() - 1) * page.getPageSize(), page.getPageSize()));
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();

        for (Question question : questionList) {
            UsersExample usersExample = new UsersExample();
            usersExample.createCriteria().
                    andIdEqualTo(question.getCreator());
            Users user = usersMapper.selectByExample(usersExample).get(0);
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setUser(user);
            BeanUtils.copyProperties(question,questionDTO);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setList(questionDTOList);
        paginationDTO.setParam(count, page);
        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        UsersExample usersExample = new UsersExample();
        usersExample.createCriteria().
                andIdEqualTo(question.getCreator());
        Users user = usersMapper.selectByExample(usersExample).get(0);
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (ObjectUtils.isEmpty(question.getId())) {
            //create
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        } else {
            //update
            Question newQuestion = new Question();
            newQuestion.setId(question.getId());
            newQuestion.setGmtModified(System.currentTimeMillis());
            newQuestion.setTag(question.getTag());
            newQuestion.setTitle(question.getTitle());
            newQuestion.setDescription(question.getDescription());
            int update = questionMapper.updateByPrimaryKeySelective(newQuestion);
            if (update != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }
}
