package ru.startandroid.develop.simpleadapterdata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.ListView
import android.widget.SimpleAdapter

const val CM_DELETE_ID = 1
const val ATTRIBUTE_NAME_TEXT = "text"
const val ATTRIBUTE_NAME_IMAGE = "image"

class MainActivity : AppCompatActivity() {

    private var lvSimple:ListView? = null
    private var sAdapter:SimpleAdapter? = null
    private var data:ArrayList<Map<String, Any?>>? = null
    private var m:Map<String, Any?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        data = ArrayList()
        for (i in 0..5) {
            m = HashMap<String, Any>()
            (m as HashMap<String, Any>).put(ATTRIBUTE_NAME_TEXT, "sometext $i")
            (m as HashMap<String, Any>).put(ATTRIBUTE_NAME_IMAGE, R.drawable.ic_launcher)
            data!!.add(m as HashMap<String, Any>)
        }
        val from = arrayOf(ATTRIBUTE_NAME_TEXT, ATTRIBUTE_NAME_IMAGE)
        val to = intArrayOf(R.id.tvText, R.id.ivImg)

        sAdapter = SimpleAdapter(this, data, R.layout.item, from, to)

        lvSimple = findViewById(R.id.lvSimple)
        lvSimple!!.adapter = sAdapter
        registerForContextMenu(lvSimple)
    }

    fun onButtonClick(v: View) {
        m = HashMap()
        (m as HashMap<String, Any?>).put(ATTRIBUTE_NAME_TEXT, "sometext ${data!!.size + 1}")
        (m as HashMap<String, Any?>).put(ATTRIBUTE_NAME_IMAGE, R.drawable.ic_launcher)
        data!!.add(m as HashMap<String, Any?>)
        sAdapter!!.notifyDataSetChanged()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu?.add(0, CM_DELETE_ID, 0, "Удалите запись")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (item.itemId == CM_DELETE_ID) {
            val acmi = item.menuInfo as AdapterContextMenuInfo
            data?.removeAt(acmi.position)
            sAdapter!!.notifyDataSetChanged()
            return true
        }
        return super.onContextItemSelected(item)
    }
}