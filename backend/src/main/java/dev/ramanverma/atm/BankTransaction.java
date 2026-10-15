package dev.ramanverma.atm;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "web_bank_transactions", indexes = @Index(name = "idx_web_tx_account_date", columnList = "accountId,createdAt"))
public class BankTransaction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(nullable = false)
    public Long accountId;
    @Column(nullable = false, length = 20)
    public String type;
    @Column(nullable = false, precision = 14, scale = 2)
    public BigDecimal amount;
    @Column(nullable = false)
    public Instant createdAt = Instant.now();
    protected BankTransaction() {}
    public BankTransaction(Long accountId, String type, BigDecimal amount) {
        this.accountId = accountId; this.type = type; this.amount = amount;
    }
}
