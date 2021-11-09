package com.hashkart.usermicroservice.response;

import java.util.List;

import com.hashkart.commonutilities.response.Response;
import com.hashkart.usermicroservice.domain.UserProfile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserListResponse implements Response {

	@NonNull
	private List<UserProfile> profiles;
}
