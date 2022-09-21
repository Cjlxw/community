package life.cj.community.community.mapper;

import life.cj.community.community.model.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author cj
 * @apiNote TODO
 * @date 2022/9/21 14:07
 */
@Mapper
public interface UserMapper {

    @Insert("insert into users (account_id,name,token,gmt_create,gmt_modified) values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified})")
    void insert(Users user);

}