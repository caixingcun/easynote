package com.caixc.easynoteapp.util

import android.app.Activity


class ActivityController {

    companion object{
        var activitys = mutableListOf<Activity>()
        fun addActivity(activity: Activity){
            activitys.add(activity)
        }

        fun removeActivity(activity: Activity) {
            activitys.remove(activity)
            if (!activity.isFinishing) {
                activity.finish()
            }
        }

        fun removeAll() {
            for (activity in activitys) {
                if (!activity.isFinishing) {
                    activity.finish()
                }
            }
        }

        fun removeAll(vararg clazz: Class<*>) {

            for (activity in activitys) {
                var needDel = true
                for (clazz in clazz) {
                    if (activity.javaClass.isAssignableFrom(clazz)) {
                    needDel = false
                    }
                }
                if (needDel) {
                    if (!activity.isFinishing) {
                        activity.finish()
                    }
                }
            }


        }
    }

    fun removeAll(vararg clazz: Class<*>) {
        var isExist = false
        for (act in activitys) {
            for (c in clazz) {
                if (act.javaClass.isAssignableFrom(c)) {
                    isExist = true
                    break
                }
            }
            if (!isExist) {
                if (!act.isFinishing) {
                    act.finish()
                }
            } else {
                isExist = false
            }
        }
    }
}
