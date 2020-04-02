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

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.lixbox.common.exceptions.ProcessusException;
import fr.lixbox.common.resource.LixboxResources;


/**
 * Cette classe est le transtypeur de base
 * 
 * @author ludovic.terral
 */

public abstract class BaseConverter implements Serializable
{
    // ----------- Attribut -----------
    private static final long serialVersionUID = 201509041345L;
    protected static final Log LOG = LogFactory.getLog(BaseConverter.class);    
    private static final Map<Class<?>, Class<?>> REGISTRY = Collections.synchronizedMap(new HashMap<Class<?>,Class<?>>(32, 0.75f));

    static 
    {
        registerTranstypeur(String.class, StringConverter.class);
        registerTranstypeur(String[].class, StringConverter.class);
        registerTranstypeur(Date.class, DateConverter.class);
        registerTranstypeur(Calendar.class, CalendarConverter.class);
        registerTranstypeur(Integer.class, IntegerConverter.class);
        registerTranstypeur(int.class, IntegerConverter.class);
        registerTranstypeur(int[].class, IntegerConverter.class);
        registerTranstypeur(Double.class, DoubleNullableConverter.class);
        registerTranstypeur(double.class, DoubleConverter.class);
        registerTranstypeur(double[].class, DoubleConverter.class);
        registerTranstypeur(Double[].class, DoubleConverter.class);
        registerTranstypeur(Long.class, LongConverter.class);
        registerTranstypeur(long.class, LongConverter.class);
        registerTranstypeur(long[].class, LongConverter.class);
        registerTranstypeur(Long[].class, LongConverter.class);        
        registerTranstypeur(Boolean.class, BooleanConverter.class);
        registerTranstypeur(Boolean.TYPE, BooleanConverter.class);
        registerTranstypeur(Boolean[].class, BooleanConverter.class);
        registerTranstypeur(boolean[].class, BooleanConverter.class);
    }    
    
    
    
    // ----------- Methode ----------- 
    /**
     * Cette methode renvoie le Transtypeur adequat
     * 
     * @param aType la classe dy tpe a transtyper
     * 
     * @return Transtypeur     
     */
    public static BaseConverter getTranstypeur(Class<?> aType) 
    {
        final Class<?> type = transtypeurForType(aType);  // $codepro.audit.disable unusedAssignment
        BaseConverter transtypeur = null;
        try 
        {
            transtypeur = (BaseConverter) type.getConstructor().newInstance();
        }
        catch (InstantiationException  | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e)
        {
            throw new ProcessusException(LixboxResources.getString("ERROR.STRUTS.TRANSTYPEUR.CLASS.NON.INSTANCIABLE") + type, e);  //$NON-NLS-1$
        }  
        return transtypeur;
    }
    
    
    
    /**
     * Cette methode permet d'associer un type de donnee avec
     * un transtypeur. Un transtypeur peut etre associe a plusieurs types
     * 
     * @param type 
     * @param transtypeurType
     */
    public static void registerTranstypeur(Class<?> type, Class<?> transtypeurType) 
    {
        REGISTRY.put(type, transtypeurType);
    }
    
    
    
    /**
     * Cette methode retourne une instance d'un transtypeur
     * associe a un type de donnee..
     * 
     * @param type
     *
     * @return une instance du transtypeur associe
     */
    public static Class<?> transtypeurForType(Class<?> type) // $codepro.audit.disable multipleReturns
    {
        if (null == type)
        {
            throw new IllegalArgumentException(LixboxResources.getString("ERROR.PARAM.INCORRECT.02", "type"));  //$NON-NLS-1$//$NON-NLS-2$
        }
        
        for (Entry<Class<?>, Class<?>> currType : REGISTRY.entrySet())
        {
            if (currType.getKey().isAssignableFrom(type))
            {
                return currType.getValue();
            }
        }
        return StringConverter.class; 
    }

       

    /**
     * Cette methode permet d'obtenir la representation
     * d'un objet donne pour affichage.
     * 
     * @param target
     * 
     * @return une string ou null
     */
    public abstract String formatForPresentation(Object target);
    
    
    
    /**
     * Cette methode permet d'obtenir un objet 
     * a partir de sa representation d'affichage.
     * 
     * @param target
     * 
     * @return une instance de l'objet converti
     */
    public abstract Object convertFromPresentationFormat(String target);
}
