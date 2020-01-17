package in.co.sdrc.newsapp.service;

import java.util.List;

import in.co.sdrc.newsapp.model.UserModel;


public interface UserService {

	List<UserModel> updatedUsers(String lastSyncDate);

}