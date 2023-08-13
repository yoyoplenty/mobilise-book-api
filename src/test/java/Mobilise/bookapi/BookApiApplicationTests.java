package Mobilise.bookapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
class BookApiApplicationTests {

	Calculator calculator = new Calculator();

	@Test
	void itShouldAddTwoNumbers() {
		//Given numbers
		int no1 = 20;
		int no2 = 30;

		//When
		int result = calculator.add(no1, no2);

		//When
		assertThat(result).isEqualTo(50);
	}

	class Calculator{
		int add(int a, int b){
			return a+b;
		}
	}


}
