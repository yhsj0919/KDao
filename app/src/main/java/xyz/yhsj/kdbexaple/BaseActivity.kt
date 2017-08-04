package xyz.yhsj.kdbexaple

import android.os.Bundle
import android.support.v4.view.WindowCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem


/**
 * 这里面不要写静态的方法，静态的属性。
 */
abstract class BaseActivity : AppCompatActivity() {


    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(WindowCompat.FEATURE_ACTION_MODE_OVERLAY)
        setContentView(getLayoutId())

        afterSetContentView()

        val _toolBar = getToolBar()

        if (_toolBar != null) {
            setSupportActionBar(_toolBar)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        init()

    }

    /**
     * 设置视图之后
     */
    protected fun afterSetContentView() {}

    /**
     * 返回布局id
     */
    protected abstract fun getLayoutId(): Int

    /**
     * 返回toolbar
     */
    protected abstract fun getToolBar(): Toolbar?

    /**
     * 初始化功能
     */
    protected abstract fun init()


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
