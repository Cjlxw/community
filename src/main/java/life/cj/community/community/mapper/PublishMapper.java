package life.cj.community.community.mapper;

import life.cj.community.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author cj
 * @apiNote TODO
 * @date 2022/9/21 16:40
 */
@Mapper
public interface PublishMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);
}
