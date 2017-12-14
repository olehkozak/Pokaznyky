package pokaznyky.lviv.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import pokaznyky.lviv.R
import pokaznyky.lviv.ui.fragment.ModeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout)
        if (savedInstanceState != null) {
            return
        }
        showFragment(ModeFragment())
    }

    fun FragmentManager.inTransactionReplace(containerViewId: Int, fragment: Fragment) {
        inTransaction { replace(containerViewId, fragment) }
    }

    fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction()
                .func()
                .addToBackStack(null)
                .commit()
    }

    fun showFragment(fragment: Fragment) {
        supportFragmentManager.inTransactionReplace(R.id.content_view, fragment)
    }
}
