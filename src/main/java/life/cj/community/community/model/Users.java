package life.cj.community.community.model;

import lombok.Data;

/**
 * @author cj
 * @apiNote TODO
 * @date 2022/9/21 14:09
 */
@Data
public class Users {

    private Integer id;

    private String accountId;

    private String name;

    private String token;

    private Long gmtCreate;

    private Long gmtModified;

    private String bio;
}
