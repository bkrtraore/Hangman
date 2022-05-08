package com.example.hangman;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity<et1> extends AppCompatActivity {

    private static final String CHANNEL_ID = "channel";
    public static String[] WORDS;
    public static String[] DEFINITIONS;

    public static final Random RANDOM = new Random();
    // Max erreurs avant défaite du joueur
    public static final int MAX_ERRORS = 6;


    private String wordToFind;
    // Les caractères trouvés par le joueur
    private char[] wordFound;
    private int nbErrors;


    // Lettres déjà utilisées
    private ArrayList<String> letters = new ArrayList<>();

    private ImageView img;
    private TextView wordTv;
    private TextView wordToFindTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = findViewById(R.id.img);
        wordTv = findViewById(R.id.wordTv);
        wordToFindTv = findViewById(R.id.wordToFindTv);
        insert_into_db();
        newGame();
    }

    private void insert_into_db(){
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.delete_all_words();
        sqLiteManager.add_word("AIGUILLE", "Fine tige d'acier pointue à une extrémité et percée à l'autre d'un trou (chas) où passe le fil.");
        sqLiteManager.add_word("ANDROID", "Système d'exploitation mobile fondé sur le noyau Linux et développé par des informaticiens sponsorisés par Google.");
        sqLiteManager.add_word("BAGUETTE", "Variété de pain, reconnaissable à sa forme allongée.");
        sqLiteManager.add_word("BILLARD", "Jeu où les joueurs font rouler sur une table spéciale des billes lancées au moyen d'un bâton (queue de billard).");
        sqLiteManager.add_word("BOL", "Pièce de vaisselle, récipient individuel hémisphérique.");
        sqLiteManager.add_word("BOUTEILLE", "Récipient à goulot étroit, destiné à contenir un liquide.");
        sqLiteManager.add_word("CASQUE", "Coiffure rigide (métal, cuir, plastique) qui couvre et protège la tête.");
        sqLiteManager.add_word("CHAUSSON", "Chaussure d'intérieur légère et chaude ; chaussure tricotée pour bébé.");
        sqLiteManager.add_word("CIDRE", "Boisson obtenue par la fermentation alcoolique du jus de pomme.");
        sqLiteManager.add_word("CORNICHON", "Petit concombre cueilli avant sa maturité, que l'on conserve dans du vinaigre.");
        sqLiteManager.add_word("DISCIPLINE", "Règle de conduite commune aux membres d'un corps, d'une collectivité ; obéissance à cette règle.");
        sqLiteManager.add_word("FUSILLADE", "Échange de coups de feu.");
        sqLiteManager.add_word("GALAXIE", "Vaste amas d'étoiles, l'une des structures essentielles de l'Univers, et dont la Galaxie est un exemple.");
        sqLiteManager.add_word("GONDOLE", "Barque vénitienne à un seul aviron, longue et plate, aux extrémités relevées et recourbées.");
        sqLiteManager.add_word("MONTRE", "instrument de mesure du temps qui se porte sur soi.");
        sqLiteManager.add_word("MOTO", "Véhicule à deux roues, à moteur à essence de plus de 125 cm3");
        sqLiteManager.add_word("NAPPE", "Linge qui sert à couvrir la table du repas.");
        sqLiteManager.add_word("ORPHELIN", "Enfant qui a perdu son père et sa mère, ou l'un des deux.");
        sqLiteManager.add_word("PANDA", "Mammifère des forêts d'Inde et de Chine.");
        sqLiteManager.add_word("PINCER", "Serrer entre les extrémités des doigts, entre les branches d'une pince ou d'un objet analogue.");
        sqLiteManager.add_word("POCHE", "Petit sac, pièce cousu(e) dans ou sur un vêtement et où l'on met les objets qu'on porte sur soi.");
        sqLiteManager.add_word("POTIN", "Bavardage, commérage.");
        sqLiteManager.add_word("PROJET", "Image d'une situation, d'un état que l'on pense atteindre.");
        sqLiteManager.add_word("REMPLIR", "Rendre plein, utiliser entièrement (un espace disponible).");
        sqLiteManager.add_word("ROSEAU", "Plante aquatique à tige droite et lisse.");
        sqLiteManager.add_word("SENTIMENT", "Conscience plus ou moins claire, connaissance comportant des éléments affectifs et intuitifs.");
        sqLiteManager.add_word("SERVIETTE", "Pièce de linge dont on se sert à table ou pour la toilette.");
        sqLiteManager.add_word("SILENCE", "Fait de ne pas parler ; état, attitude d'une personne qui reste sans parler.");
        sqLiteManager.add_word("SOURIS", "Petit mammifère rongeur.");
        sqLiteManager.add_word("VENDEUR", "Personne dont la profession est de vendre");
        sqLiteManager.add_word("VOITURE", "Véhicule monté sur roues, tiré ou poussé par un animal, un homme");

        WORDS = sqLiteManager.get_mots();
        DEFINITIONS = sqLiteManager.get_definitions();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    private String nextWordToFind() {
        return WORDS[RANDOM.nextInt(WORDS.length)];
    }


    public void newGame() {
        createNotificationChannel();

        LinearLayout lt1 = (LinearLayout) findViewById(R.id.letters_layout_one);
        LinearLayout lt2 = (LinearLayout) findViewById(R.id.letters_layout_two);
        LinearLayout lt3 = (LinearLayout) findViewById(R.id.letters_layout_three);
        for (int i = 0; i < lt1.getChildCount();  i++ ){
            View view = lt1.getChildAt(i);
            view.setEnabled(true);
        }
        for (int i = 0; i < lt2.getChildCount();  i++ ){
            View view = lt2.getChildAt(i);
            view.setEnabled(true);
        }
        for (int i = 0; i < lt3.getChildCount();  i++ ){
            View view = lt3.getChildAt(i);
            view.setEnabled(true);
        }
        findViewById(R.id.playAgain).setVisibility(View.INVISIBLE);
        nbErrors = -1;
        letters.clear();
        wordToFind = nextWordToFind();

        wordFound = new char[wordToFind.length()];

        for (int i = 0; i < wordFound.length; i++) {
            wordFound[i] = '_';
        }

        updateImg(nbErrors);
        wordTv.setText(wordFoundContent());
        wordToFindTv.setText("");
    }

    // Pour le bouton rejouer
    public void newGame(View view) {
        newGame();
    }

    // Pour l'item du menu
    public void newGame(MenuItem item) {
        newGame();
    }

    // Retourne vrai si le mot est trouvé
    public boolean wordFound() {
        return wordToFind.contentEquals(new String(wordFound));
    }

    // Méthode permettant la maj du mot si une lettre est trouvé
    private void enter(String c) {
        // on met a jour seulement si c n'a pas déjà été entré
        if (!letters.contains(c)) {
            // on vérifie si le mot contient c
            if (wordToFind.contains(c)) {
                // si oui on remplace _ par c (le caractère).
                int index = wordToFind.indexOf(c);

                while (index >= 0) {
                    wordFound[index] = c.charAt(0);
                    index = wordToFind.indexOf(c, index + 1);
                }
            } else {
                // c n'est pas dans le mot => erreur
                nbErrors++;
                Toast.makeText(this, R.string.try_an_other, Toast.LENGTH_SHORT).show();
            }

            // c est ajouté aux letttres utilisées
            letters.add(c);
        } else {
            Toast.makeText(this, R.string.letter_already_entered, Toast.LENGTH_SHORT).show();
        }
    }

    // Retourne l'état du mot trouvé par l'utilisateur jusqu'à maintenant
    private String wordFoundContent() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < wordFound.length; i++) {
            builder.append(wordFound[i]);

            if (i < wordFound.length - 1) {
                builder.append(" ");
            }
        }

        return builder.toString();
    }


    private void updateImg(int play) {
        int resImg = getResources().getIdentifier("hangman_" + play, "drawable",
                getPackageName());
        img.setImageResource(resImg);
    }



    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void notifyUser(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Définition")
                .setContentText("La définition de ce mot se trouve la liste des mots, n'hésitez pas a y jeter un oeil !")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }


    public void touchLetter(View v) {


        if (nbErrors < MAX_ERRORS
                && !getString(R.string.you_win).equals(wordToFindTv.getText())) {
            String letter = ((Button) v).getText().toString();
            enter(letter);
            wordTv.setText(wordFoundContent());
            updateImg(nbErrors);

            // Vérifie si le mot est trouvé
            if (wordFound()) {
                Toast.makeText(this, R.string.you_win, Toast.LENGTH_SHORT).
                        show();
                wordToFindTv.setText(R.string.you_win);

                notifyUser();
                findViewById(R.id.playAgain).setVisibility(View.VISIBLE);
            } else {
                if (nbErrors >= MAX_ERRORS) {
                    Toast.makeText(this, R.string.you_lose, Toast.LENGTH_SHORT).show();
                    wordToFindTv.setText(getString(R.string.word_to_find).
                            replace("#word#", wordToFind));
                    notifyUser();
                    findViewById(R.id.playAgain).setVisibility(View.VISIBLE);
                }
            }
        } else {
            Toast.makeText(this, R.string.game_is_ended, Toast.LENGTH_SHORT).show();
        }
        v.setEnabled(false);
    }


    public void goToListe(MenuItem item) {
        // On envoi la liste de mots et leur definitions grace à l'intent
        Intent intent = new Intent(MainActivity.this, Liste.class);
        intent.putExtra("mots", WORDS);
        intent.putExtra("definitions", DEFINITIONS);
        startActivity(intent);
    }

    public void goToAbout(MenuItem item) {
        startActivity(new Intent(MainActivity.this, About.class));
    }


}