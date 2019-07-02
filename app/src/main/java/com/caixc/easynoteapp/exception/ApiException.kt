package com.caixc.easynoteapp.exception

import java.io.IOException
import java.lang.Exception

class ApiException : IOException {
    constructor(code:Int,msg:String)
}
