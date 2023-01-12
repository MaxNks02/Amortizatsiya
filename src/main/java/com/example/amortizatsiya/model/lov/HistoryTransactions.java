package com.example.amortizatsiya.model.lov;


import com.example.amortizatsiya.model.AmortizationIncome;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "history_transactions")
public class HistoryTransactions extends LOVEntity{
    @Column(name = "state")
    State state;


    @ManyToOne(fetch = FetchType.EAGER)
    AmortizationIncome amortizationIncome;

    @Column(name = "created_at")
    private  String createdAt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());


}
