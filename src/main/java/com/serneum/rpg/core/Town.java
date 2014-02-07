/***********************************************************************
 *Author: Chris Rees and Wilfredo Velasquez
 *Date: 11/2/08
 *File Name: Town.java
 *Purpose: A simple town for selling/buying items and healing. Will be
 *connected to the map and possibly located using a coordinate style
 *system.
***********************************************************************/

package com.serneum.rpg.core;

import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import com.serneum.rpg.character.CharSelectScreen;
import com.serneum.rpg.character.Stats;

public class Town extends JFrame
{
    JButton map, shop, healer, inventory, quit;
    static JButton incB, decB;
    static JLabel blank1, blank2, blank3, blank4, blank5, town, randEvent, welcome, to, the;
    static JLabel something;

    public static String location = "Town";
    private int shopPage = 1;
    private static boolean pres = false;

    private static String line1 = "";
    private static String line2 = "";
    private static String rand = "";

    //Create GridBagLayout and constraints variable
    static Container pane = new Container();
    GridBagConstraints c = new GridBagConstraints();

    //Import images to use for buttons
    ImageIcon dec = new ImageIcon("Images\\dec.gif");
    ImageIcon inc = new ImageIcon("Images\\inc.gif");

    public Town()
    {
        //Initialize all variables
        map = new JButton("Map");
        shop = new JButton("Shop");
        healer = new JButton("Healer");
        inventory = new JButton("Inventory");
        blank1 = new JLabel(" ", SwingConstants.LEFT);
        blank2 = new JLabel(" ", SwingConstants.LEFT);
        blank3 = new JLabel(" ", SwingConstants.LEFT);
        blank4 = new JLabel(" ", SwingConstants.LEFT);
        blank5 = new JLabel(" ", SwingConstants.LEFT);
        quit = new JButton("Quit");
        welcome = new JLabel("Welcome", SwingConstants.CENTER);
        to = new JLabel("To", SwingConstants.CENTER);
        the = new JLabel("The", SwingConstants.CENTER);
        town = new JLabel("Town", SwingConstants.CENTER);
        randEvent = new JLabel("", SwingConstants.CENTER);
        something = new JLabel("", SwingConstants.CENTER);
        incB = new JButton(inc);
        decB = new JButton(dec);

        //Create action listener and add it to the JButtons
        ButtonHandler bHandler;
        bHandler = new ButtonHandler();
        map.addActionListener(bHandler);
        shop.addActionListener(bHandler);
        healer.addActionListener(bHandler);
        inventory.addActionListener(bHandler);

        //Create a listener just for the inc/dec buttons
        IncDecHandler idHandler;
        idHandler = new IncDecHandler();
        incB.addActionListener(idHandler);
        decB.addActionListener(idHandler);

        //Create listener for the Quit button
        eButtonHandler ebHandler;
        ebHandler = new eButtonHandler();
        quit.addActionListener(ebHandler);

        //Create content pane
        pane = getContentPane();
        pane.setLayout(new GridBagLayout());

        //Get a random event
        getRandEvent();

        //Place all objects in the pane
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.2;
        c.weighty = 0.2;
        pane.add(map, c);
        c.gridy = 1;
        pane.add(shop, c);
        c.gridy = 2;
        pane.add(healer, c);
        c.gridy = 3;
        pane.add(inventory, c);
        c.gridy = 4;
        pane.add(quit, c);
        c.gridwidth = 1;
        c.gridy = 0;
        c.gridx = 2;
        pane.add(welcome, c);
        c.gridx = 3;
        pane.add(to, c);
        c.gridx = 4;
        pane.add(the, c);
        c.gridx = 5;
        pane.add(town, c);
        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 4;
        pane.add(something, c);
        c.gridy = 2;
        pane.add(randEvent, c);
        c.gridy = 3;
        c.gridx = 3;
        c.gridwidth = 2;
        pane.add(blank1, c);
        c.gridy = 4;
        c.gridwidth = 4;
        pane.add(blank2, c);

        //Set GUI visible, set title and size.
        setTitle("Town");
        setSize(550, 200);
        setVisible(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    private class ButtonHandler implements ActionListener
      {
          @Override
        public void actionPerformed(ActionEvent e)
          {
              try
              {
                  if (location.equals("Town"))
                  {
                      if(e.getSource() == map)
                      {
                          Overworld map = new Overworld();
                          dispose();
                      }

                      if(e.getSource() == shop)
                      {
                          location = "Shop";
                          //Set default page to 1
                          shopPage = 1;
                          //Print out what page the user is on
                          blank2.setText("Page: " + shopPage);
                          //Place the inc/dec buttons
                          c.gridx = 2;
                          c.gridy = 4;
                          c.gridwidth = 1;
                          c.gridheight = 1;
                          c.anchor = GridBagConstraints.CENTER;
                          c.fill = GridBagConstraints.NONE;
                          c.weightx = 0.2;
                          c.weighty = 0.2;
                          pane.add(decB, c);
                          c.gridx = 5;
                          pane.add(incB, c);

                          //Remove and re-add blank1 in a way that centers it
                          pane.remove(blank2);
                          c.gridy = 4;
                        c.gridx = 3;
                        c.anchor = GridBagConstraints.CENTER;
                        c.gridwidth = 2;
                        pane.add(blank2, c);

                          //Default page 1 item choices
                          map.setText("Greatsword");
                          shop.setText("Longspear");
                          healer.setText("Maul");
                          inventory.setText("Back");

                          welcome.setText("Welcome");
                          to.setText("To");
                          the.setText("The");
                          town.setText("Store");

                          //Remove the random event when going to the store
                          something.setText("Welcome, " + Stats.charName.toString());
                          randEvent.setText("");
                          blank1.setText("");
                      }

                      if(e.getSource() == healer)
                      {
                          //Checks if the inc/dec buttons for the shop are present,
                          //and removes them if they are.
                          IncDecAreComponents();
                          if(pres == true)
                          {
                              pane.remove(decB);
                              pane.remove(incB);
                          }

                          location = "Healer";
                          map.setText("Full Heal");
                          shop.setText("Half Heal");
                          healer.setText("Rest");
                          inventory.setText("Back");
                          blank1.setText("");

                          welcome.setText("At");
                          to.setText("The");
                          the.setText("Healer's");
                          town.setText("Hut");

                          //Remove the random event when going to the healer
                          something.setText("Welcome, " + Stats.charName.toString());
                          randEvent.setText("");
                          blank1.setText("");
                      }

                      if(e.getSource() == inventory)
                      {
                          try
                          {
                              InvenScreen inv = new InvenScreen();
                          }
                          catch(FileNotFoundException FNFE)
                          {
                          }
                      }
                  }

                  else if (location.equals("Shop"))
                  {
                      //Defines the actions taken for each button if user enters the shop
                      if(e.getSource() == map)
                      {
                          if(shopPage == 1)
                          {
      //                    System.out.print("\nPage 1, Weapon 1");
                              Stats.wep = map.getText();
                          }
                          else if(shopPage == 2)
                          {
      //                    System.out.print("\nPage 2, Armour 1");
                              Stats.arm = map.getText();
                          }
                          else if(shopPage == 3)
                          {
      //                    System.out.print("\nPage 3, Shield 1");
                              Stats.shl = map.getText();
                          }
                          else if (shopPage == 4)
                              Inventory.addItem(map.getText());

                          if(shopPage == 1 || shopPage == 2 || shopPage == 3)
                          {
                              JOptionPane.showMessageDialog(null, "You have equipped " + map.getText(),
                                  "New Equipment", JOptionPane.INFORMATION_MESSAGE);
                          }

                          else if(shopPage == 4 && Inventory.full == false)
                          {
                              JOptionPane.showMessageDialog(null, "You have added " + map.getText() + " to slot " + Inventory.bagSlot,
                                  "New Item", JOptionPane.INFORMATION_MESSAGE);
                          }

                          Stats.printGear();
                      }

                      else if(e.getSource() == shop)
                      {
                          if(shopPage == 1)
                          {
      //                    System.out.print("\nPage 1, Weapon 2");
                              Stats.wep = shop.getText();
                          }

                          else if(shopPage == 2)
                          {
      //                    System.out.print("\nPage 2, Armour 2");
                              Stats.arm = shop.getText();
                          }
                          else if(shopPage == 3)
                          {
      //                    System.out.print("\nPage 3, Shield 2");
                              Stats.shl = shop.getText();
                          }
                          else if (shopPage == 4)
                              Inventory.addItem(shop.getText());

                          if(shopPage == 1 || shopPage == 2 || shopPage == 3)
                          {
                              JOptionPane.showMessageDialog(null, "You have equipped " + shop.getText(),
                                  "New Equipment", JOptionPane.INFORMATION_MESSAGE);
                          }

                          else if(shopPage == 4 && Inventory.full == false)
                          {
                              JOptionPane.showMessageDialog(null, "You have added " + shop.getText() + " to slot " + Inventory.bagSlot,
                                  "New Item", JOptionPane.INFORMATION_MESSAGE);
                          }

                          Stats.printGear();
                      }

                      else if(e.getSource() == healer)
                      {
                          if(shopPage == 1)
                          {
      //                    System.out.print("\nPage 1, Weapon 3");
                              Stats.wep = healer.getText();
                          }
                          else if(shopPage == 2)
                          {
      //                    System.out.print("\nPage 2, Armour 3");
                              Stats.arm = healer.getText();
                          }
                          else if(shopPage == 3)
                          {
      //                    System.out.print("\nPage 3, Shield 3");
                              Stats.shl = healer.getText();
                          }
                          else if (shopPage == 4)
                              Inventory.addItem(healer.getText());

                          if(shopPage == 1 || shopPage == 2 || shopPage == 3)
                          {
                              JOptionPane.showMessageDialog(null, "You have equipped " + healer.getText(),
                                  "New Equipment", JOptionPane.INFORMATION_MESSAGE);
                          }

                          else if(shopPage == 4 && Inventory.full == false)
                          {
                              JOptionPane.showMessageDialog(null, "You have added " + healer.getText() + " to slot " + Inventory.bagSlot,
                                  "New Item", JOptionPane.INFORMATION_MESSAGE);
                          }

                          Stats.printGear();
                      }

                      else if(e.getSource() == inventory)
                      {
                          //Checks if the inc/dec buttons for the shop are present,
                          //and removes them if they are.
                          IncDecAreComponents();
                          if(pres == true)
                          {
                              pane.remove(decB);
                              pane.remove(incB);
                          }

                          location = "Town";
                          map.setText("Map");
                          shop.setText("Shop");
                          healer.setText("Healer");
                          inventory.setText("Inventory");

                          welcome.setText("Welcome");
                          to.setText("To");
                          the.setText("The");
                          town.setText("Town");

                          something.setText("" + rand);
                          randEvent.setText("" + line1);
                          blank1.setText("" + line2);
                          blank2.setText("");

                          //Remove and re-add blank1 in a way that expands it
                          pane.remove(blank2);
                          c.gridy = 4;
                        c.gridx = 2;
                        c.gridwidth = 4;
                        pane.add(blank2, c);
                      }
                  }

                  else if(location.equals("Healer"))
                  {
                      if(e.getSource() == map)
                      {
                          Stats.hp = Stats.maxHP;
                          try
                          {
                              Stats.printStats();
                          }
                          catch(FileNotFoundException FNFE)
                          {
                          }

                          JOptionPane.showMessageDialog(null, "You have been healed to full health.",
                                  "Heal", JOptionPane.INFORMATION_MESSAGE);
                      }

                      if(e.getSource() == shop)
                      {
                          Stats.hp += (Stats.maxHP / 2);

                          if(Stats.hp > Stats.maxHP)
                              Stats.hp = Stats.maxHP;

                          Stats.printStats();

                          JOptionPane.showMessageDialog(null, "You have been healed for half of your maximum health.\n"
                              + "You now have " + Stats.hp + " HP", "Heal", JOptionPane.INFORMATION_MESSAGE);
                      }

                      if(e.getSource() == healer)
                      {
                          Stats.AP = Stats.maxAP;

                          Stats.printStats();

                          JOptionPane.showMessageDialog(null, "You feel rested.\n"
                              + "All AP restored.", "Rest", JOptionPane.INFORMATION_MESSAGE);
                      }

                      if(e.getSource() == inventory)
                      {
                          //Checks if the inc/dec buttons for the shop are present,
                          //and removes them if they are.
                          IncDecAreComponents();
                          if(pres == true)
                          {
                              pane.remove(decB);
                              pane.remove(incB);
                          }

                          location = "Town";
                          map.setText("Map");
                          shop.setText("Shop");
                          healer.setText("Healer");
                          inventory.setText("Inventory");

                          welcome.setText("Welcome");
                          to.setText("To");
                          the.setText("The");
                          town.setText("Town");

                          something.setText("" + rand);
                          randEvent.setText("" + line1);
                          blank1.setText("" + line2);
                      }
                  }
              }

              catch(FileNotFoundException FNFE)
              {
              }
          }
      }

      private class IncDecHandler implements ActionListener
      {
          @Override
        public void actionPerformed(ActionEvent e)
          {
              if(e.getSource() == incB && shopPage < 4)
              {
                  shopPage++;
                  blank2.setText("Page: " + shopPage);
              }

              if(e.getSource() == decB && shopPage > 1)
              {
                  shopPage--;
                  blank2.setText("Page: " + shopPage);
              }

              //Run the test to see what page the user is on after they click inc/dec buttons
              //Set buttons accordingly
              if (shopPage == 1)
              {
                  map.setText("Greatsword");
                  shop.setText("Longspear");
                  healer.setText("Maul");
                  inventory.setText("Back");
              }

              else if (shopPage == 2)
              {
                  map.setText("Chainmail");
                  shop.setText("Scale");
                  healer.setText("Plate");
              }

              else if (shopPage == 3)
             {
                 map.setText("Arm");
                 shop.setText("Light");
                 healer.setText("Heavy");
             }

             else if(shopPage == 4)
             {
                 map.setText("HP Potion");
                 shop.setText("AP Potion");
                 healer.setText("HP/AP Potion");
             }
          }
      }

      private class eButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            //Placeholder frame
              Frame frame = new Frame();

              //Define button text
              Object[] options = {"Yes, I'm sure", "No, I don't want to"};

              //Present user with option to quit the game
              int exit = JOptionPane.showOptionDialog(frame, "Are you sure you want to exit to the character screen?",
                  "Exit Game", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

              if(exit == JOptionPane.YES_OPTION)
              {
                  try
                  {
                      Stats.printStats();
                      Stats.printGear();
                      Inventory.printOut();
                  }
                  catch(FileNotFoundException FNFE)
                  {
                  }

                CharSelectScreen reload = new CharSelectScreen();
                dispose();
              }
        }
    }

      //Creates a random event
      private static void getRandEvent()
      {
          //Make a random number generator and get a number between 1 and 5
          Random generator = new Random();
          int random = generator.nextInt(5) + 1;

          //Get a string representation of the ID
          String ID = "ID" + random;
        String temp = "";

        InputStream eventStream = Town.class.getResourceAsStream("/data/Events.dat");
        Scanner randData = new Scanner(eventStream);

        while (randData.hasNext())
        {
            temp = randData.next();

            if (temp.equals(ID))
            {
                //ID1 means nothing happened
                if(temp.equals("ID1"))
                {
                    something.setText("");
                    randEvent.setText("");
                    blank1.setText("");
                }

                //IDs 2-5
                else
                {
                    //Set text according to event
                    rand = "Something has happened!";
                    something.setText("" + rand);
                    line1 = randData.next();
                    line2 = randData.next();

                    String find = "_";
                    String replace = " ";
                    int index = 0;

                    //Search for _ and replace with a space in both lines of the event
                    index = line1.indexOf(find);
                    while(index != -1)
                    {
                        line1 = line1.replace(find, replace);
                        index = line1.indexOf(find);
                    }

                    index = line2.indexOf(find);
                    while(index != -1)
                    {
                        line2 = line2.replace(find, replace);

                        index = line2.indexOf(find);
                    }

                    randEvent.setText("" + line1);
                    blank1.setText("" + line2);
                }
            }
        }
        randData.close();
    }

      //Checks to see if the inc/dec buttons are in the Container
      private static boolean IncDecAreComponents()
      {
          //Stores how many components are in the container
          int compCount = pane.getComponentCount();
          int count = 0;
          //Stores all components in an array to be used for searching
          Component[] array = new Component[compCount];
          array = pane.getComponents();

          //Searches for the components in the array. Because the buttons are ALWAYS together, it checks
          //if one is present, and then sets a boolean value to true if either one is found.
          for(count = 0; count < compCount; count++)
          {
              if(decB == array[count] || incB == array[count])
                  pres = true;
          }

          return pres;
      }
/*
    public static void main(String[] args)
    {
        Town menu = new Town();
    }
*/
}