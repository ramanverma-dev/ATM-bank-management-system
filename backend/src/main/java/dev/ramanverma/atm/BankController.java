package dev.ramanverma.atm;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.*;

@RestController
@RequestMapping("/api")
public class BankController {
    private final BankAccountRepository accounts;
    private final BankTransactionRepository transactions;
    private final PasswordEncoder encoder;
    private final SecureRandom random = new SecureRandom();

    public BankController(BankAccountRepository accounts, BankTransactionRepository transactions, PasswordEncoder encoder) {
        this.accounts = accounts; this.transactions = transactions; this.encoder = encoder;
    }

    public record SignupRequest(@NotBlank @Size(max=80) String name, @NotBlank @Pattern(regexp="[0-9]{4,6}") String pin) {}
    public record LoginRequest(@NotBlank @Pattern(regexp="[0-9]{16}") String cardNumber, @NotBlank @Pattern(regexp="[0-9]{4,6}") String pin) {}
    public record AmountRequest(@NotNull @DecimalMin(value="1.00") @Digits(integer=12,fraction=2) BigDecimal amount) {}
    public record PinRequest(@NotBlank @Pattern(regexp="[0-9]{4,6}") String currentPin, @NotBlank @Pattern(regexp="[0-9]{4,6}") String newPin) {}

    @GetMapping("/health")
    public Map<String,String> health() { return Map.of("status","ok","service","atm-banking-api"); }

    @PostMapping("/auth/signup")
    @Transactional
    public Map<String,String> signup(@Valid @RequestBody SignupRequest req, HttpSession session) {
        String card;
        do { StringBuilder b = new StringBuilder(); for (int i=0;i<16;i++) b.append(random.nextInt(10)); card=b.toString(); }
        while (accounts.findByCardNumber(card).isPresent());
        BankAccount a = new BankAccount();
        a.cardNumber = card; a.name = req.name().trim(); a.pinHash = encoder.encode(req.pin()); a.balance = BigDecimal.ZERO;
        accounts.save(a); session.setAttribute("accountId", a.id); session.setAttribute("cardNumber", a.cardNumber);
        return Map.of("message","Account created","cardNumber",a.cardNumber,"name",a.name);
    }

    @PostMapping("/auth/login")
    public Map<String,String> login(@Valid @RequestBody LoginRequest req, HttpSession session) {
        BankAccount a = accounts.findByCardNumber(req.cardNumber()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid card number or PIN"));
        if (!encoder.matches(req.pin(), a.pinHash)) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid card number or PIN");
        session.setAttribute("accountId", a.id); session.setAttribute("cardNumber", a.cardNumber);
        return Map.of("message","Signed in","name",a.name,"cardNumber",a.cardNumber);
    }

    @PostMapping("/auth/logout")
    public Map<String,String> logout(HttpSession session) { session.invalidate(); return Map.of("message","Signed out"); }

    @GetMapping("/account")
    public Map<String,Object> account(HttpSession session) {
        BankAccount a = current(session);
        return Map.of("name",a.name,"cardNumber",a.cardNumber,"balance",a.balance);
    }

    @GetMapping("/account/transactions")
    public List<Map<String,Object>> statement(HttpSession session) {
        BankAccount a = current(session);
        return transactions.findTop10ByAccountIdOrderByCreatedAtDesc(a.id).stream()
            .map(t -> Map.<String,Object>of("id",t.id,"type",t.type,"amount",t.amount,"createdAt",t.createdAt)).toList();
    }

    @PostMapping("/account/deposit")
    @Transactional
    public Map<String,Object> deposit(@Valid @RequestBody AmountRequest req, HttpSession session) {
        BankAccount a = accounts.findLockedById(current(session).id).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        a.balance = a.balance.add(req.amount()); transactions.save(new BankTransaction(a.id,"DEPOSIT",req.amount()));
        return Map.of("message","Deposit recorded","balance",a.balance);
    }

    @PostMapping("/account/withdraw")
    @Transactional
    public Map<String,Object> withdraw(@Valid @RequestBody AmountRequest req, HttpSession session) {
        BankAccount a = accounts.findLockedById(current(session).id).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        if (a.balance.compareTo(req.amount()) < 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Insufficient balance");
        a.balance = a.balance.subtract(req.amount()); transactions.save(new BankTransaction(a.id,"WITHDRAWAL",req.amount()));
        return Map.of("message","Withdrawal recorded","balance",a.balance);
    }

    @PostMapping("/account/pin")
    @Transactional
    public Map<String,String> changePin(@Valid @RequestBody PinRequest req, HttpSession session) {
        BankAccount a = accounts.findLockedById(current(session).id).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        if (!encoder.matches(req.currentPin(),a.pinHash)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Current PIN is incorrect");
        a.pinHash = encoder.encode(req.newPin());
        return Map.of("message","PIN changed");
    }

    private BankAccount current(HttpSession session) {
        Object id = session.getAttribute("accountId");
        if (!(id instanceof Long accountId)) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Please sign in again");
        return accounts.findById(accountId).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Account not found"));
    }
}
