package life.cj.community.community.service;

import life.cj.community.community.mapper.UserMapper;
import life.cj.community.community.mapper.UsersMapper;
import life.cj.community.community.model.Users;
import life.cj.community.community.model.UsersExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author cj
 * @apiNote TODO
 * @date 2022/11/2 13:49
 */
@Service
public class UserService {

    @Autowired
    private UsersMapper usersMapper;


    public void createOrUpdate(Users user) {
        UsersExample usersExample = new UsersExample();
        usersExample.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());
        Users dbUser = usersMapper.selectByExample(usersExample).get(0);
        if (ObjectUtils.isEmpty(dbUser)) {
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            usersMapper.insert(user);
        } else {
            Users newUser = new Users();
            newUser.setId(dbUser.getId());
            newUser.setGmtModified(System.currentTimeMillis());
            newUser.setAvatarUrl(user.getAvatarUrl());
            newUser.setName(user.getName());
            newUser.setToken(user.getToken());
            UsersExample example = new UsersExample();
            example.createCriteria()
                    .andIdEqualTo(newUser.getId());
            usersMapper.updateByExampleSelective(newUser,example);
        }

    }
}
