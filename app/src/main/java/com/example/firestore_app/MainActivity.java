package com.example.firestore_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    EditText name;
    EditText email;
    Button save;
    Button read;
    Button update;
    Button delete;
    TextView result;

    //firebase firestore
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    DocumentReference documentReference=db.collection("Users").document("ODpJNVEsxRu9ZvqFHpTp");

    CollectionReference collectionReference=db.collection("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        save=findViewById(R.id.savebtn);
        read=findViewById(R.id.readbtn);
        update=findViewById(R.id.updatebtn);
        delete=findViewById(R.id.deletebtn);
        result=findViewById(R.id.test_result);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveDataToNewDocument();
            }
        });

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetAllDocumentsInCollection();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateSpecificDocument();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteDocument();
            }
        });
    }

    public void SaveDataToNewDocument(){

        //adding data to firestore
        String n= name.getText().toString();
        String m= email.getText().toString();

        Friend friend=new Friend(n,m);
        collectionReference.add(friend).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                String docID=documentReference.getId();

            }
        });

    }

    public void GetAllDocumentsInCollection(){
        //reading data from firestore
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String data="";
                //this code is executed when data retrieval is successful
                //the queryDocumentSnapshot contains the documents in the collection
                //each queryDocumentSnapshot-->represents a document in the collection

                for (QueryDocumentSnapshot snapshots: queryDocumentSnapshots){
                    Friend friend=snapshots.toObject(Friend.class);

                    data+="Name : "+friend.getName()+" Email : "+friend.getEmail()+"\n";
                }

                result.setText(data);
            }
        });
    }

    public void UpdateSpecificDocument(){
        //update document in firestore database ,whichever document name documentref is referring to
        String n= name.getText().toString();
        String m= email.getText().toString();

        documentReference.update("name",n);
        documentReference.update("email",m);

    }

    public void DeleteDocument(){
        //delete document in firestore database ,whichever document name documentref is referring to
        documentReference.delete();
    }
}