package life.cj.community.community.model;

import lombok.Data;

/**
 * @author cj
 * @apiNote TODO
 * @date 2022/9/21 16:44
 */
@Data
public class Question {

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
}
