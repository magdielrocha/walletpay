package mag.dev.br.walletpay.controller;

import jakarta.validation.Valid;
import mag.dev.br.walletpay.controller.dto.CreateWalletDTO;
import mag.dev.br.walletpay.entity.Wallet;
import mag.dev.br.walletpay.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {

        this.walletService = walletService;
    }

    @PostMapping("/wallets")
    public ResponseEntity<Wallet> createWallet(@RequestBody @Valid CreateWalletDTO dto) {

        var wallet = walletService.createWallet(dto);

        return ResponseEntity.ok(wallet);
    }
}
