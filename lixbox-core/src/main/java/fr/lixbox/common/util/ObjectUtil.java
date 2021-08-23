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

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.lixbox.common.exceptions.ProcessusException;
import fr.lixbox.common.model.ConteneurEvenement;
import fr.lixbox.common.model.Contexte;
import fr.lixbox.common.model.enumeration.NiveauEvenement;
import fr.lixbox.common.resource.LixboxResources;

/**
 * Cette classe est un utilitaire de traitement des objets.
 * 
 * @author ludovic.terral
 */
public class ObjectUtil extends ObjectUtils
{
    // ----------- Attribut -----------
    protected static final Log LOG = LogFactory.getLog(ObjectUtil.class);
    
    
    
    // ----------- Methode ----------- 
    /**
     * Cette methode clone un objet
     * 
     * @param objetPourClone
     */
    @SuppressWarnings("unchecked")
    public static <T> T cloneObject(T objetPourClone) 
    {
        try
        {
            T objClone = null;
            if (null != objetPourClone)
			{
            	try
    			{
                	objClone = (T) BeanUtil.getInstance().cloneBean(objetPourClone);
    			}
    			catch (InstantiationException ie)
    			{    			    
    				LOG.trace("ERREUR NORMALE CAR L'OBJET A CLONE EST DE TYPE PRIMITIF", ie);
    			}
            	catch (Exception e)
            	{
            	    LOG.error(e);
            	}
                if (ObjectUtils.notEqual(objetPourClone, objClone))
                {
                    objClone=SerializationUtils.deserialize(SerializationUtils.serialize((Serializable) objetPourClone)); // $codepro.audit.disable unnecessaryCast
                }
			}            
            return objClone;
        }
        catch (Exception e)
        {
            throw new ProcessusException(e);
        }
    }
    
    
    
    /**
     * Cette methode introspecte deux objets s'ils sont de meme nature.
     * Elle extrait les proprietes qui contiennent des valeurs differentes.
     * 
     * @param objet1
     * @param objet2
     * @param pathLogique
     * @param excludedProperties
     * 
     * @return une liste de differences sous la forme
     *         d'un conteneur d'evenements
     */
    public static ConteneurEvenement extraireDifference(Object objet1, Object objet2, 
            String pathLogique, String[] excludedProperties)
    {
        final var contEvent=new ConteneurEvenement();
        final var contexte = new Contexte();
        contexte.put("parent", pathLogique); //$NON-NLS-1$     
        final List<String> listeExcludeProperties = new ArrayList<>();
        CollectionUtil.addAll(listeExcludeProperties, excludedProperties);
        
        //verifier les parametres
        if (((null != objet1) && (null != objet2)) &&
            !objet1.getClass().equals(objet2.getClass()))
        {
            throw new ProcessusException(LixboxResources.getString("ERROR.PARAM.INCOMPATIBLE.01"));
        }
        
        
        //analyser les differences
        //C3: un des deux null
        if (((null == objet1) && (null != objet2)) && (!StringUtil.isEmpty(String.valueOf(objet2))))
        {
            contEvent.add(
                NiveauEvenement.INFO,
                LixboxResources
                    .getString(
                            "INFO.PARAM.VALEUR.DIFFERENTE",
                            new String[]
                               {
                                    pathLogique,
                                    null,
                                    StringUtil.stringValueOfObject(objet2, 55)
                               }),
                contexte);
        }
        if (((null != objet1) && (null == objet2)) && (!StringUtil.isEmpty(String.valueOf(objet1))))
        {
            contEvent.add(
                NiveauEvenement.INFO,
                LixboxResources
                    .getString(
                            "INFO.PARAM.VALEUR.DIFFERENTE",
                            new String[]
                               {
                                    pathLogique,
                                    null,
                                    StringUtil.stringValueOfObject(objet1, 55)
                               }),
                contexte);
        }
        
        //C4: non null && fondamentalement different
        if (((null != objet1) && (null != objet2)) && !objet1.equals(objet2))
        {        
            try
            {
                final Field[] listeField1 = objet1.getClass().getDeclaredFields();
                final Field[] listeField2 = objet2.getClass().getDeclaredFields();
        
                //traitement
                if (isSimpleType(objet1))
                {
                    if (objet1 instanceof Calendar && objet2 instanceof Calendar)
                    {
                        if (((Calendar)objet1).getTimeInMillis()!=((Calendar)objet2).getTimeInMillis())
                        {
                            contEvent.add(
                                    NiveauEvenement.INFO,
                                    LixboxResources
                                        .getString(
                                                "INFO.PARAM.VALEUR.DIFFERENTE",
                                                new String[]
                                                   {
                                                        pathLogique,
                                                        StringUtil.stringValueOfObject(objet1, 55),
                                                        StringUtil.stringValueOfObject(objet2, 55)
                                                   }),
                                    contexte); 
                        }
                    }
                    else if (!objet1.equals(objet2))
                    {
                        contEvent.add(
                                NiveauEvenement.INFO,
                                LixboxResources
                                    .getString(
                                            "INFO.PARAM.VALEUR.DIFFERENTE",
                                            new String[]
                                               {
                                                    pathLogique,
                                                    StringUtil.stringValueOfObject(objet1, 55),
                                                    StringUtil.stringValueOfObject(objet2, 55)
                                               }),
                                contexte); 
                    }
                }
                else
                {                        
                    for (var j=0; j < listeField1.length; j++)
                    {
                        if (!listeExcludeProperties.contains(listeField1[j].getName()))
                        {
                            try
                            {
                                Object sField1 = PropertyUtils.getNestedProperty(objet1, listeField1[j].getName());
                                Object sField2 = PropertyUtils.getNestedProperty(objet2, listeField2[j].getName());                                
                                contEvent.addAll(
                                    extraireDifference(
                                            sField1, 
                                            sField2, 
                                            pathLogique + "." + listeField1[j].getName(), excludedProperties));
                            }
                            catch (NoSuchMethodException nsme)
                            {
                                //field sans accesser/ on continue
                            }
                            catch (Exception e)
                            {
                                //field sans accesser/ on continue
                            }
                        }
                    }
                }
            }
            catch (Exception e)
            {
                throw new ProcessusException(e);
            }
        }
        return contEvent;
    }
    
    
    
    /**
     * Cette methode determine si un objet passe en parametre
     * est dis de type simple ou complexe
     * ex: une string est de type simple
     *     une voiture et ses attributs est de type complexe
     * 
     * @param objet
     * 
     * @return true si le type est simple
     *         false si le type est complexe     
     */
    public static boolean isSimpleType(Object objet)
    {
        var isSimple = true;          
        final int length = objet.getClass().getDeclaredFields().length;
        for (var j=0; j < length; j++)
        {
            try
            {
                LOG.trace(PropertyUtils.getNestedProperty(objet, objet.getClass().getDeclaredFields()[j].getName()));
                isSimple=false;
                break;
            }
            catch (NoSuchMethodException nsme) 
            {
                //field sans accesser/ on continue
            }
            catch (Exception e)
            {
                //field sans accesser/ on continue
            }                       
        }   
        return isSimple;
    }
}
