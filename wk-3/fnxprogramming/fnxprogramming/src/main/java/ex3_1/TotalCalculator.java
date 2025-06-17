package ex3_1;

import domainclasses.OrderItem;

import java.util.List;

public interface TotalCalculator {
    double calculate(List<OrderItem> items);
}
