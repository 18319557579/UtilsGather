package com.example.utilsuser.delegate

import java.util.LinkedList


//class MyLis(theList: ArrayList<String>) : List<String> by theList {
fun getDdd() : LinkedList<String> {
    return LinkedList<String>()
}
class MyList7 : List<String> by getDdd() {



}
