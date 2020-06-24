package esprims.gi2.tp3Wiem

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import esprims.gi2.tp3Wiem.classes.Mot
import esprims.gi2.tp3Wiem.classes.WordsManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val wordsManager : WordsManager = WordsManager(this)
    var list : List<Mot> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list=this.refreshList()
        registerForContextMenu(wordsList)
        btn_add.setOnClickListener {
            intent = Intent(applicationContext, AddWordActivity::class.java)
            startActivityForResult(intent,1)

        }

    }
    private fun refreshList(): List<Mot> {

        wordsManager.openWriteDB()
        list =wordsManager.getWords()
        wordsList.adapter = ArrayAdapter<Mot>(this,android.R.layout.simple_list_item_1,list)
        wordsManager.closeDB()
        return list
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode){
            1->{ when (resultCode){
                Activity.RESULT_OK->{
              list=this.refreshList()
                    Toast.makeText(applicationContext, "yes", Toast.LENGTH_LONG).show()

                }
            else-> {
                Toast.makeText(applicationContext, "no no no", Toast.LENGTH_LONG).show()
                this.refreshList()
            }
            }}
            2->{ when (resultCode){
                Activity.RESULT_OK->{
                    list=this.refreshList()
                    Toast.makeText(applicationContext, "updated", Toast.LENGTH_LONG).show()

                }
                else-> {
                    Toast.makeText(applicationContext, "not updated", Toast.LENGTH_LONG).show()
                    this.refreshList()
                }
            }}
            }

        }
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)

        menu?.add(Menu.NONE,1, Menu.NONE,"Supprimer ")
        menu?.add(Menu.NONE,2,Menu.NONE,"Editer ")


    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            1 -> {
                val info= item.menuInfo as AdapterView.AdapterContextMenuInfo
                val component = list[info.position]
                wordsManager.deleteWord(component.leMot)
                list=this.refreshList()
                Toast.makeText(this, "Deleted", Toast.LENGTH_LONG).show()
            }
            2 -> {
                val info= item.menuInfo as AdapterView.AdapterContextMenuInfo
                val component = list[info.position]
                Toast.makeText(this, component.toString(), Toast.LENGTH_LONG).show()

                intent = Intent(applicationContext, EditWordActivity::class.java)
                intent.putExtra("motOld", component.leMot)
                intent.putExtra("typeOld", component.type)
                intent.putExtra("genreOld", component.genre)

                startActivityForResult(intent, 2)
            }
        }
        return super.onContextItemSelected(item)
    }

    }
