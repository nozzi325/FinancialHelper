package com.andrew.FinancialHelper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = {
		"spring.test.database.replace=none",
		"spring.datasource.url=jdbc:tc:postgresql:15-alpine:///general"
})
@ActiveProfiles("test")
class FinancialHelperApplicationTests {

	@Test
	void contextLoads() {
	}

}
