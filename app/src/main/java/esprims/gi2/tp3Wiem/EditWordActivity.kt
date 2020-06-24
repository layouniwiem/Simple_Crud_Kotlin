package esprims.gi2.tp3Wiem

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import esprims.gi2.tp3Wiem.classes.Mot
import esprims.gi2.tp3Wiem.classes.WordsManager
import kotlinx.android.synthetic.main.activity_add_word.*
import kotlinx.android.synthetic.main.activity_edit_word.*

class EditWordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_word)
        val wordsManager: WordsManager = WordsManager(this)
        val motOld = intent.getStringExtra("motOld")
        val typeOld = intent.getStringExtra("typeOld")
        val genreOld = intent.getStringExtra("genreOld")
        edit_genre.setText(genreOld.toString())
        edit_mot.setText(motOld.toString())
        edit_type.setText(typeOld.toString())
        confirm_btn.setOnClickListener {
            var motN : Mot = Mot( edit_mot.text.toString(),edit_mot.text.toString().length, edit_type.text.toString() , edit_genre.text.toString())
           wordsManager.openWriteDB()
            var verif=wordsManager.updateWord(motN,motOld)
            wordsManager.closeDB()
            if (verif >= 0) {
                setResult(Activity.RESULT_OK)
                finish()
            } else {
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
        }

    }
}