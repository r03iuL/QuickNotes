package com.r03iuL.quicknotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addNotebtn;
    ImageButton menubtn;
    RecyclerView recyclerView;
    CollectionReference collectionReference;

    NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addNotebtn = findViewById(R.id.add_notes_btn);
        menubtn = findViewById(R.id.menu_btn);
        recyclerView = findViewById(R.id.recycler_view);

        addNotebtn.setOnClickListener((v)-> startActivity(new Intent(MainActivity.this, AddNewNotesActivity.class)));

        menubtn.setOnClickListener((v)-> openMenue());

        setRecyclerView();
    }

    void  openMenue(){
        PopupMenu popupMenu = new PopupMenu(MainActivity.this,menubtn);
        popupMenu.getMenu().add("LogOut");
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getTitle()=="LogOut"){
                 FirebaseAuth.getInstance().signOut();
                 startActivity(new Intent(MainActivity.this,LoginActivity.class));
                 finish();
                 return true;


                }
                return false;
            }
        });


    }

    void setRecyclerView(){
        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();

        collectionReference = FirebaseFirestore.getInstance().collection("notes").document(currentuser.getUid()).collection("my_notes");
        Query query = collectionReference.orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Note> options =new FirestoreRecyclerOptions.Builder<Note>().setQuery(query, Note.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter = new NoteAdapter(options,this);
        recyclerView.setAdapter(noteAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        noteAdapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        noteAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        noteAdapter.notifyDataSetChanged();

    }
}