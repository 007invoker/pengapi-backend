package com.pengapiclientsdk.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SignUtilTest {

    @Test
    void testGetSign() {
        assertThat(SignUtil.getSign("123", "321")).isEqualTo("7e2d9b9de4716dda3c9e26a180b90c30688ee2300104d6adfec23d47d7e0f9cf");
    }
}
