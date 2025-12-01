package mag.dev.br.walletpay.controller;

import jakarta.validation.Valid;
import mag.dev.br.walletpay.controller.dto.TransferDto;
import mag.dev.br.walletpay.entity.Transfer;
import mag.dev.br.walletpay.service.TransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<Transfer> transfer(@RequestBody @Valid TransferDto dto) {

        var response = transferService.transfer(dto);

        return ResponseEntity.ok(response);

    }
}

