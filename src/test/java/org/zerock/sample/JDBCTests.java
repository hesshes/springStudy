package org.zerock.sample;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

import lombok.extern.log4j.Log4j;

@Log4j
public class JDBCTests {
	// static 초기화 블럭
	// 클래스가 처음 초기화 될 때 한 번 실행

	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testConnection() {
		// try() 구문
		// () 안은 Statement Class의 인스턴스나 Stream 타입의 클래스들이 동작후 필요로하는
		// close() 메소드를 자동실행 해주는 공간이라고 함

		try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "book_ex",
				"book_ex")) {
			log.info(con);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
