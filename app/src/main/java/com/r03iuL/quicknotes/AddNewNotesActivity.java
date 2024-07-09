package com.r03iuL.quicknotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddNewNotesActivity extends AppCompatActivity {
    EditText titleText;
    EditText contentText;
    ImageButton saveNoteButton;
    TextView pageTitleTextView, deleteNoteTextViewBtn;

    String title, content, docId;

    boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_notes);

        titleText = findViewById(R.id.note_title);
        contentText = findViewById(R.id.note_body);
        saveNoteButton = findViewById(R.id.save_note_btn);
        pageTitleTextView = findViewById(R.id.page_title);
        deleteNoteTextViewBtn = findViewById(R.id.delete_btn);

        //get data from intent
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        docId = getIntent().getStringExtra("docId");



        //set editMode
        if (docId != null && !docId.isEmpty()) {
            isEditMode = true;
        }
        //set title and content
        titleText.setText(title);
        contentText.setText(content);


        //change page title text
        if (isEditMode) {
            pageTitleTextView.setText("Edit note:");
            deleteNoteTextViewBtn.setVisibility(View.VISIBLE);
        }

        saveNoteButton.setOnClickListener((v) -> saveNote());
        deleteNoteTextViewBtn.setOnClickListener((v) -> deleteNote());


    }

    void saveNote() {
        String title = titleText.getText().toString();
        String content = contentText.getText().toString();

        if (title.isEmpty()) {
            titleText.setError("Title is empty!");
            return;
        }

        if (content.isEmpty()) {
            contentText.setError("No content in note!");
            return;
        }

        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setTimestamp(Timestamp.now());
        saveToFirebase(note);

    }

    void saveToFirebase(Note note) {
        DocumentReference documentReference;
        if (isEditMode) {
            //updates existing note
            documentReference = getCollectionReference().document(docId);
            documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {

                        Toast.makeText(AddNewNotesActivity.this, "Note updated successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddNewNotesActivity.this, "Failed updating note!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            //creates a new note in firestoredb
            documentReference = getCollectionReference().document();
            documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {

                        Toast.makeText(AddNewNotesActivity.this, "Note added successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddNewNotesActivity.this, "Failed adding note!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }

    static CollectionReference getCollectionReference() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("notes").document(currentUser.getUid()).collection("my_notes");
    }

    void deleteNote() {

        DocumentReference documentReference;
        documentReference = getCollectionReference().document(docId);

        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(AddNewNotesActivity.this, "Note deleted successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddNewNotesActivity.this, "Failed deleting note!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
