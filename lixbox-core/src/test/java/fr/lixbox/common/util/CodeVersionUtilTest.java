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

import org.junit.Assert;
import org.junit.Test;


/**
 * Cette suite de test vérifie la classe CodeVersionUtil
 * 
 * @author ludovic.terral
 */
public class CodeVersionUtilTest
{
    @Test
    public final void testGetVersion()
    {
        Assert.assertTrue("Bad version read waited version_test received "+CodeVersionUtil.getVersion(this.getClass()), "0.8.2".equals(CodeVersionUtil.getVersion(this.getClass())));
    }
}
