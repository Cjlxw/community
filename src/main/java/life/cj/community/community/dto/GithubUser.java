package life.cj.community.community.dto;

import lombok.Data;

/**
 * @author cj
 * @apiNote TODO
 * @date 2022/9/20 15:41
 */
@Data
public class GithubUser {

    private String name;

    private Long id;

    private String bio;

    private String avatar_url;

}
