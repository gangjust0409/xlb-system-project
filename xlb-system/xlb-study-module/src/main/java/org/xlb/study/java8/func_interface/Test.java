package org.xlb.study.java8.func_interface;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Component("java8Test")
public class Test {

    /**
     * Consumer 消费性接口，有参无返
     * @param name
     * @param consumer
     */
    public void accept(String name, Consumer<String> consumer) {
        consumer.accept(name);
    }

    /**
     * Supplier 供给性接口，有返无参
     * @param supplier
     * @return
     */
    public Double count(Supplier<Double> supplier) {
        return supplier.get();
    }

    /**
     * Predicate 断言型接口，有参又返
     * @param str
     * @param predicate
     */
    public Boolean test(String str, Predicate<String> predicate) {
        return predicate.test(str);
    }

    /**
     * Function 函数式接口
     * @param price
     * @param function param1 参数类型,param2 返回值类型
     * @return
     */
    public Integer compare(String price, Function<String, Integer> function) {
        return function.apply(price);
    }

    public <R> R tstFunc(Double price, Function<Double, R> rFunction, Function<Double, Double> function) {
        return rFunction.compose(function).apply(price);
    }

}
