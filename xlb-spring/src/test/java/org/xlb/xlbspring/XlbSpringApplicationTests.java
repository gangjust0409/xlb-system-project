package org.xlb.xlbspring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class XlbSpringApplicationTests {

	@Autowired
	private MockMvc mvc;

	/*@Autowired
	private WebTestClient client;*/

	@Test
	void contextLoads() throws Exception {
		MvcResult mvcResult = mvc.perform(get("/test/张三")).andExpect(status().isOk()).andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}

	// webflux
	/*@Test
	void testWebCli() throws URISyntaxException {
		WebTestClient.BodyContentSpec body = client.get().uri(new URI("/test/鲁班")).exchange().expectBody();
		EntityExchangeResult<byte[]> entityExchangeResult = body.returnResult();
		HttpMethod method = entityExchangeResult.getMethod();
		String str = entityExchangeResult.getResponseBody().toString();
		System.out.println(method + "\t" + str);
	}
*/
	@Test
	void testThead() throws InterruptedException {
		Thread thread = new Thread(() -> {
			while (true) {
				System.out.printf("任务执行完成！\n");
			}
		});
		thread.start();
		thread.join();

	}

}
