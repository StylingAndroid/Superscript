package com.stylingandroid.superscript;

import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.widget.TextView;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class OrdinalSuperscriptFormatterTest {
    private OrdinalSuperscriptFormatter formatter;

    @Mock
    TextView textView;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Nested
    @DisplayName("Given a string with a single date, When we format it")
    class SingleDates {
        private String text;

        @Mock
        SpannableStringBuilder stringBuilder;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.initMocks(this);
            text = "This contains 1st date but no more";
            when(textView.getText()).thenReturn(text);
            formatter = new OrdinalSuperscriptFormatter(stringBuilder);
        }

        @Test
        @DisplayName("Then two spans are created")
        void testTwoSpans() {
            formatter.format(textView);

            verify(stringBuilder, times(2)).setSpan(any(), anyInt(), anyInt(), anyInt());
        }

        @Test
        @DisplayName("Then one SuperscriptSpan is created")
        void testSuperscriptSpan() {
            formatter.format(textView);

            verify(stringBuilder, times(1)).setSpan(any(SuperscriptSpan.class), anyInt(), anyInt(), anyInt());
        }

        @Test
        @DisplayName("Then one RelativeSizeSpan is created")
        void testRelativeSizeSpan() {
            formatter.format(textView);

            verify(stringBuilder, times(1)).setSpan(any(RelativeSizeSpan.class), anyInt(), anyInt(), anyInt());
        }

        @Test
        @DisplayName("Then the start position is correct")
        void testStartPosition() {
            formatter.format(textView);

            verify(stringBuilder, times(2)).setSpan(any(), eq(15), anyInt(), anyInt());
        }

        @Test
        @DisplayName("Then the end position is correct")
        void testEndPosition() {
            formatter.format(textView);

            verify(stringBuilder, times(2)).setSpan(any(), anyInt(), eq(17), anyInt());
        }
    }

    @TestFactory
    @DisplayName("Multiple dates")
    Collection<DynamicTest> testCorrectNumberOfSpans() {
        Set<DynamicTest> tests = new LinkedHashSet<>();
        tests.add(createTest("Hello world!", 0));
        tests.add(createTest("1st 2nd", 2));
        tests.add(createTest("1st 3rd", 2));
        tests.add(createTest("1st 4th", 2));
        tests.add(createTest("1st 2nd 3rd 4th", 4));
        tests.add(createTest("The word \'handle\' should not have the \'nd\' superscript", 0));
        tests.add(createTest("1stamp should not have \'st\' superscript", 0));
        tests.add(createTest("first1st should not have \'st\' superscript", 0));
        tests.add(createTest("11th", 1));
        tests.add(createTest(" 11th", 1));
        tests.add(createTest("The 11th", 1));
        return tests;
    }

    private DynamicTest createTest(final String source, final int expected) {
        String displayName = String.format(Locale.getDefault(),"Given the source string \'%1$s\', When we format it, then %2$d spans are created", source, expected);
        return DynamicTest.dynamicTest(displayName, new Executable() {
            @Override
            public void execute() throws Throwable {
                SpannableStringBuilder localStringBuilder = mock(SpannableStringBuilder.class);
                OrdinalSuperscriptFormatter localFormatter = new OrdinalSuperscriptFormatter(localStringBuilder);
                when(textView.getText()).thenReturn(source);

                localFormatter.format(textView);

                verify(localStringBuilder, times(expected)).setSpan(any(SuperscriptSpan.class), anyInt(), anyInt(), anyInt());
            }
        });
    }

}
