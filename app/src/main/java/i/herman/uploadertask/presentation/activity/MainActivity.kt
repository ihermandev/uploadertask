package i.herman.uploadertask.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import i.herman.uploadertask.R
import com.iherman.util.replaceFragment
import i.herman.uploadertask.di.Injectable
import i.herman.uploadertask.presentation.fragment.UploaderFragment

/**
 * Created by Illia Herman on 14.12.2020.
 */
class MainActivity : AppCompatActivity(R.layout.activity_main), Injectable {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            replaceFragment(
                fragment = UploaderFragment.newInstance(),
                frameId = R.id.main_container,
                addToStack = false,
                clearBackStack = true
            )
        }
    }
}