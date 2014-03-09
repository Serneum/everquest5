/***********************************************************************
 *Author: Chris Rees and Wilfredo Velasquez
 *Date: 12/4/08
 *File Name: Inventory.java
 *Purpose: All of the methods for handling inventory
***********************************************************************/

package com.serneum.rpg.core;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.serneum.rpg.character.Stats;

public class Inventory
{
    public static Vector<String> items = new Vector<String>();
    private static int count = 0;
    public static int bagSlot = 0;
    public static boolean full = false;

    //Used only when a new character is created. Creates the inventory file.
    public static void New() throws FileNotFoundException
    {
        PrintWriter outFile = new PrintWriter("Data\\Characters\\Character" + Stats.charNum + "\\Inventory.dat");

        outFile.printf("Slot1: ------%n");
        outFile.printf("Slot2: ------%n");
        outFile.printf("Slot3: ------%n");
        outFile.printf("Slot4: ------%n");
        outFile.printf("Slot5: ------%n");

        outFile.close();
        readIn();
    }

    //Reads in the inventory file to the game. Does a search to remove any _ and replace with spaces.
    //All items are stored in files containing _ instead of spaces in order to make them easier to read
    //but they shouldn't appear like that on buttons
    public static void readIn() throws FileNotFoundException
    {
        Scanner inFile = new Scanner(new FileReader("Data\\Characters\\Character" + Stats.charNum + "\\Inventory.dat"));

        count = 0;
        items.removeAllElements();

        String find = "_";
        String replace = " ";
        String item = "";
        int fInd = 0;

        while(count < 5)
        {
            inFile.next();
            item = inFile.next();

            if(item.equals("Slot" + count + ":"))
                item = "------";

            fInd = item.indexOf(find);
            if(fInd != -1)
                item = item.replace(find, replace);

            items.addElement(item);
            count++;
        }

        inFile.close();
    }

    //An extra method to make sure files are read in and printed properly.
    public static void fileReadIn() throws FileNotFoundException
    {
        Scanner inFile = new Scanner(new FileReader("Data\\Characters\\Character" + Stats.charNum + "\\Inventory.dat"));

        count = 0;
        items.removeAllElements();

        String item = "";

        while(count < 5)
        {
            inFile.next();
            item = inFile.next();
            items.addElement(item);
            count++;
        }

        inFile.close();
    }

    //Prints out any changes to the inventory file. Has a search feature to replace spaces with _
    //so that items can be read in much easier.
    public static void printOut() throws FileNotFoundException
    {
        PrintWriter outFile = new PrintWriter("Data\\Characters\\Character" + Stats.charNum + "\\Inventory.dat");
        int count = 0;
        String itm = "";

        for(count = 0; count < items.size(); count++)
        {
            itm = items.elementAt(count);

            String find = " ";
            String replace = "_";
            int fInd = 0;

            fInd = itm.indexOf(find);
            if(fInd != -1)
                itm = itm.replace(find, replace);

            outFile.printf("Slot" + (count + 1) + ": %s%n", itm);
        }

        outFile.close();
        readIn();
    }

    //"Uses" an item by removing it from the array and printing it back out to the file.
    public static void Use(int slot) throws FileNotFoundException
    {
        int index = (slot - 1);
        items.removeElementAt(index);
        items.insertElementAt("------", index);

        full = false;

        printOut();
        fileReadIn();
    }

    //Adds an item to the item array and prints it out to the file. Searches for any spaces and
    //replaces them with _ for easier reading in.
    public static void addItem(String itemName) throws FileNotFoundException
    {
        String defaultName = "------";
        int index = -1;

        String find = " ";
        String replace = "_";
        int fInd = 0;

        fInd = itemName.indexOf(find);
        if(fInd != -1)
            itemName = itemName.replace(find, replace);

        index = items.indexOf(defaultName);

        for(count = 0; count < 5; count++)
        {
            if(defaultName.equals(items.elementAt(count)))
            {
                index = count;
                //Break so that the index is always the lowest number instead of the highest.
                break;
            }
        }

        //If the default name of ------ cannot be found, the index is returned as -1. This means that the
        //5 slots of the inventory are full. Print out a message to the user
        if(index == -1)
        {
            JOptionPane.showMessageDialog(null, "Your inventory is full.", "Not Enough Space", JOptionPane.INFORMATION_MESSAGE);
            full = true;
        }

        else
        {
            items.removeElementAt(index);
            items.insertElementAt(itemName, index);
            bagSlot = index + 1;
        }

        printOut();
        fileReadIn();
    }
}