package models.lombok.usersModel;

import lombok.Data;
import models.lombok.usersModel.UserDataResponseModel;
import models.lombok.usersModel.UsersSupportResponseModel;

@Data
public class UserslistModel {
    int page;
    int per_page;
    int total;
    int total_pages;
    private UserDataResponseModel[] data;
    private UsersSupportResponseModel support;
}
