package mag.dev.br.walletpay.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import mag.dev.br.walletpay.controller.dto.TransferDto;
import mag.dev.br.walletpay.entity.Transfer;
import mag.dev.br.walletpay.entity.Wallet;
import mag.dev.br.walletpay.exception.InsufficientBalanceException;
import mag.dev.br.walletpay.exception.TransferNotAllowedForWalletTypeException;
import mag.dev.br.walletpay.exception.TransferNotAuthorizedException;
import mag.dev.br.walletpay.exception.WalletNotFoundException;
import mag.dev.br.walletpay.repository.TransferRepository;
import mag.dev.br.walletpay.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class TransferService {

    private final TransferRepository transferRepository;
    private final AuthorizationService authorizationService;
    private final NotificationService notificationService;
    private final WalletRepository walletRepository;


    public TransferService(TransferRepository transferRepository,
                           AuthorizationService authorizationService,
                           WalletRepository walletRepository,
                           NotificationService notificationService) {
        this.transferRepository = transferRepository;
        this.authorizationService = authorizationService;
        this.walletRepository = walletRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public Transfer transfer(@Valid TransferDto transferDto) {

        var sender = walletRepository.findById(transferDto.payer())
                .orElseThrow(() -> new WalletNotFoundException(transferDto.payer()));

        var receiver = walletRepository.findById(transferDto.payee())
                .orElseThrow(() -> new WalletNotFoundException(transferDto.payee()));

        validateTransfer(transferDto, sender);

        sender.debit(transferDto.value());
        receiver.credit(transferDto.value());

        var transfer = new Transfer(sender, receiver, transferDto.value());

        walletRepository.save(sender);
        walletRepository.save(receiver);
        var transferResult = transferRepository.save(transfer);

        CompletableFuture.runAsync(() -> notificationService.sendNotification(transferResult));

        return transferResult;
    }

    private void validateTransfer(TransferDto transferDto, Wallet sender) {

        if (!sender.isTransferAllowedForWalletType()) {
            throw new TransferNotAllowedForWalletTypeException();
        }

        if (!sender.isBalancerEqualOrGreatherThan(transferDto.value())) {
            throw new InsufficientBalanceException();
        }

        if (!authorizationService.isAuthorized(transferDto)) {
            throw new TransferNotAuthorizedException();
        }

    }
}
