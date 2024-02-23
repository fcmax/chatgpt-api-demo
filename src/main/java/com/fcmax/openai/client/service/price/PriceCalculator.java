package com.fcmax.openai.client.service.price;

import com.fcmax.openai.client.dto.Price;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PriceCalculator {

    @Value("${coffee.bean.price}")
    private double beanPrice;

    public Price calculate(int inputTokenCount, int outputTokenCount, double input1KTokenPrice, double output1KTokenPrice) {
        double inputTokenPrice = input1KTokenPrice / 1000;
        double outputTokenPrice = output1KTokenPrice / 1000;

        double executionUsdPrice = inputTokenCount * inputTokenPrice + outputTokenCount * outputTokenPrice;
        double executionBeansPrice = executionUsdPrice / beanPrice;

        return new Price(executionUsdPrice, executionBeansPrice);
    }
}
