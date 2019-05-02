package com.taklip.jediorder;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.taklip.jediorder.bean.Id;
import com.taklip.jediorder.service.IdService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JediOrderApplicationTests {
	@Autowired
	private IdService idService;

	@Test
	public void contextLoads() {
		long id = idService.generateId();

		System.out.println(id);

		Id idObj = idService.explainId(id);

		System.out.println(idObj);

		assertThat(idObj.getMachine()).isEqualTo(1023L);
	}
}