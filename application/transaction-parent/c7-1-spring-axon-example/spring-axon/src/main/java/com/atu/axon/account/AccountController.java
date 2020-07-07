package com.atu.axon.account;

import com.atu.axon.account.command.AccountCreateCommand;
import com.atu.axon.account.command.AccountDepositCommand;
import com.atu.axon.account.command.AccountWithdrawCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * @author: Tom
 * @date: 2020-07-07 23:01
 * @description:
 */
@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private CommandGateway commandGateway;

    @PostMapping("")
    public CompletableFuture<Object> create() {
        UUID accountId = UUID.randomUUID();
        AccountCreateCommand command = new AccountCreateCommand(accountId.toString(), "AAA");
        return commandGateway.send(command);
    }

    @PutMapping("/{accountId}/deposit/{amount}")
    public void deposit(@PathVariable String accountId, @PathVariable Double amount) {
        commandGateway.send(new AccountDepositCommand(accountId, amount));
    }

    @PutMapping("/{accountId}/withdraw/{amount}")
    public void withdraw(@PathVariable String accountId, @PathVariable Double amount) {
        commandGateway.send(new AccountWithdrawCommand(accountId, amount));
    }
}
