package mag.dev.br.walletpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class WalletpayApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalletpayApplication.class, args);
	}

}
