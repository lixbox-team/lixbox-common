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


import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import junit.framework.TestCase;


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
