package esprims.gi2.tp3Wiem

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import esprims.gi2.tp3Wiem.classes.Mot
import esprims.gi2.tp3Wiem.classes.WordsManager
import kotlinx.android.synthetic.main.activity_add_word.*

class AddWordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_word)
        btn_ajout.setOnClickListener {
            val wordsManager : WordsManager = WordsManager(this)
            wordsManager.openWriteDB()
            var mot : Mot = Mot( mot_in.text.toString(),mot_in.text.toString().length, type_in.text.toString() , genre_in.text.toString())

            var verif : Long = wordsManager.addWord(mot)
            wordsManager.closeDB()
            if (verif>=0){
                setResult(Activity.RESULT_OK)
                finish()
            }else{
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
        }
    }
}