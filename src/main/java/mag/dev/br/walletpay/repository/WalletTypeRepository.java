package mag.dev.br.walletpay.repository;

import mag.dev.br.walletpay.entity.WalletType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletTypeRepository extends JpaRepository<WalletType, Long> {
}
