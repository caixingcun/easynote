package com.caixc.easynoteapp.exception

import java.lang.Exception


class ResponeThrowable(e: Throwable, var code: Int, override var message: String) : Exception(e)