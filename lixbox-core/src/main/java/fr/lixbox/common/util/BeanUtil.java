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

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.expression.Resolver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.lixbox.common.exceptions.BusinessException;
import fr.lixbox.common.exceptions.ProcessusException;
import fr.lixbox.common.resource.LixboxResources;

/**
 * Cette classe effectue des operations sur les Beans.
 * 
 * @author ludovic.terral
 */
public class BeanUtil extends BeanUtilsBean
{
    // ----------- Attribut -----------
    protected static final Log LOG = LogFactory.getLog(BeanUtil.class);    
    protected static final BeanUtil instance = new BeanUtil();

    
        
    // ----------- Methode ----------- 
    public static BeanUtilsBean getInstance()
    {
        return instance;
    }
       
   
    
    /**
     * Cette methode renvoie la description de la propriete d'un bean.
     *
     * @param bean
     * @param propertyName
     * 
     * @return PropertyDescriptor
     *
     * @throws ProcessusException
     * @throws BusinessException
     */
    public static PropertyDescriptor getPropertyDescriptor(Object bean, String propertyName)
    {
        try
        {
            return instance.getPropertyUtils().getPropertyDescriptor(bean, propertyName);
        }
        catch (Exception e)
        {
            throw new ProcessusException(e);
        }
    }    
    
        
    
    /**
     * Cette methode copie les proprietes d'un objet vers un autre.
     * Il supporte la copie optimiste afin de ne pas remplacer les
     * champs null
     *
     * @param dest
     * @param orig
     * @param optimisteCopy
     *
     * @throws ProcessusException
     * @throws BusinessException
	 */
    public static void copyProperties(Object dest, Object orig, boolean optimisteCopy)
    {
        copyProperties(dest, orig, optimisteCopy, new String[]{});
    }
    
    
    
    /**
     * Cette methode copie les proprietes d'un objet vers un autre.
     * Il supporte la copie optimiste afin de ne pas remplacer les
     * champs null et l'exclusion de propriete
     *
     * @param dest
     * @param orig
     * @param optimisteCopy
     * @param excludeProperties
     * 
    
     * @throws ProcessusException
     * @throws BusinessException
	 */
    public static void copyProperties(Object dest, Object orig, boolean optimisteCopy,
            String[] excludeProperties)
    {
        // Copy the properties, converting as necessary
        if (orig instanceof DynaBean)
        {
            copyPropertiesDynaBean(dest, orig, optimisteCopy, excludeProperties);
        }
        else if (orig instanceof Map<?, ?>)
        {
            copyPropertiesMap(dest, orig, optimisteCopy, excludeProperties);
        }
        else
        {
            copyPropertiesPojo(dest, orig, optimisteCopy, excludeProperties);
        }
    }
    
    
    
    /**
     * Cette methode copie les valeurs d'une map
     * 
     * @param dest
     * @param orig
     * @param optimisteCopy
     * @param excludeProperties
     * 
     * @throws ProcessusException
     */
    private static void copyPropertiesMap(Object dest, Object orig, boolean optimisteCopy,  String[] excludeProperties) // $codepro.audit.disable cyclomaticComplexity
    {
        String name = null;
        Object value = null;        
        final List<String> listeExcludeProperties = new ArrayList<>();        
        final String[] names = ((Map<?, ?>) orig).keySet().toArray(new String[((Map<?, ?>) orig).keySet().size()]); // $codepro.audit.disable possibleNullPointer
        if (names == null)
        {
            throw new ProcessusException(LixboxResources.getString("ERROR.PARAM.INCORRECT.02", "names"));
        }
        CollectionUtil.addAll(listeExcludeProperties, excludeProperties);
               
        
        // copie les valeurs d'une map        
        for (int i=0; i < names.length; i++)            
        {
            name = names[i];
            if (getInstance().getPropertyUtils().isWriteable(dest, name))
            {
                value = ((Map<?, ?>) orig).get(name);
                if ((optimisteCopy || (null != value)) && !listeExcludeProperties.contains(name))
                {
                    try
                    {
                        getInstance().copyProperty(dest, name, value);
                    }
                    catch (IllegalAccessException | InvocationTargetException e)
                    {
                        throw new ProcessusException(e);
                    }
                }
            }
        }            
    }
    
    
    
    /**
     * Cette methode copie les proprietes d'un pojo sur un autre
     * 
     * @param dest
     * @param orig
     * @param optimisteCopy
     * @param excludeProperties
     * 
     * @throws ProcessusException
     */
    private static void copyPropertiesPojo(Object dest, Object orig, boolean optimisteCopy, String[] excludeProperties) // $codepro.audit.disable cyclomaticComplexity
    {
        String name = null;
        Object value = null;
        final PropertyDescriptor[] origDescriptors = getInstance().getPropertyUtils().getPropertyDescriptors(orig);
        final List<String> listeExcludeProperties = new ArrayList<>();
        CollectionUtil.addAll(listeExcludeProperties, excludeProperties);
        
        if (origDescriptors == null)
        {
            throw new ProcessusException(LixboxResources.getString("ERROR.PARAM.INCORRECT.02", "origDescriptors"));
        }
        for (int i = 0; i < origDescriptors.length; i++)
        {
            name = origDescriptors[i].getName();
            if ("class".equals(name)) // $codepro.audit.disable stringMethodUsage
            {
                continue; // No point in trying to set an object's class
            }
            if (getInstance().getPropertyUtils().isReadable(orig, name)
                    && getInstance().getPropertyUtils().isWriteable(dest, name))
            {
                try
                {
                    value = getInstance().getPropertyUtils().getSimpleProperty(orig, name);
                    if ((optimisteCopy || (null != value)) && !listeExcludeProperties.contains(name))
                    {
                        getInstance().copyProperty(dest, name, value);
                    }
                }
                catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e)
                {
                    throw new ProcessusException(e);
                }
            }
        }
    } 
    
    
    
    /**
     * <p>Copy the specified property value to the specified destination bean,
     * performing any type conversion that is required.  If the specified
     * bean does not have a property of the specified name, or the property
     * is read only on the destination bean, return without
     * doing anything.  If you have custom destination property types, register
     * {@link Converter}s for them by calling the <code>register()</code>
     * method of {@link ConvertUtils}.</p>
     *
     * <p><strong>IMPLEMENTATION RESTRICTIONS</strong>:</p>
     * <ul>
     * <li>Does not support destination properties that are indexed,
     *     but only an indexed setter (as opposed to an array setter)
     *     is available.</li>
     * <li>Does not support destination properties that are mapped,
     *     but only a keyed setter (as opposed to a Map setter)
     *     is available.</li>
     * <li>The desired property type of a mapped setter cannot be
     *     determined (since Maps support any data type), so no conversion
     *     will be performed.</li>
     * </ul>
     *
     * @param bean Bean on which setting is to be performed
     * @param name Property name (can be nested/indexed/mapped/combo)
     * @param value Value to be set
     *
     * @exception IllegalAccessException if the caller does not have
     *  access to the property accessor method
     * @exception InvocationTargetException if the property accessor method
     *  throws an exception
     */
    @Override
    public void copyProperty(Object bean, String name, Object value) throws IllegalAccessException, InvocationTargetException
    {
        // Resolve any nested expression to get the actual target bean
        Object target = bean;
        Resolver resolver = getPropertyUtils().getResolver();
        while (resolver.hasNested(name))
        {
            try
            {
                target = getPropertyUtils().getProperty(target, resolver.next(name));
                name = resolver.remove(name);
            }
            catch (NoSuchMethodException e)
            {
                return; // Skip this property setter
            }
        }

        // Declare local variables we will require
        String propName = resolver.getProperty(name);
        Class<?> type = null; // Java type of target property
        int index = resolver.getIndex(name); // Indexed subscript value (if any)
        String key = resolver.getKey(name); // Mapped key value (if any)
        
        // Calculate the target property type
        if (target instanceof DynaBean)
        {
            DynaClass dynaClass = ((DynaBean) target).getDynaClass();
            DynaProperty dynaProperty = dynaClass.getDynaProperty(propName);
            if (dynaProperty == null)
            {
                return; // Skip this property setter
            }
            type = dynaProperty.getType();
        }
        else
        {
            PropertyDescriptor descriptor = null;
            try
            {
                descriptor = getPropertyUtils().getPropertyDescriptor(target, name);
                if (descriptor == null)
                {
                    return; // Skip this property setter
                }
            }
            catch (NoSuchMethodException e)
            {
                return; // Skip this property setter
            }
            type = descriptor.getPropertyType();
            if (type == null)
            {
                return;
            }
        }

        // Convert the specified value to the required type and store it
        if (index >= 0)
        { // Destination must be indexed
            value = convert(value, type.getComponentType());
            try
            {
                getPropertyUtils().setIndexedProperty(target, propName, index, value);
            }
            catch (NoSuchMethodException e)
            {
                throw new InvocationTargetException(e, "Cannot set " + propName);
            }
        }
        else if (key != null)
        { 
            // Destination must be mapped
            // Maps do not know what the preferred data type is,
            // so perform no conversions at all
            try
            {
                getPropertyUtils().setMappedProperty(target, propName, key, value);
            }
            catch (NoSuchMethodException e)
            {
                throw new InvocationTargetException(e, "Cannot set " + propName);
            }
        }
        else
        { 
            // Destination must be simple
            if (value != null)
            {
                value = convert(value, type);
            }
            try
            {
                getPropertyUtils().setSimpleProperty(target, propName, value);
            }
            catch (NoSuchMethodException e)
            {
                throw new InvocationTargetException(e, "Cannot set " + propName);
            }
        }
    }
    
    
    
    /**
     * Cette methode copie les proprietes d'un dynabean vers un autre
     * 
     * @param dest
     * @param orig
     * @param optimisteCopy
     * @param excludeProperties
     * 
     * @throws ProcessusException
     */
    private static void copyPropertiesDynaBean(Object dest, Object orig, boolean optimisteCopy,
            String[] excludeProperties)
    {
        String name = null;
        Object value = null;
        final List<String> listeExcludeProperties = new ArrayList<>();
        final DynaProperty[] origDescriptors = ((DynaBean) orig).getDynaClass().getDynaProperties(); // $codepro.audit.disable possibleNullPointer
        CollectionUtil.addAll(listeExcludeProperties, excludeProperties);        
        if (origDescriptors == null)
        {
            throw new ProcessusException(LixboxResources.getString("ERROR.PARAM.INCORRECT.02", "origDescriptors"));
        }
        for (int i = 0; i < origDescriptors.length; i++)
        {
            name = origDescriptors[i].getName();
            if (getInstance().getPropertyUtils().isWriteable(dest, name))
            {
                value = ((DynaBean) orig).get(name);
                if ((optimisteCopy || (null != value)) && !listeExcludeProperties.contains(name))
                {
                    try
                    {
                        getInstance().copyProperty(dest, name, value);
                    }
                    catch (IllegalAccessException  | InvocationTargetException e)
                    {
                        throw new ProcessusException(e);
                    }
                }
            }
        }
    }
    
        
    
    /***
     * Cette methode verifie si un object est assignable par
     * une classe donnee.
     * 
     * @param object
     * @param classe
     *  
     * @return true si assignable
     *          false si non 
	 */
    public static boolean estCastable(Object object, Class<?> classe)
    {
        boolean result = false;
        try
        {
            if (classe != null)
            {
                LOG.trace(classe.cast(object));
                result = true;
            }
        }
        catch (ClassCastException e)
        {
            LOG.trace(e);
        }
        return result;
    }
    
    
    
    /**
     * Cette methode recupere le contenu d'un attribut d'un objet.
     * 
     * @param objet
     * @param name
     * 
	 * @return l'objet image de l'attribut 
	 */
    public static Object getAttribut(Object objet, String name)
    {       
        Object result = null;
        try
        {
            result = getInstance().getPropertyUtils().getProperty(objet, name);
        }
        catch (Exception e)
        {
            LOG.trace(e);
        }
        return result;
    }
}
