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
