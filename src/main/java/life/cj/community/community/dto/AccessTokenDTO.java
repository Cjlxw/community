package life.cj.community.community.dto;

import lombok.Data;

/**
 * @author cj
 * @apiNote TODO
 * @date 2022/9/20 15:15
 */
@Data
public class AccessTokenDTO {

    private String client_id;

    private String client_secret;

    private String code;

    private String redirect_uri;

    private String state;
}
