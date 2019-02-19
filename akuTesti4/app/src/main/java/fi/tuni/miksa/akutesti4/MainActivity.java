package fi.tuni.miksa.akutesti4;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "softa";
    private EditText nro;
    private EditText nimi;
    private List<Aku> kaikkiAkut;
    private ListView mAkuListView;
    private ArrayAdapter mAkuListAdapter;

    private Context context;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mAkussDatabaseReference;
    private ChildEventListener mChildEventListener;

    private FirebaseFirestore mFirestore;
    private CollectionReference akuReference;
    //Kohta34
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private static final int RC_SIGN_IN = 124;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");
        context = this;

        mFirestore = FirebaseFirestore.getInstance();
        akuReference = mFirestore.collection("akuja");

        nro = findViewById(R.id.editTextNumero);
        nimi = findViewById(R.id.editTextNimi);
        mAkuListView = (ListView) findViewById(R.id.listView);


        kaikkiAkut = new ArrayList<>();
        muutosKysely();
    }

    public void muutosKysely(){
        mFirestore.collection("akuja").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                kaikkiAkut.clear();
                Log.d(TAG, "nakyman paivitys?");
                if(queryDocumentSnapshots!=null) {
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                        Aku aku = snapshot.toObject(Aku.class);
                        kaikkiAkut.add(aku);

                    }
                    mAkuListAdapter = new AkuArrayAdapter(context, R.layout.item_aku, kaikkiAkut);
                    mAkuListAdapter.notifyDataSetChanged();
                    mAkuListView.setAdapter(mAkuListAdapter);
                }else {
                    Log.d(TAG, "Querysnapshot on null");
                }
            }
        });
    }

    public void onClick(View view) {
        Aku aku = null;

        switch (view.getId()) {
            case R.id.add:
                // save the new Aku to the database
                aku = new Aku();
                aku.setKirjanNumero(nro.getText().toString());
                aku.setKirjanNimi(nimi.getText().toString());


                //TODO DatabaseReferenceen tuupataan yllä luotu aku
                akuReference = mFirestore.collection("akuja");
                akuReference.add(aku);


                tyhjenna();
                break;
            case R.id.delete:
                Log.d("aku", "delete tuli");

                break;
        }
    }



    public void tyhjenna() {
        //tyhjennys
        nro.setText("");
        nimi.setText("");
    }
}

