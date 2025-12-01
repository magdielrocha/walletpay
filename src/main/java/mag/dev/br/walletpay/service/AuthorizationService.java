package mag.dev.br.walletpay.service;

import mag.dev.br.walletpay.client.AuthorizationClient;
import mag.dev.br.walletpay.controller.dto.TransferDto;
import mag.dev.br.walletpay.exception.WalletPayException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    private final AuthorizationClient authorizationClient;

    public AuthorizationService(AuthorizationClient authorizationClient) {
        this.authorizationClient = authorizationClient;
    }

    public boolean isAuthorized(TransferDto transfer) {

        var response = authorizationClient.isAuthorized();

        if (response.getStatusCode().isError()){
            throw new WalletPayException();
        }

        return response.getBody().authorized();

    }

}
