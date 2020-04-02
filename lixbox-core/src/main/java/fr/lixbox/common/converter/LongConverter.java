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
package fr.lixbox.common.converter;

import fr.lixbox.common.util.StringUtil;



/**
 * Cette classe est le transtypeur de Long
 * 
 * @author ludovic.terral
 */
public class LongConverter extends BaseConverter
{
	// ----------- Attribut -----------
	private static final long serialVersionUID = -7025063558865307075L;



	// ----------- Methode ----------- 
    @Override    
    public Long convertFromPresentationFormat(String target) // $codepro.audit.disable
    {
        if (StringUtil.isEmpty(target))
        {
            return Long.valueOf(0);
        }        
        return Long.valueOf(target);
    }
    
    
    
    @Override
    public String formatForPresentation(Object target)
    {
        return (null == target) ? "0.0" : ((Long) target).toString(); //$NON-NLS-1$
    }
}
