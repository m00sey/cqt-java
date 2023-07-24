package m00.sey;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

class CQTTest {
    private CQT cqt;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        cqt = new CQT();
    }

    @org.junit.jupiter.api.Test
    void test_algorithm_1_14() {
        String hello = cqt.algorithm_1_15("hello");
        assert "hello".equals(hello);
    }

    @org.junit.jupiter.api.Test
    void test_empty() {
        String empty = cqt.algorithm_1_15("");
        assert "".equals(empty);
    }

    @org.junit.jupiter.api.Test
    void test_normalize() {
        assert Arrays.equals(cqt.algorithm_1_15("\uFEC9\uFECA\uFECB\uFECC").getBytes(), new byte[]{(byte) 0xd8, (byte) 0xb9, (byte) 0xd8, (byte) 0xb9, (byte) 0xd8, (byte) 0xb9, (byte) 0xd8, (byte) 0xb9});
        assert cqt.algorithm_1_15("ℌℍ\u00a0①ｶ︷︸⁹₉㌀¼ǆ").equals(new String("HH 1カ{}99アパート1⁄4dž".getBytes(), StandardCharsets.UTF_8));
    }

    @org.junit.jupiter.api.Test
    void test_ampersands_pattern() {
        assert cqt.algorithm_1_15("&﹠＆").equals("and and and");
        assert cqt.algorithm_1_15("&!?").equals("and !?");
        assert cqt.algorithm_1_15("!﹠!?").equals("! and !?");
        assert cqt.algorithm_1_15("?＆!?").equals("? and !?");
    }

    @org.junit.jupiter.api.Test
    void test_specialized_whitespace_pattern() {
        assert cqt.algorithm_1_15(" abc  ").equals("abc");
        assert cqt.algorithm_1_15("\n abc\n\t  ").equals("abc");
        assert cqt.algorithm_1_15("\r abc\n\t  \n").equals("abc");
        assert cqt.algorithm_1_15("\u3000\u00a0abc\ufeff\u200b").equals("abc");
    }

    @org.junit.jupiter.api.Test
    void test_trailing_whitespace_on_lines() {
        assert cqt.algorithm_1_15("line1 \nline2").equals("line1 line2");
    }

    @org.junit.jupiter.api.Test
    void test_redundant_linebreaks() {
        assert cqt.algorithm_1_15("line1\n\nline2").equals("line1 line2");
    }

    @org.junit.jupiter.api.Test
    void test_redundant_linebreaks_with_trailing_whitespace() {
        assert cqt.algorithm_1_15("line1 \n \nline2").equals("line1 line2");
    }

    @org.junit.jupiter.api.Test
    void test_cr_to_space() {
        assert cqt.algorithm_1_15("line1\rline2").equals("line1 line2");
    }

    @org.junit.jupiter.api.Test
    void test_2028_to_space() {
        assert cqt.algorithm_1_15("line1\u2028\tline2").equals("line1 line2");
    }

    @org.junit.jupiter.api.Test
    void test_2029_to_space() {
        assert cqt.algorithm_1_15("line1\t\u2029\rline2").equals("line1 line2");
    }

    @org.junit.jupiter.api.Test
    void test_crlf_to_space() {
        assert cqt.algorithm_1_15("line1\r\nline2").equals("line1 line2");
    }

    @org.junit.jupiter.api.Test
    void test_other_spaces() {
        String[] spaces = new String[]{"\u200B", "\ufeff", "\u00a0", "\u3000"};
        for (String s : spaces) {
            assert cqt.algorithm_1_15('a' + s + 'b').equals("a b");
        }
    }

    @org.junit.jupiter.api.Test
    void test_squeeze() {
        assert cqt.algorithm_1_15("this  is  a \n\t\r   test").equals("this is a test");
    }

//    @org.junit.jupiter.api.Test
    void test_ideographic_punctuation() {
        assert cqt.algorithm_1_15("\u3001\u3000\u3002\u3008").equals(",.'");
    }

//    @org.junit.jupiter.api.Test
    void test_ideographic_ascii() {
        assert cqt.algorithm_1_15("\uFF01\uFF02\uFF25\uFF37\uFF56").equals("!'EWv");
    }
}