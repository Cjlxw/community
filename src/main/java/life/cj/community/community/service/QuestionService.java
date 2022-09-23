package life.cj.community.community.service;

import life.cj.community.community.dto.PaginationDTO;
import life.cj.community.community.dto.QuestionDTO;
import life.cj.community.community.mapper.QuestionMapper;
import life.cj.community.community.mapper.UserMapper;
import life.cj.community.community.model.Page;
import life.cj.community.community.model.Question;
import life.cj.community.community.model.Users;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public PaginationDTO list(Page page) {
        Integer count = questionMapper.count();
        if (page.getPageNum() < 1) {
            page.setPageNum(1);
        }
        Integer size = count % page.getPageSize() == 0 ?
                count / page.getPageSize() :
                count / page.getPageSize() + 1;
        if (page.getPageNum() > size) {
            page.setPageNum(size);
        }
        List<Question> questionList = questionMapper.list(page);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();

        for (Question question : questionList) {
            Users user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setUser(user);
            BeanUtils.copyProperties(question,questionDTO);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setList(questionDTOList);
        paginationDTO.setParam(count, page);
        return paginationDTO;
    }
}
