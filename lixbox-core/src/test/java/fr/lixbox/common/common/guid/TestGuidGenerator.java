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
package fr.lixbox.common.common.guid;


import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import fr.lixbox.common.guid.GuidGenerator;


/**
 * Cette classe permet de valider le generateur de GUID
 */
public class TestGuidGenerator extends TestCase 
{
    // ----------- Attribut -----------
    private static final Log LOG = LogFactory.getLog(TestGuidGenerator.class); // NOPMD by ludovic.terral on 12/09/07 09:45

    
    
    // ----------- Methode -----------
    public TestGuidGenerator(String arg0) 
    {
        super(arg0);
    }

    

    /**
     * Cette methode permet de verifier le fonctionnement du 
     * generateur dans sa fonction generation OID
     */
    @Test()
    public void testGuidGeneratorGetGUID() 
    {
        try 
        {
            final String oid1 = GuidGenerator.getGUID(this);
            final String oid2 = GuidGenerator.getGUID(this);
            LOG.info("Valeur OID1=" + oid1 + "  " + oid1.length());
            LOG.info("Valeur OID2=" + oid2 + "  " + oid2.length());
            assertFalse("Erreur de generation, pas d'unicite", oid1.equals(oid2));
        } 
        catch (Exception e) 
        {
            assertNull("Erreur Inattendue", e);
        }
    }

    

    /**
     * Cette methode permet de verifier le fonctionnement du 
     * generateur dans sa fonction generation de type LOT
     */
    @Test()
    public void testGuidGeneratorGetNumeroRef() 
    {
        try 
        {
            final String oid1 = GuidGenerator.getNumeroRef("L");
            final String oid2 = GuidGenerator.getNumeroRef("L");
            LOG.info("Valeur OID1=" + oid1 + "  " + oid1.length());
            LOG.info("Valeur OID2=" + oid2 + "  " + oid2.length());
            assertFalse("Erreur de generation, pas d'unicite", oid1.equals(oid2));
        } 
        catch (Exception e) 
        {
            assertNull("Erreur Inattendue", e);
        }
    }

    

    /**
     * Cette methode permet de verifier le fonctionnement du 
     * generateur dans sa fonction generation de type Marchandise
     */
    @Test()
    public void testGuidGeneratorGetNumeroRef2() 
    {
        try 
        {
            final String oid1 = GuidGenerator.getNumeroRef("M");
            final String oid2 = GuidGenerator.getNumeroRef("M");
            LOG.info("Valeur OID1=" + oid1 + "  " + oid1.length());
            LOG.info("Valeur OID2=" + oid2 + "  " + oid2.length());
            assertFalse("Erreur de generation, pas d'unicite", oid1.equals(oid2));
        } 
        catch (Exception e) 
        {
            assertNull("Erreur Inattendue", e);
        }
    }
}
