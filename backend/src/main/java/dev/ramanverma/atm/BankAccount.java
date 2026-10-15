package dev.ramanverma.atm;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "web_bank_accounts", uniqueConstraints = @UniqueConstraint(columnNames = "cardNumber"))
public class BankAccount {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(nullable = false, unique = true, length = 16)
    public String cardNumber;
    @Column(nullable = false)
    public String name;
    @Column(nullable = false, length = 100)
    public String pinHash;
    @Column(nullable = false, precision = 14, scale = 2)
    public BigDecimal balance = BigDecimal.ZERO;
    @Column(nullable = false)
    public Instant createdAt = Instant.now();
    protected BankAccount() {}
}
