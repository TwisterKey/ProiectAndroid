package com.example.proiectandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView scorp1, scorp2, status;
    private Button [] buttons = new Button[9];
    private Button reset;


    private int p1c, p2c, rc;
    boolean jactiv;

    int [] joc = {2, 2, 2, 2, 2, 2, 2, 2, 2}; //dam fiecarui buton valoarea 2, jucatorul 1 este reprezentat de 0 iar jucator
                                                //2 de 1
    int [][] pozitiicastigatoare = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0 ,3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {6, 4, 2}  //liniile care trebuie completate de X sau de 0 pentru a catiga jocul
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scorp1 = (TextView) findViewById(R.id.textView7);
        scorp2 = (TextView) findViewById(R.id.textView4);
        status = (TextView) findViewById(R.id.textView5);

        reset = (Button) findViewById(R.id.reset);

        for(int i=0; i < buttons.length; i++)      //aici am facut un for pentru toate butonele cu numele btn_
        {
            String bid = "btn_" + i;
            int resoursceID = getResources().getIdentifier(bid, "id", getPackageName());
            buttons[i] = findViewById(resoursceID);
            buttons[i].setOnClickListener(this);
        }
        rc = 0;
        p1c = 0;
        p2c = 0;
        jactiv = true;

        reset.setOnClickListener(new View.OnClickListener() {        //la apasarea butonlui reset se va reseta scorurile jucatorilor,
            @Override                                               //contoarele si textul de pe butoane
            public void onClick(View view) {
                // ifErrorOnOutput();
                // exceedLength();
                scorp1.setText("0");
                scorp2.setText("0");
                status.setText("");
                p1c=0;
                p2c=0;
                for(int i=0; i < buttons.length; i++)
                {
                    String bid = "btn_" + i;
                    int resoursceID = getResources().getIdentifier(bid, "id", getPackageName());
                    buttons[i] = findViewById(resoursceID);
                    buttons[i].setText("");
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        if(!((Button)v).getText().toString().equals("")) //verfica daca cineva a ales butonul
        {
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId());
        int gameState = Integer.parseInt(buttonID.substring(buttonID.length()-1, buttonID.length()));

        if(jactiv)       //verifica daca primul jucator alege butonul
        {
            ((Button) v).setText("X");    //se pune X in buton
            joc[gameState] = 0;    //se pune valoarea 0 in vector
        }else           //verifica daca al doilea jucator alege butonul
        {
            ((Button) v).setText("O");     //se pune O in buton
            joc[gameState] = 1;   //se pune valoarea 1 in vector
        }
        rc++;

        if(ecastigator()){  //se verfica daca jucatorul e castigator
            if(jactiv){    //daca jucatorul 1 e castigator i se va adauga un punct
                p1c++;
                playeruscore();
                reseteaza();    //se reseteaza textul de pe butoane
            }else{      //daca jucatorul 2 e castigator i se va adauga un punct
                p2c++;
                playeruscore();
                reseteaza();   //se reseteaza textul de pe butoane
            }
        }else if(rc == 9)   //daca rc ajunge la 9 inseamna ca nu avem niciun castigator
        {
            reseteaza();  //se reseteaza textul de pe butoane
        }else
        {
            jactiv = !jactiv;  //jucatorul 1 va deveni jucatorul 2
        }
        if(p1c > p2c) //daca castigatorul 1 are mai multe puncte, se va afisa mesajul "Player 1 castiga"
        {
            status.setText("Player 1 castiga");
        }
        if(p1c < p2c) //daca castigatorul 2 are mai multe puncte, se va afisa mesajul "Player 1 castiga"
        {
            status.setText("Player 2 castiga");
        }
        if(p1c == p2c) //daca scorul dintre jucatori este egal , se va afisa mesajul "Scor egal"
        {
            status.setText("Scor egal");
        }
    }

    public boolean ecastigator() //functia de verificare a castigatorului
    {
        boolean wresults = false;

        for(int [] pozcastig : pozitiicastigatoare) //foreach
        {
            if(joc[pozcastig[0]] == joc[pozcastig[1]] &&  //verifica daca s-a gasit o linie castigatoare
                    joc[pozcastig[1]] == joc[pozcastig[2]] &&
                        joc[pozcastig[0]] != 2)
            {
                wresults = true;  //daca s-a gasit o linie castigatoare, inseamna ca avem un castigator
            }
        }
        return wresults;
    }

    public void playeruscore()
    {
        scorp1.setText(Integer.toString(p1c)); //se adauga scorul jucatorului 1
        scorp2.setText(Integer.toString(p2c)); //se adauga scorul jucatorului 2
    }

    public void reseteaza(){
        rc=0;
        jactiv=true;
        for(int i=0; i<buttons.length; i++) //se reface vectorul si butoanele
        {
            joc[i] = 2;
            buttons[i].setText("");
        }
    }
}