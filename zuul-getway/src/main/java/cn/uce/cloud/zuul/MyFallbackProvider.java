package cn.uce.cloud.zuul;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

@Component
public class MyFallbackProvider implements FallbackProvider {
	private final Logger logger = LoggerFactory.getLogger(FallbackProvider.class);

	@Override
	public ClientHttpResponse fallbackResponse() {

		return new ClientHttpResponse() {

			@Override
			public HttpHeaders getHeaders() {
				HttpHeaders httpHeaders = new HttpHeaders();
				httpHeaders.setContentType(MediaType.APPLICATION_JSON);
				return httpHeaders;
			}

			@Override
			public InputStream getBody() throws IOException {
				return new ByteArrayInputStream("啦啦啦，服务不在线".getBytes());

			}

			@Override
			public String getStatusText() throws IOException {

				return "OK";
			}

			@Override
			public HttpStatus getStatusCode() throws IOException {

				return HttpStatus.OK;
			}

			@Override
			public int getRawStatusCode() throws IOException {

				return 200;
			}

			@Override
			public void close() {

			}
		};
	}

	@Override
	public String getRoute() {
		return "service-hi";
	}

	@Override
	public ClientHttpResponse fallbackResponse(Throwable cause) {

		if (cause != null && cause.getCause() != null) {
			String reason = cause.getCause().getMessage();
			logger.info("Excption {}", reason);
		}
		return fallbackResponse();

	};
}
