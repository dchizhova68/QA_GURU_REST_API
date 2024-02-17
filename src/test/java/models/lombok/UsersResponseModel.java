package models.lombok;

import lombok.Data;

@Data
public class UsersResponseModel {

    private UserDataResponseModel data;
    private UsersSupportResponseModel support;

}
