package life.cj.community.community.mapper;

import life.cj.community.community.model.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author cj
 * @apiNote TODO
 * @date 2022/9/21 14:07
 */
@Mapper
public interface UserMapper {

    @Insert("insert into users (account_id,name,token,gmt_create,gmt_modified,avatar_url) values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(Users user);

    @Select("select * from users where token = #{token}")
    Users findByToken(@Param("token") String token);

    @Select("select * from users where id = #{id}")
    Users findById(@Param("id") Integer id);
}
