package com.atu.effectivejava.builder;

import com.atu.effectivejava.builder.vo.Size;

import java.util.Objects;

/**
 * @Author: Tom
 * @Date: 2021/10/13 6:31 下午
 * @Description:
 */
public class NyPizza extends Pizza {

    public enum Size {SMALL, MEDIUM, LARGE}
    private final Size size;

    public static class Builder extends Pizza.Builder<Builder> {
        private final Size size;

        public Builder(Size size) {
            this.size = Objects.requireNonNull(size);
        }

        @Override
        NyPizza build() {
            return new NyPizza(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    NyPizza(Builder builder) {
        super(builder);
        size = builder.size;
    }
}
