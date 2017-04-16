package com.stylingandroid.superscript;

import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrdinalSuperscriptFormatter {
    private static final String SUPERSCRIPT_REGEX = "(?<=\\b\\d{1,10})(st|nd|rd|th)(?=\\b)";
    private static final Pattern PATTERN = Pattern.compile(SUPERSCRIPT_REGEX);
    private static final float PROPORTION = 0.5f;

    private final SpannableStringBuilder stringBuilder;

    public OrdinalSuperscriptFormatter(@NonNull SpannableStringBuilder stringBuilder) {
        this.stringBuilder = stringBuilder;
    }

    public void format(TextView textView) {
        CharSequence text = textView.getText();
        Matcher matcher = PATTERN.matcher(text);
        stringBuilder.clear();
        stringBuilder.append(text);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            createSuperscriptSpan(start, end);
        }
        textView.setText(stringBuilder);
    }

    private void createSuperscriptSpan(int start, int end) {
        SuperscriptSpan superscript = new SuperscriptSpan();
        RelativeSizeSpan size = new RelativeSizeSpan(PROPORTION);
        stringBuilder.setSpan(superscript, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringBuilder.setSpan(size, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}
