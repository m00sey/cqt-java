package m00.sey;

import org.javatuples.Pair;

import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CQT {
    public static final String AMPERSAND_REPLACEMENT = " and ";
    public static final Pattern AMPERSANDS = Pattern.compile("[&\\uFE60\\uFF06]");
    public static final String WHITESPACE_REPLACEMENT = " ";
    public static final Pattern SPECIALIZED_WHITESPACE = Pattern.compile("[\\u2028\\u2029\\u200B\\uFEFF\\u00A0\\u3000\\r\\n\\t]+");
    public static final Pattern MULTIPLE_WHITESPACE = Pattern.compile("\\s{2,}");
    public static final String DASH_REPLACEMENT = "-";
    public static final Pattern DASH_CHARACTERS = Pattern.compile("[\\u058A\\u05BE\\u1400\\u1806\\u2010\\u2011\\u2012\\u2013\\u2014\\u2015\\u2e17\\u2e1a\\u2e3a\\u2e3b\\u2e40\\u2e5d\\u301c\\u3030\\u30a0\\ufe31\\ufe32\\ufe58\\ufe63\\uff0d]+");
    public static final Pattern MULTIPLE_HYPHENS = Pattern.compile("-{2,}");
    public static final List<Pair<String, String>> CJK_PUNCTUATION = Arrays.asList(new Pair<>("\u3001", ","), new Pair<>("\u3002", "."));
    public static final Pattern LONG_DOTS = Pattern.compile("[.]{4,}");
    public static final Pattern QUOTERS = Pattern.compile("[\"\\u2018\\u2019\\u201C\\u201D\\u00AB\\u00BB\\u2039\\u203A\\u3008\\u3009\\u300A\\u300B\\u300C\\u300D]");
    public static final Pattern ANY_WHITESPACE = Pattern.compile("(\\s+)");
    public static final List<Pair<String, String>> AUTOCORRECT = Arrays.asList(new Pair<>("\u1f60A", ":-)"), new Pair<>("\u1f610", ":-|"), new Pair<>("\u2639", ":-("), new Pair<>("\u1f603", ":-D"), new Pair<>("\u1f61D", ":-p"), new Pair<>("\u1f632", ":-o"), new Pair<>("\u1f609", ";-)"), new Pair<>("\u2764", "<3"), new Pair<>("\u1f494", "</3"), new Pair<>("\u00a9", "(c)"), new Pair<>("\u00ae", "(R)"), new Pair<>("\u2022", "*"));

    public static final List<Pair<String, String>> ASCII_EMOJI = Arrays.asList(new Pair<>(":)", ":-)"), new Pair<>(":|", ":-|"), new Pair<>(":(", ":-("), new Pair<>(":D", ":-D"), new Pair<>(":p", ":-p"), new Pair<>(":o", ":-o"), new Pair<>(";)", ";-)"));
    private String text;

    public String algorithm_1_15(String text) {
        this.text = text;
        return new String(step_1().step_2().step_3().step_4().step_5().text.getBytes(), StandardCharsets.UTF_8);
    }

    public CQT step_1() {
        return this;
    }

    public CQT step_2() {
        this.text = Normalizer.normalize(this.text, Normalizer.Form.NFKC);
        return this;
    }

    public CQT step_3() {
        Matcher matcher = CQT.AMPERSANDS.matcher(this.text);
        this.text = matcher.replaceAll(AMPERSAND_REPLACEMENT);
        return this;
    }

    public CQT step_4() {
        Matcher matcher = CQT.SPECIALIZED_WHITESPACE.matcher(this.text);
        this.text = matcher.replaceAll(WHITESPACE_REPLACEMENT);
        this.text = this.text.strip();

        matcher = CQT.MULTIPLE_WHITESPACE.matcher(this.text);
        this.text = matcher.replaceAll(WHITESPACE_REPLACEMENT);
        return this;
    }

    public CQT step_5() {
        Matcher matcher = CQT.DASH_CHARACTERS.matcher(this.text);
        this.text = matcher.replaceAll(DASH_REPLACEMENT);

        matcher = CQT.MULTIPLE_HYPHENS.matcher(this.text);
        this.text = matcher.replaceAll(DASH_REPLACEMENT);

        CJK_PUNCTUATION.forEach(p -> this.text = this.text.replaceAll(p.getValue0(), p.getValue1()));

        return this;
    }
}