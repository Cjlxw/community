package life.cj.community.community.mapper;

import life.cj.community.community.model.Page;
import life.cj.community.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author cj
 * @apiNote TODO
 * @date 2022/9/21 16:40
 */
@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    @Select("select * from question limit (${pageNum}-1)*${pageSize}, #{pageSize}")
    List<Question> list(Page page);

    @Select("select count(1) from question")
    Integer count();
}
