package mag.dev.br.walletpay.service;

import mag.dev.br.walletpay.controller.dto.CreateWalletDTO;
import mag.dev.br.walletpay.entity.Wallet;
import mag.dev.br.walletpay.exception.WalletDataAlreadyExistsException;
import mag.dev.br.walletpay.repository.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet createWallet(CreateWalletDTO dto) {

        var walletDB = walletRepository.findByCpfCnpjOrEmail(dto.cpfCnpj(), dto.email());
        if(walletDB.isPresent()) {
            throw new WalletDataAlreadyExistsException("CpfCnpj or Email already exists");
        }

        return walletRepository.save(dto.toWallet());
    }
}
