package com.jso.deco.controller;

import static com.jso.deco.api.exception.DHMessageCode.USER_ALREADY_EXISTS;
import static com.jso.deco.api.exception.DHMessageCode.USER_DOESNT_EXIST;
import static com.jso.deco.data.service.UserDataService.EMAIL;
import static com.jso.deco.data.service.UserDataService.USERNAME;

import com.jso.deco.api.controller.UpdateAvatarResponse;
import com.jso.deco.api.controller.UserInfosResponse;
import com.jso.deco.api.controller.UserLoginResponse;
import com.jso.deco.api.exception.DHServiceException;
import com.jso.deco.api.service.request.UserInfosRequest;
import com.jso.deco.api.service.request.UserLoginRequest;
import com.jso.deco.api.service.request.UserRegisterRequest;
import com.jso.deco.controller.adapter.UserAdapter;
import com.jso.deco.controller.image.ImageService;
import com.jso.deco.data.api.DBUser;
import com.jso.deco.data.api.DBUserInfos;
import com.jso.deco.data.service.UserDataService;

public class UserController {
	private UserAdapter adapter;
	private UserDataService userDataService;
	private ImageService imageService;

	/**
	 * Create a new user
	 * @param user
	 * @return 
	 * @throws DHServiceException 
	 */
	public UserLoginResponse createUser(final UserRegisterRequest user) throws DHServiceException {
		if(userDataService.exists(USERNAME, user.getUsername())) {
			throw new DHServiceException(USER_ALREADY_EXISTS, "username");
		}
		if(userDataService.exists(EMAIL, user.getEmail())) {
			throw new DHServiceException(USER_ALREADY_EXISTS, "email");
		}

		DBUser dbUser = adapter.userRequestToDBUser(user);
		userDataService.create(dbUser);
		UserLoginResponse response = adapter.dbUserToUserLoginResponse(dbUser);

		return response;
	}

	/**
	 * Get user for login
	 * @param request
	 * @return
	 * @throws DHServiceException 
	 */
	public UserLoginResponse login(final UserLoginRequest request) throws DHServiceException {
		final DBUser dbUser = userDataService.find(request.getEmail(), request.getPassword());
		if(dbUser == null) {
			throw new DHServiceException(USER_DOESNT_EXIST, null);
		}

		final UserLoginResponse response = adapter.dbUserToUserLoginResponse(dbUser);

		return response;
	}

	/**
	 * Get user infos
	 * @param userId
	 * @param wholeInfos - if false, get only public infos
	 * @return
	 * @throws DHServiceException
	 */
	public UserInfosResponse getUserInfos(final String userId, final boolean wholeInfos) throws DHServiceException {
		final DBUserInfos dbUserInfos = userDataService.findInfosById(userId);
		if(dbUserInfos == null) {
			throw new DHServiceException(USER_DOESNT_EXIST, null);
		}

		final UserInfosResponse response = adapter.dbUserInfosToUserInfosResponse(dbUserInfos, wholeInfos);
		return response;
	}

	/**
	 * Update user infos
	 * @param userId
	 * @param request
	 * @throws DHServiceException 
	 */
	public void updateUserInfos(String userId, UserInfosRequest request) throws DHServiceException {
		final DBUserInfos dbUserInfos = userDataService.findInfosById(userId);
		if(dbUserInfos == null) {
			throw new DHServiceException(USER_DOESNT_EXIST, null);
		}

		adapter.copyUserInfosFromRequest(dbUserInfos, request);
		userDataService.update(dbUserInfos);
	}

	/**
	 * Update user avatar
	 * @param userId
	 * @param avatarDataUrl
	 * @return
	 * @throws DHServiceException 
	 */
	public UpdateAvatarResponse updateAvatar(String userId, String avatarDataUrl) throws DHServiceException {
		final String imageEncodedId = userId;

		imageService.saveAvatar(imageEncodedId, avatarDataUrl);
		if(userDataService.userAvatarEmpty(userId)) {
			userDataService.updateAvatar(userId, imageEncodedId);
		}

		return new UpdateAvatarResponse(imageEncodedId);
	}
	
	/**
	 * Get avatar from image name
	 * @param imageUrl
	 * @return
	 */
	public byte[] getAvatar(String imageUrl) {
		return imageService.getAvatar(imageUrl);
	}

	public void setUserDataService(UserDataService userDataService) {
		this.userDataService = userDataService;
	}

	public void setAdapter(UserAdapter adapter) {
		this.adapter = adapter;
	}

	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}
}
