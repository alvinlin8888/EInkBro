package info.plateaukao.einkbro.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import info.plateaukao.einkbro.util.Constants.Companion.EN_AUTO_RUN

class BootReceiver: BroadcastReceiver() {
    val TAG = "BootReceiver";

    /*
    override fun onReceive(p0: Context?, p1: Intent?) {
        TODO("Not yet implemented")
    }
    */

    override fun onReceive(context: Context?, intent: Intent?) {
        var ctx: Context? = context?.getApplicationContext()
        var pkg: String? = ctx?.getPackageName()
        var act: String? = intent?.getAction()
        var msg: String = String.format("[%s] BootReceiver: act=%s, ctx=%s", pkg, act, ctx)
        Log.i(TAG, msg)
        when (act) {
            Intent.ACTION_BOOT_COMPLETED -> {
                if(!EN_AUTO_RUN)
                    return
                try {
                    val itn = ctx!!.packageManager.getLaunchIntentForPackage(pkg!!)
                    Log.d(TAG, String.format("Auto start app, intent=%s,%s ...", pkg, intent?.component.toString(), intent?.extras.toString()))
                    itn!!.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    ctx.startActivity(itn)
                } catch (ex: Exception) {
                    Log.e(TAG, String.format("Exception: ex=%s", ex.toString()) )
                }

                /*
                var itn = Intent(context, BrowserActivity::class.java)
                itn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                ctx?.startService(itn)
                */
            }
        }
    }
}

/*
class BootReceiver  {
}
*/