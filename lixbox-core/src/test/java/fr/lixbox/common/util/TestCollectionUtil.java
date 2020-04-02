/*******************************************************************************
 *    
 *                           FRAMEWORK Lixbox
 *                          ==================
 *      
 *   Copyrigth - LIXTEC - Tous droits reserves.
 *   
 *   Le contenu de ce fichier est la propriete de la societe Lixtec.
 *   
 *   Toute utilisation de ce fichier et des informations, sous n'importe quelle
 *   forme necessite un accord ecrit explicite des auteurs
 *   
 *   @AUTHOR Ludovic TERRAL
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