package com.tempKafka.Config;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.tempKafka")
public class Config {

	@Value("${elastic.host}")
	private String host;
	@Value("${elastic.port}")
	private int port;

	@Value("${spring.elasticsearch.rest.password}")
	private String password;
	@Value("${spring.elasticsearch.rest.username}")
	private String username;
//   for xpack 
	@Bean(destroyMethod = "close")
	public RestHighLevelClient restClient() {
		final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY,
				new UsernamePasswordCredentials(username, password));

		RestClientBuilder builder = RestClient.builder(new HttpHost(host, port))
				.setDefaultHeaders(compatibilityHeaders()).setHttpClientConfigCallback(httpClientBuilder -> {
					httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
					return httpClientBuilder;
				});

		return new RestHighLevelClient(builder);
	}

//	@Bean(destroyMethod = "close")
//	public RestHighLevelClient restClient() {
//
//		RestClientBuilder builder = RestClient.builder(new HttpHost(host, port))
//				.setDefaultHeaders(compatibilityHeaders());
//
//		return new RestHighLevelClient(builder);
//	}

	@Bean
	public SecurityFilterChain filter(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests().antMatchers("/actuator/**").permitAll().anyRequest().authenticated().and()
				.oauth2ResourceServer().jwt(); // to redirect to oauth2 login page.
		http.cors();
		return http.build();
	}
	
	private Header[] compatibilityHeaders() {
		return new Header[] {
				new BasicHeader(HttpHeaders.ACCEPT, "application/vnd.elasticsearch+json;compatible-with=7"),
				new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.elasticsearch+json;compatible-with=7") };
	}




}
