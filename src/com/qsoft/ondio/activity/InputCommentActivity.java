package com.qsoft.ondio.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import com.qsoft.ondio.R;


public class InputCommentActivity extends Activity
{
    private Button btBack;
    private Button btDone;
    private EditText etContent;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_comment);
        setUpUI();
        setUpListenerController();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    private void setUpListenerController()
    {
        btDone.setOnClickListener(onClickListener);
        btBack.setOnClickListener(onClickListener);
        etContent.addTextChangedListener(textChangeListener);
    }

    private void setUpUI()
    {
        btBack = (Button) findViewById(R.id.inputcommnet_btBack);
        btDone = (Button) findViewById(R.id.inputcommnet_btDone);
        etContent = (EditText) findViewById(R.id.inputcomment_etInputComment);
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            switch (view.getId())
            {
                case R.id.inputcommnet_btBack:
                    doBack();
                    break;
                case R.id.inputcommnet_btDone:
                    doDone();
                    break;
            }
        }
    };

    private void doDone()
    {
        Intent intent = getIntent();
        intent.putExtra("data", etContent.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    private void doBack()
    {
        Intent intent = getIntent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    private final TextWatcher textChangeListener = new TextWatcher()
    {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {
        }

        @Override
        public void afterTextChanged(Editable s)
        {
            if (etContent.getText().toString().isEmpty())
            {
                btDone.setBackgroundResource(R.drawable.done_visible);
                btDone.setEnabled(false);
            }
            else
            {
                btDone.setBackgroundResource(R.drawable.done);
                btDone.setEnabled(true);
            }
        }
    };
}
