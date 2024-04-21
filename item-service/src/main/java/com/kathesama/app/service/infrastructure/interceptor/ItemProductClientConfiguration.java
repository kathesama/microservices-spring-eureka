package com.kathesama.app.service.infrastructure.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.ClientRequest;
import reactor.core.publisher.Mono;

import java.util.Map;

@Configuration
public class ItemProductClientConfiguration {

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.auth-server-url}")
    private String accessTokenUri;

    @Value("${myservice.security.keycloak-oauth2.client.username}")
    private String username;

    @Value("${myservice.security.keycloak-oauth2.client.password}")
    private String password;

    @Value("${myservice.security.keycloak-oauth2.client.grant-type}")
    private String grantType;

    @Bean
    WebClient webClient(ReactiveClientRegistrationRepository clientRegistrations, ServerOAuth2AuthorizedClientRepository authorizedClientRepository) {

        return WebClient.builder()
                .filter(addAuthenticationHeader())
                .build();
    }

    @Bean
    public RequestInterceptor requestInterceptor(ReactiveClientRegistrationRepository clientRegistrations, ServerOAuth2AuthorizedClientRepository authorizedClientRepository) {
        return requestTemplate -> {
            String accessToken = webClient(clientRegistrations, authorizedClientRepository).get()
                    .uri(accessTokenUri)
                    .headers(headers -> {
                        headers.setBasicAuth(clientId, clientSecret);
                        headers.set("grant_type", "client_credentials");
                    })
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            // Convertir la cadena del token de acceso a un objeto Map
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> tokenMap = null;
            try {
                tokenMap = mapper.readValue(accessToken, Map.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            // Obtener el token de acceso del objeto Map
            String token = tokenMap.get("access_token");

            requestTemplate.header("Authorization", "Bearer " + token);
        };
    }

    private ExchangeFilterFunction addAuthenticationHeader() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("client_id", clientId);
            map.add("client_secret", clientSecret);
            map.add("grant_type", grantType);
            map.add("username", username);
            map.add("password", password);

            ClientRequest newRequest = ClientRequest.from(clientRequest)
                .method(HttpMethod.POST)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(BodyInserters.fromFormData(map))
                .build();

            return Mono.just(newRequest);
        });
    }
}
