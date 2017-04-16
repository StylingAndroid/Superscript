package com.stylingandroid.superscript;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.text_view);
        OrdinalSuperscriptFormatter formatter = new OrdinalSuperscriptFormatter(new SpannableStringBuilder());
        formatter.format(textView);
    }
}
