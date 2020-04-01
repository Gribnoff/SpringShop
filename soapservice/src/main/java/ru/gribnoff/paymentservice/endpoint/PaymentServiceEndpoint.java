package ru.gribnoff.paymentservice.endpoint;

import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.gribnoff.paymentservice.repository.PaymentRepository;
import ru.gribnoff.soap.payment.GetPaymentRequest;
import ru.gribnoff.soap.payment.GetPaymentResponse;

@Endpoint
@RequiredArgsConstructor
public class PaymentServiceEndpoint {
    private final String NAMESPACE = "http://www.gribnoff.ru/PaymentService";
    private final PaymentRepository paymentRepository;

    @ResponsePayload
    @PayloadRoot(namespace = NAMESPACE, localPart = "getPaymentRequest")
    public GetPaymentResponse getPaymentResponse(@RequestPayload GetPaymentRequest request) {
        GetPaymentResponse response = new GetPaymentResponse();
        response.getPayments().addAll(paymentRepository.getPayments(request.getCountry(), request.getPrice()));
        return response;
    }
}
