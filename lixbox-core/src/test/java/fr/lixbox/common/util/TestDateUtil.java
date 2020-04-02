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


import java.util.Calendar;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.lixbox.common.util.DateUtil;


/**
 * Cette classe permet de valider l'utilitaire des dates
 * 
 * @author ludovic.terral
 */
public class TestDateUtil extends TestCase 
{
    // ----------- Attribut -----------
    public static final Log LOG = LogFactory.getLog(TestDateUtil.class);

    
    
    // ----------- Methode -----------
    public TestDateUtil(String arg0) 
    {
        super(arg0);
    }

    

    /**
     * Cette methode verifie l'ajout d'une heure au delai initial
     */
    public void testDateUtil_addDelayToCalendar() 
    {
        int heureDebut = 10;
        Calendar calFin = Calendar.getInstance();
        calFin.set(Calendar.HOUR_OF_DAY, heureDebut);
        try 
        {
            calFin = DateUtil.addDelayToCalendar(calFin, 3600000L);            
            assertTrue("Le resultat attendu n'est pas atteint", calFin.get(Calendar.HOUR_OF_DAY)==heureDebut+1);
        } 
        catch (Exception e) 
        {
            fail(e.getMessage());
        }
    }
}
