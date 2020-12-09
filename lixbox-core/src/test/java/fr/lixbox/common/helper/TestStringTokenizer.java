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
package fr.lixbox.common.helper;

import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import junit.framework.TestCase;



/**
 * Cette classe permet de tester le fonctionnement du
 * StringTokenizer
 * 
 */
public class TestStringTokenizer extends TestCase
{
    // ----------- Attribut -----------   
    private static final Log LOG = LogFactory.getLog(TestStringTokenizer.class);
    
    public static final String chaineTest1 = ",AD,ALV,Andorra la Vella,Andorra la Vella,,--34-6--,AI,601,,4230N 00131E,";
    public static final String sepaTest1 = ",";
    public static final String chaineTest2 = "1.1D;0004;NON_RENSEIGNE;NS;OUEST;NS;ROUL_CONT;cf tableau du reglement local;NS;ENL_IM;0;GARDE_RAP;NS;NS;NON;OUI";
    public static final String sepaTest2 = ";";
    
    
    
    // ----------- Methode -----------
    public static void main(final String[] args)
    {
        junit.textui.TestRunner.run(TestStringTokenizer.class);
    }
    
    
    
    public TestStringTokenizer(String name)
    {
        super(name);
    }
    
    
    
    @Test
    public void testStringTokenizer_test1()
    {
        LOG.info("--------------------------------\nDebut testStringTokenizer_test1\n");
        final long debut = Calendar.getInstance().getTimeInMillis();
        
        
        //Preparation du test
        StringTokenizer token = null;
        
                
        //Execution du test    
        try
        { 
            token = new StringTokenizer(chaineTest1,sepaTest1);
        }
        catch (Exception e)
        {
            assertNull("Exception levee non attendue", e);
        }  
        
        
        //Verification du test     
        assertTrue("Token non realisee", (token!=null&&token.size()==12));


        final long duree = Calendar.getInstance().getTimeInMillis() - debut;
        LOG.info("duree:" + duree + " ms");  
        LOG.info("Fin testStringTokenizer_test1\n--------------------------------\n\n");
    }
    
    
    
    @Test
    public void testStringTokenizer_test2()
    {
        LOG.info("--------------------------------\nDebut testStringTokenizer_test2\n");
        final long debut = Calendar.getInstance().getTimeInMillis();
        
        
        //Preparation du test
        StringTokenizer token = null;
        
                
        //Execution du test    
        try
        { 
            token = new StringTokenizer(chaineTest2,sepaTest2);
        }
        catch (Exception e)
        {
            assertNull("Exception levee non attendue", e);
        }  
        
        
        //Verification du test     
        assertTrue("Token non realisee", (token!=null&&token.size()==16));


        final long duree = Calendar.getInstance().getTimeInMillis() - debut;
        LOG.info("duree:" + duree + " ms\n");  
        LOG.info("Fin testStringTokenizer_test2\n--------------------------------\n\n");
    }
}
