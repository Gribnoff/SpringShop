package ru.gribnoff.paymentservice.repository;

import org.springframework.stereotype.Component;
import ru.gribnoff.soap.payment.Payment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentRepository {
    public List<Payment> getPayments(String country) {

        Payment payment1 = new Payment();
        payment1.setId(1);
        payment1.setName("PayPal");
        payment1.setCountry("USA");

        Payment payment2 = new Payment();
        payment2.setId(2);
        payment2.setName("GooglePay");
        payment2.setCountry("USA");

        Payment payment3 = new Payment();
        payment3.setId(3);
        payment3.setName("WebMoney");
        payment3.setCountry("Russia");

        Payment payment4 = new Payment();
        payment4.setId(4);
        payment4.setName("YandexMoney");
        payment4.setCountry("Russia");

        return new ArrayList<Payment>() {{
            add(payment1);
            add(payment2);
            add(payment3);
            add(payment4);
        }}.stream().filter(
                payment -> payment.getCountry().contains(country)
        ).collect(Collectors.toList());
    }
}
