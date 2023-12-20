package com.tdd.tdd.chap02;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PasswordStrengthMeterTest {

    private PasswordStrengthMeter meter = new PasswordStrengthMeter();

    private void assertStrength(String password, PasswordStrength expStr) {
        PasswordStrength result = meter.meter(password);
        assertEquals(expStr, result);
    }

    // 길이가 8글자 이상, 0 - 9 사이의 숫자 포함, 대문자 포함 모두 충족하는 암호에 대한 테스트
    @Test
    void meetsAllCriteria_Then_Strong(){
        assertStrength("ab12!@AB", PasswordStrength.STRONG);
        assertStrength("abc1!Add", PasswordStrength.STRONG);
    }

    // 길이가 8 미만이고 나머지 조건은 충족하는 암호에 대한 테스트
    @Test
    void meetsOtherCriteria_except_for_Length_Then_Nomal(){
        assertStrength("ab12!@A", PasswordStrength.NORMAL);

    }

    // PasswordStrengthMeter Test 클래스에 숫자를 포함하지 않고 나머지 조건은 충족하는
    // 암호에 대한 테스트
    @Test
    void meetsOtherCriteria_except_for_number_Then_Normal(){
        assertStrength("ab!@ABqwer", PasswordStrength.NORMAL);
    }

    //입력이 null인 경우에 대한 테스트
    @Test
    void nullInput_Then_Invalid(){
        assertStrength(null, PasswordStrength.INVALID);
    }

    // 입력이 빈 문자열이 경우에 대한 테스트
    @Test
    void emptyInput_Then_Invalid()
    {
        assertStrength("",PasswordStrength.INVALID);
    }

    // 대문자를 포함하지 않고 나머지 조건을 충족하는 경링
    @Test
    void meetsOtherCriteria_except_for_Uppercase_Then_Normal() {
        assertStrength("ab12!@df", PasswordStrength.NORMAL);
    }

    // 길이가 8글자 이상인 조건만 충족하는 경우를 검증하기 위한 테스트 코드
    @Test
    void meetsOnlyLengthCriteria_Then_Weak(){
        assertStrength("abdefghi", PasswordStrength.WEAK);
    }

    // 숫자 포함 조건만 충족하는 경우를 검증하기 위한 테스트
    @Test
    void meetsOnlyNumCriteria_Then_Weak(){
        assertStrength("12345", PasswordStrength.WEAK);
    }

    // 대문자 포함 조건만 충족하는 경우를 검증하기 위한 테스트
    @Test
    void meetsOnlyUpperCriteria_Then_Weak(){
        assertStrength("ABZEF", PasswordStrength.WEAK);
    }

    // 아무 조건도 충족하지 않은 경우를 검증하는 테스트 코드 추가
    @Test
    void meetsNoCriteria_Then_Weak(){
        assertStrength("abc", PasswordStrength.WEAK);
    }

}