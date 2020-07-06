/*******************************************************************************
 *    
 *                           FRAMEWORK Lixbox
 *                          ==================
 *      
 * This file is part of lixbox-common.
 *
 *    lixbox-iam is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    lixbox-iam is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *    along with lixbox-common.  If not, see <https://www.gnu.org/licenses/>
 *   
 *   @AUTHOR Lixbox-team
 *
 ******************************************************************************/
package fr.lixbox.common.util;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.lixbox.common.model.Evenement;
import fr.lixbox.common.util.CollectionUtil;
import junit.framework.TestCase;


/**
 * Cette classe permet de valider l'utilitaire de traitement
 * des collections.
 * 
 * @author ludovic.terral
 */
public class TestCollectionUtil extends TestCase 
{
    // ----------- Attribut -----------
    

    
    
    // ----------- Methode -----------
    public TestCollectionUtil(String arg0) 
    {
        super(arg0);
    }

    

    /**
     * Ce test permet de verifier l'efficacite de l'autoBoxing
     * des collections 
     * Il convertit une liste typee interface vers une liste
     * typee objet
     */
    public void testCollectionUtil_autoBoxList_1() 
    {
        System.out.println("--------------------------------------");
        System.out.println("Debut testCollectionUtil_autoBoxList_1");
        System.out.println("--------------------------------------\n");
        final long debut = Calendar.getInstance().getTimeInMillis();
        
        //Preparation du test
        final List<Evenement> liste = new ArrayList<Evenement>();
        liste.add(new Evenement());
        liste.add(new Evenement());
        liste.add(new Evenement());
        
        
        /*
         * Execution du test 
        */
        List<Evenement> listeAutoBoxee= null;
        try
        {
            listeAutoBoxee = CollectionUtil.autoBoxList(liste, Evenement.class);            
        }
        catch (Exception e)
        {
            assertNull("Exception levee non attendue", e);
        }        
                

        //Verification des resultats
        if (null != listeAutoBoxee && 3 == listeAutoBoxee.size())
        {
            System.out.println("==== Resultat testCollectionUtil_autoBoxList_1: Succes");
        }
        else
        {
            System.out.println("==== Resultat testCollectionUtil_autoBoxList_1: Echec");
            assertTrue("ListeAutoBoxee nulle", null != listeAutoBoxee);
            assertTrue("Contenu Contexte Incorrect", 3 == listeAutoBoxee.size());
        } 

        
        final long duree = Calendar.getInstance().getTimeInMillis() - debut;
        System.out.println("duree:" + duree + " ms");  
        System.out.println("------------------------------------");
        System.out.println("Fin testCollectionUtil_autoBoxList_1");
        System.out.println("------------------------------------\n");    
    }
    
    
    
    /**
     * Ce test permet de verifier l'efficacite de l'autoBoxing
     * des collections 
     * Il convertit une liste typee objet vers une liste
     * typee interface
     */
    public void testCollectionUtil_autoBoxList_2() 
    {
        System.out.println("--------------------------------------");
        System.out.println("Debut testCollectionUtil_autoBoxList_2");
        System.out.println("--------------------------------------\n");
        final long debut = Calendar.getInstance().getTimeInMillis();
        
        //Preparation du test
        final List<Evenement> liste = new ArrayList<Evenement>();
        liste.add(new Evenement());
        liste.add(new Evenement());
        liste.add(new Evenement());
        
        
        /*
         * Execution du test 
        */
        List<Evenement> listeAutoBoxee= null;
        try
        {
            listeAutoBoxee = CollectionUtil.autoBoxList(liste, Evenement.class);            
        }
        catch (Exception e)
        {
            assertNull("Exception levee non attendue", e);
        }        
                

        //Verification des resultats
        if (null != listeAutoBoxee && 3 == listeAutoBoxee.size())
        {
            System.out.println("==== Resultat testCollectionUtil_autoBoxList_2: Succes");
        }
        else
        {
            System.out.println("==== Resultat testCollectionUtil_autoBoxList_2: Echec");
            assertTrue("ListeAutoBoxee nulle", null != listeAutoBoxee);
            assertTrue("Contenu Contexte Incorrect", 3 == listeAutoBoxee.size());
        } 

        
        final long duree = Calendar.getInstance().getTimeInMillis() - debut;
        System.out.println("duree:" + duree + " ms");  
        System.out.println("------------------------------------");
        System.out.println("Fin testCollectionUtil_autoBoxList_2");
        System.out.println("------------------------------------\n");    
    }
}