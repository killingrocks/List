package com.example.meoryou.myapplication;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.meoryou.myapplication.DataBase.CRUD;
import com.example.meoryou.myapplication.DataBase.Item;
import com.example.meoryou.myapplication.DataBase.ItemsCollection;
import com.example.meoryou.myapplication.RecyclerView.ItemAdapter;

public class MainActivity extends AppCompatActivity {
    RecyclerView recv;
    ItemAdapter adapter;
    CRUD crud;
    EditText nameEditText,descEditText;
    Button saveBtn, cancelBtn;
    private BroadcastReceiver bm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recv = (RecyclerView) findViewById(R.id.recycle_view);
        recv.setLayoutManager(new LinearLayoutManager(this));
        recv.setItemAnimator(new DefaultItemAnimator());

        crud = new CRUD(ItemsCollection.getItem());
        adapter = new  ItemAdapter(this, crud.getItems());
bm= new ConnectionReceiver();

    }

    public void addItemOnClick(View view){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.set_card_view);

        nameEditText=(EditText) dialog.findViewById(R.id.nameEditText);
        descEditText=(EditText) dialog.findViewById(R.id.descEditText);
        saveBtn= (Button) dialog.findViewById(R.id.saveBtn);
        cancelBtn = (Button) dialog.findViewById(R.id.cancelBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item item = new Item();
                item.setName(nameEditText.getText().toString());
                item.setDescription(descEditText.getText().toString());
                if(crud.addNew(item)){
                    nameEditText.setText("");
                    descEditText.setText("");
                    recv.setAdapter(adapter);
                }
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                            dialog.cancel();
                                         }
                                     });
                dialog.show();
    }

    protected void onResume(){
        super.onResume();
        recv.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter =new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(bm, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(bm);
    }
}
