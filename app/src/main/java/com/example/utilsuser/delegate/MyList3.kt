package com.example.utilsuser.delegate

import java.util.ArrayList

//class MyLis(theList: ArrayList<String>) : List<String> by theList {
class MyList3 : List<String> by theList {

    val ttt = theList
    object theList : ArrayList<String>()
}
