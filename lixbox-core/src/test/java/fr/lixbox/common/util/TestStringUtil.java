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


import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.lixbox.common.util.StringUtil;


/**
 * Cette classe permet de valider l'utilitaire de traitement
 * des strings.
 * 
 * @author ludovic.terral
 */
public class TestStringUtil extends TestCase 
{
    // ----------- Attribut -----------
    private static final Log LOG = LogFactory.getLog(TestStringUtil.class);

    
    
    // ----------- Methode -----------
    public TestStringUtil(String arg0) 
    {
        super(arg0);
    }

    

    /**
     * Cette methode permet de verifier la suppression des accents
     * dans une chaine.
     */
    public void testStringUtil_sansAccent() 
    {
        try 
        {
            final String chaine = "Accès à la base ";
            final String chaine2 = StringUtil.removeAccent(chaine);
            LOG.info("chaine origine      : " + chaine);
            LOG.info("chaine sans accents : " + chaine2);
            assertFalse(chaine.equals(chaine2));
        } 
        catch (Exception e) 
        {
            assertTrue(false);
        }
    }


    
    /**
     * Cette methode permet de verifier si on est reellement capable d'evaluer
     * la composition d'une chaine.
     */
    public void testStringUtil_isEmpty() 
    {
        try 
        {
            final String chaine1 = "coucou";
            final String chaine2 = "\tcoucou";
            final String chaine3 = "\t";
            final String chaine4 = "\n";
            final String chaine5 = null;
            assertFalse(StringUtil.isEmpty(chaine1));            
            assertFalse(StringUtil.isEmpty(chaine2));
            assertTrue(StringUtil.isEmpty(chaine3));
            assertTrue(StringUtil.isEmpty(chaine4));
            assertTrue(StringUtil.isEmpty(chaine5));
        } 
        catch (Exception e) 
        {
            assertTrue(false);
        }
    }
}
