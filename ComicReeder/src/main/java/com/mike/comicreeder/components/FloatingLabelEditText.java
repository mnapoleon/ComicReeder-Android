package com.mike.comicreeder.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mike.comicreeder.R;

/**
 * Created by michaelnapoleon on 10/21/13.
 */
public class FloatingLabelEditText extends LinearLayout {

    TextView label;
    EditText textField;
    String hintText;
    String floatingLabelText;

    public FloatingLabelEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FloatingLabelEditText);
        hintText = a.getString(R.styleable.FloatingLabelEditText_editTextHint);
        floatingLabelText = a.getString(R.styleable.FloatingLabelEditText_floatingLabelText);

        this.setOrientation(VERTICAL);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.floating_label_edit_text, this, true);

        label = (TextView) findViewById(R.id.floatingLabel);
        textField = (EditText) findViewById(R.id.textField);
        if (floatingLabelText != null) {
            this.label.setText(floatingLabelText);
        }

        this.textField.setHint(hintText);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //TextView textView = (TextView) findViewById(R.id.floatingLabel);
                if (s.length() == 0) {
                    label.setText("");
                } else if (s.length() == 1) {
                    label.setText(hintText);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        this.textField.addTextChangedListener(textWatcher);
    }


}
