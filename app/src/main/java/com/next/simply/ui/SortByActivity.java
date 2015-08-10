package com.next.simply.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.next.simply.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SortByActivity extends AppCompatActivity {

    @Bind(R.id.radioGroup) RadioGroup mRadioGroup;
    @Bind(R.id.nameButton) RadioButton mName;
    @Bind(R.id.surnameButton) RadioButton mSurname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_by);
        ButterKnife.bind(this);

        mName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ContactsActivity.class);
                intent.putExtra("ORDER_BY", true);
                startActivity(intent);
            }
        });

        mSurname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ContactsActivity.class);
                intent.putExtra("ORDER_BY", false);
                startActivity(intent);
            }
        });
    }
}
