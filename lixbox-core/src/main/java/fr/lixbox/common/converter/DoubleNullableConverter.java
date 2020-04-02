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
 * Cette classe est le transtypeur de Double
 * 
 * @author ludovic.terral
 */
public class DoubleNullableConverter extends BaseConverter
{
	// ----------- Attribut -----------
	private static final long serialVersionUID = -3541922517195044295L;



	// ----------- Methode ----------- 
    @Override
    public Double convertFromPresentationFormat(String target) 
    {
        if (StringUtil.isEmpty(target))
        {
            return null;
        }       
        target = target.replace(",",".");
        return Double.valueOf(target);
    }
    
    
    
    @Override
    public String formatForPresentation(Object target)
    {
        return (null == target) ? "0.0" : ((Double) target).toString(); //$NON-NLS-1$
    }
}
