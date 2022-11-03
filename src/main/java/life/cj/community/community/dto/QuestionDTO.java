package life.cj.community.community.dto;

import life.cj.community.community.model.Users;
import lombok.Data;

/**
 * @author cj
 * @apiNote TODO
 * @date 2022/9/22 13:59
 */
@Data
public class QuestionDTO {

    private Integer id;

    private String title;

    private String description;

    private Long gmtCreate;

    private Long gmtModified;

    private Integer creator;

    private Integer commentCount;

    private Integer likeCount;

    private Integer viewCount;

    private String tag;

    private Users user;
}
