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
package fr.lixbox.common.stream.util;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;

public class ElUtil
{
    private ElUtil()
    {
        // constructeur servant à transformer la classe en utilitaire
    }
    
    
    
    public static Object evalExpression(Map<String,Object> context , String expression)
    {
        JexlEngine jexl = new JexlBuilder().create();        
        JexlExpression e = jexl.createExpression(expression);
        JexlContext jc = new MapContext();
        
        for (Entry<String, Object> entry : context.entrySet())
        {
            jc.set(entry.getKey(), entry.getValue());            
        }
        return e.evaluate(jc);
    }
}
