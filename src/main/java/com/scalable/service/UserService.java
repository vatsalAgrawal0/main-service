package com.scalable.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.scalable.config.MainConfig;
import com.scalable.dto.user.UserDetailResponse;
import com.scalable.dto.user.UserLoginRequest;
import com.scalable.dto.user.UserLoginResponse;
import com.scalable.dto.user.RegisterUserRequest;
import com.scalable.dto.user.RegisterUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class UserService {
    @Autowired
    private MainConfig mainConfig;
    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<RegisterUserResponse> registerNewUser(RegisterUserRequest registerUserRequest) throws JsonProcessingException {
        String url = UriComponentsBuilder.newInstance()
                .scheme(mainConfig.getUserService().getScheme())
                .host(mainConfig.getUserService().getHost())
                .port(mainConfig.getUserService().getPort())
                .path(mainConfig.getUserService().getEndpoint().get("registerEndpoint"))
                .encode().build().toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return restTemplate.exchange(url,
                HttpMethod.POST,
                new HttpEntity<>(registerUserRequest, headers),
                RegisterUserResponse.class);
    }

    public ResponseEntity<UserLoginResponse> userLogin(UserLoginRequest userLoginRequest) {
        String url = UriComponentsBuilder.newInstance()
                .scheme(mainConfig.getUserService().getScheme())
                .host(mainConfig.getUserService().getHost())
                .port(mainConfig.getUserService().getPort())
                .path(mainConfig.getUserService().getEndpoint().get("loginEndpoint"))
                .encode().build().toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return restTemplate.exchange(url,
                HttpMethod.POST,
                new HttpEntity<>(userLoginRequest, headers),
                UserLoginResponse.class);
    }

    public ResponseEntity<UserDetailResponse> getUserDetails(String jwtToken) {
        String url = UriComponentsBuilder.newInstance()
                .scheme(mainConfig.getUserService().getScheme())
                .host(mainConfig.getUserService().getHost())
                .port(mainConfig.getUserService().getPort())
                .path(mainConfig.getUserService().getEndpoint().get("userDetailsEndpoint"))
                .encode().build().toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);

        return restTemplate.exchange(url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                UserDetailResponse.class);
    }
}
