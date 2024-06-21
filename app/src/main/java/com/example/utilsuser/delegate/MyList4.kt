package com.example.utilsuser.delegate

import java.util.ArrayList

//class MyLis(theList: ArrayList<String>) : List<String> by theList {
class MyList4 : List<String> by theList {
    companion object {
        val theList: List<String> = ArrayList<String>()
    }

}
