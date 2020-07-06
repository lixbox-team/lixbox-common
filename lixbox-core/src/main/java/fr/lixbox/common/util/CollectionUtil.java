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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.lixbox.common.exceptions.ProcessusException;
import fr.lixbox.common.resource.LixboxResources;

/**
 * Cette classe effectue des operations sur les collections.
 * 
 * @author ludovic.terral
 */
public class CollectionUtil
{
    // ----------- Attribut -----------
    protected static final Log LOG = LogFactory.getLog(CollectionUtil.class); 


        
    // ----------- Methode -----------     
    private CollectionUtil()
    {        
        //private constructor
    }
    
    
    
    /**
     * Cette methode filtre les doublons dans une liste.
     * 
     * @param listeAFiltrer
     * 
     * @return la liste filtree
     */
    public static List<?> filtrerDoublon(List<?> listeAFiltrer)
    {
        List<?> result = null;
        try
        { 
            final HashMap<Integer, Object> temp = new HashMap<>(32, 0.75f);        
            for (Object objet : listeAFiltrer)
            {
                temp.put(objet.hashCode(), objet);
            }
            result = CollectionUtil.convertAnyListToArrayList(temp.values());
        }
        catch (Exception e)
        {
            LOG.fatal(e);
        }
        return result;
    }



    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void addAll(Collection collection, Object[] elements)
    {
        Collections.addAll(collection, elements);
    }
    
    
    
	/**
     * Cette methode teste si une collection est null ou vide.
     * 
     * @param col
     * 
     * @return true si null ou vide
     *         false si non vide             
     */ 
    public static boolean isEmpty(Collection<?> col)
    {
        return ((null == col) || col.isEmpty());
    }
    
    
    
    /**
     * Cette methode teste si une collection est null ou vide.
     * 
     * @param col
     * 
     * @return true si null ou vide
     *         false si non vide             
     */ 
    public static boolean isNotEmpty(Collection<?> col)
    {
        return !isEmpty(col);
    }
    
    
    
    /**
     * Cette methode convertit un objet Set en 
     * objet ArrayList
     * 
     * @param col
     * 
     * @return un arrayList image du Set
     * 
     * @throws ProcessusException
     */
    public static <T> List<T> convertSetToArrayList(Set<T> col)
    {
        if (isEmpty(col))
        {
            throw new ProcessusException(LixboxResources.getString("ERROR.PARAM.INCORRECT.02", "col"));
        }
        final List<T> array = new ArrayList<>();
        for (final Iterator<T> iter = col.iterator(); iter.hasNext();)
        {
           array.add(iter.next());            
        }
        return array;
    }
    
    
    
    /**
     * Cette methode convertit un objet implementant Collection en 
     * objet ArrayList
     * 
     * @param col
     * 
     * @return un arrayList image de col
     * 
     * @throws ProcessusException
     */
    public static <T> List<T> convertAnyListToArrayList(Collection<T> col)
    {
        if (null == col)
        {
            throw new ProcessusException(LixboxResources.getString("ERROR.PARAM.INCORRECT.02", "col"));
        }
        final List<T> liste = new ArrayList<>();
        for (final Iterator<T> iter = col.iterator(); iter.hasNext();)
        {
            liste.add(iter.next());            
        }
        return liste;
    }
    
    
    
    /**
     * Cette methode convertit une liste d'objets recus en parametre en 
     * une liste d'objets convertis au format demande
     * 
     * @param list
     * @param destination
     * 
     * @return une List contenant des objets du type represente par destination
     * 
     * @throws ProcessusException
     */
    public static <T> List<T> autoBoxList(List<?> list, Class<T> destination)
    {      
        final List<T> result = new ArrayList<>();
        if ((null != list) && (null != destination))
        {
            final int length = list.size();
            for (int i = 0; i < length; i++)
            {
                if (destination.isInterface() && (destination.isAssignableFrom(list.get(i).getClass())))
                {
                    result.add(destination.cast(list.get(i)));
                }
                else if (destination.isInstance(list.get(i)))
                {
                    result.add(destination.cast(list.get(i)));
                }
                else
                {
                    throw new ProcessusException(LixboxResources.getString("ERROR.PARAM.INCOMPATIBLE.01", "list"));
                }
            }  
        }
        return result;
    }
    
    
    
    /**
     * Cette methode convertit n'importe quel tableau en
     * une liste equivalente
     * 
     * @param array
     * 
     * @return une liste equivalente au tableau
     */
    public static <T> List<T> convertArrayToList(T[] array)
    {
        final List<T> liste = new ArrayList<>();
        Collections.addAll(liste, array);
        return liste;
    }
    
    
    
    /**
     * Cette methode sert a inverser le contenu d'une collection
     * 
     * @param listeEntree
     * 
     * @return listeEntreeInversee
     */
    public static <T> List<T> revertCollection(List<T> listeEntree)
    {
        Collections.reverse(listeEntree);
        return listeEntree;
    }
    
    
    
    /**
     * Cette methode trie la collection passee en parametre
     * par ordre croissant de la propriete identifiee
     * 
     * @param propriete
     * @param listeATrier
     *
     * @return liste triee
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> List<T> sortCollectionAsc(String propriete, List<T> listeATrier)
    {             
        List tmpList=null;
        try
        {            
            if (!CollectionUtil.isEmpty(listeATrier))
            {                   
                tmpList = new ArrayList<>();        
                final Map listTmp = new HashMap(32, 0.75f);
                for (T tmp : listeATrier)
                {
                    if (null!=propriete && (null != BeanUtil.getInstance().getProperty(tmp, propriete)))
                    {
                        listTmp.put(PropertyUtils.getProperty(tmp, propriete), tmp);
                    }
                    else
                    {
                        tmpList.add(tmp);
                    }
                }
                if (propriete!=null)
                {
                    final List listKey = CollectionUtil.convertAnyListToArrayList(listTmp.keySet());
                    Collections.sort(listKey);                
                    for (Object key : listKey)
                    {
                        tmpList.add(listTmp.get(key));
                    }
                }
                else
                {
                    Collections.sort((List<String>)tmpList);           
                }
                listeATrier.clear();
                listeATrier.addAll(tmpList);
            }
        }
        catch (Exception e)
        {
            LOG.trace(e);
        }        
        return listeATrier;
    }
    
    
    
    /**
     * Cette methode trie la collection passee en parametre
     * par ordre decroissant de la propriete identifiee
     * 
     * @param propriete
     * @param listeATrier
     *
     * @return liste triee
     */
    public static <T> List<T> sortCollectionDesc(String propriete, List<T> listeATrier)
    {  
        return revertCollection(sortCollectionAsc(propriete, listeATrier));
    }
    
    
    
    /**
     * Cette methode convertit une liste en hashmap dont la cle est la propriete
     * qui est indique en parametre
     * 
     * @param propriete
     * @param listeAConvertir
     * 
     * @return une hashmap image de la liste
     */
    public static Map<?,?> convertirListeToHashmap(String propriete, List<?> listeAConvertir)
    {
        Map<String,Object> result=new HashMap<>(32, 0.75f);    
        try
        {
            if (!CollectionUtil.isEmpty(listeAConvertir))
            {   
                for (Object tmp : listeAConvertir)
                {
                    if (null != BeanUtil.getInstance().getProperty(tmp, propriete))
                    {
                        result.put(BeanUtil.getInstance().getProperty(tmp, propriete), tmp);
                    }
                }
            }
        }
        catch (Exception e)
        {
            LOG.trace(e);            
        }
        return result;
    }
    
    
    
    
    /**
     * Cette methode retourne un boolean si la collectionA a un element de la 
     * collectionB 
     * 
     * @param colA
     * @param colB
     * 
     * @return un arrayList image du Set
     * 
     * @throws ProcessusException
     */
    public static boolean contientAuMoinsUn(List<?> colA, List<?> colB)
    {
        boolean retour = false;
    	if (isEmpty(colA))
        {
            throw new ProcessusException(LixboxResources.getString("ERROR.PARAM.INCORRECT.02", "colA")); //$NON-NLS-1$ //$NON-NLS-2$
        }
    	if (isEmpty(colB))
        {
            throw new ProcessusException(LixboxResources.getString("ERROR.PARAM.INCORRECT.02", "colB")); //$NON-NLS-1$ //$NON-NLS-2$
        }
	    for (Object tmpA : colA)
        {
    		for (Object tmpB : colB)
            {
    			if (tmpA.equals(tmpB))
    			{
    			    return true;
    			}
            }    		
        }
    	return retour;
    }
    
    
    
    /**
     * Cette methode soustrait la collection b a la collection a en faisant le comparatif 
     * des valeurs de la propriete passee en parametre
     * 
     * @param propriete
     * @param colA
     * @param colB
     *
     * @return liste soustraite
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> soustraireParPropriete(String propriete, List<T> colA, List<T> colB)
    {             
        List<T> result=null;
        try
        {
            if (!isEmpty(colA) && !isEmpty(colB))
            {   
                result = new ArrayList<>();        
                final Map<String,T> listTmp = (Map<String, T>) CollectionUtil.convertirListeToHashmap(propriete, colA);                
                for (T tmp : colB)
                {
                    if (null != BeanUtil.getInstance().getProperty(tmp, propriete))
                    {
                        listTmp.remove(BeanUtil.getInstance().getProperty(tmp, propriete));
                    }
                }
                final List<String> listKey = CollectionUtil.convertAnyListToArrayList(listTmp.keySet());
                for (Object key : listKey)
                {
                    result.add(listTmp.get(key));
                }
            }
            else
            {
                result = colA;
            }
        }
        catch (Exception e)
        {
            LOG.trace(e);
        }
        return result;
    }

    
    
    /**
     * Cette methode soustrait le ou les objets a la collection a en faisant le comparatif 
     * des valeurs de la propriete passee en parametre
     * 
     * @param propriete
     * @param colA
     * @param objB
     *
     * @return liste soustraite
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> soustraireParPropriete(String propriete, List<T> colA, T objB)
    {             
        List<T> result=null;
        try
        {
            if (!isEmpty(colA) && (null != objB))
            {   
                result = new ArrayList<>();        
                final Map<String,T> listTmp = (Map<String, T>) CollectionUtil.convertirListeToHashmap(propriete, colA);

                if (null != BeanUtil.getInstance().getProperty(objB, propriete))
                {
                    listTmp.remove(BeanUtil.getInstance().getProperty(objB, propriete));
                }

                final List<String> listKey = CollectionUtil.convertAnyListToArrayList(listTmp.keySet());
                for (Object key : listKey)
                {
                    result.add(listTmp.get(key));
                }
            }
        }
        catch (Exception e)
        {
            LOG.trace(e);
        }
        return result;
    }

    

    /**
     * Cette methode supprime un item de collection a partir
     * de la valeur de l'un de ses attributs
     * 
     * @param liste
     * @param propertyName
     * @param value
     * 
     * return une collection epuree
     */
    @SuppressWarnings("unchecked")
    public static List<?> removeByProperty(List<?> liste, 
            String propertyName, Object value)
    {
        try
        {
            final List<Object> listeCleaned; 
            if (org.hibernate.collection.internal.PersistentBag.class.equals(liste.getClass()))
            {
                listeCleaned = new ArrayList<>();    
            }
            else
            {
                listeCleaned = liste.getClass().getConstructor().newInstance();
            }
            if (!isEmpty(liste))
            {
                for (Object item : liste)
                {
                    if (!value.equals(
                            BeanUtil.getInstance()
                                .getPropertyUtils().getProperty(item, propertyName)))
                    {
                    	listeCleaned.add(item);
                    }               
                }
            }
            return listeCleaned;
        }
        catch (Exception e)
        {
            throw new ProcessusException(e);
        }
    }
    
    
    
    /**
     * Cette methode renvoie true si la collection contient un item ayant la valeur passee en parametre l'attribut
     * 
     * @param liste
     * @param propertyName
     * @param value
     * 
     * return un boolean
     */
    public static boolean containsObjectWithProperty(List<?> liste, String propertyName, Object value)
    {
        boolean result = false;
        try
        {            
            if (!isEmpty(liste))
            {
                for (Object item : liste)
                {
                    if (value.equals(
                            BeanUtil.getInstance()
                                .getPropertyUtils().getProperty(item, propertyName)))
                    {
                        result = true;
                    }               
                }
            }           
        }
        catch (Exception e)
        {
            LOG.fatal(e);
        }
        return result;
    }

    

    /**
     * Cette methode renvoie l'index d'un objet dans la collection en fonction de la valeur passee en 
     * parametre
     * 
     * @param liste
     * @param propertyName
     * @param value
     * 
     * return un boolean
     */
    public static Integer getIndexOfObjetByProperty(List<?> liste, String propertyName, Object value)
    {
        Integer result = null;
        try
        {            
            if (!isEmpty(liste))
            {
                for (int i=0; i<liste.size(); i++)
                {
                    if (value.equals(PropertyUtils.getProperty(liste.get(i), propertyName)))
                    {
                        result = i;
                    }               
                }
            }           
        }
        catch (Exception e)
        {
            LOG.fatal(e);
        }
        return result;
    }      
    
    

    /**
     * Cette methode retourne un boolean si la collectionA a un element de la 
     * collectionB 
     * 
     * @param colA
     * @param o
     * 
     * @return un arrayList image du Set
     * 
     * @throws ProcessusException
     */
    public static boolean existeDansLaCollection(List<?> colA, Object o)
    {
        boolean retour = false;
    	if (isEmpty(colA))
        {
            return false;
        }
    	if (null == o)
        {
            throw new ProcessusException(LixboxResources.getString("ERROR.PARAM.INCORRECT.02", "colB")); //$NON-NLS-1$ //$NON-NLS-2$
        }
        for (Object tmpA : colA)
        {
            if (tmpA.equals(o))
            {
                retour = true;
                break;
            }
        }    	
        return retour;
    }
    
    
    /**
     * Cette methode retourne un boolean si la collectionA a un element de la 
     * collectionB 
     * 
     * @param colA
     * @param o
     * 
     * @return un arrayList image du Set
     * 
     * @throws ProcessusException
     */
    public static boolean existeDansLeTableau(Object[]  colA, Object o)
    {
        boolean retour = false;
    	if (null == colA)
        {
            throw new ProcessusException(LixboxResources.getString("ERROR.PARAM.INCORRECT.02", "colA")); //$NON-NLS-1$ //$NON-NLS-2$
        }
    	if (0 == colA.length)
        {
            throw new ProcessusException(LixboxResources.getString("ERROR.PARAM.INCORRECT.02", "colA")); //$NON-NLS-1$ //$NON-NLS-2$
        }
    	if (null == o)
        {
            throw new ProcessusException(LixboxResources.getString("ERROR.PARAM.INCORRECT.02", "colB")); //$NON-NLS-1$ //$NON-NLS-2$
        }
        for (Object tmpA : colA)
        {
            if (tmpA.equals(o))
            {
                retour = true;
                break;
            }
        }       
        return retour;
    }
    
    
    
    /**
     * Cette methode extrait les objets qui sont dans les deux 
     * collection.
     * 
     * @param a
     * @param b
     * 
     * @return le differentiel
     */
    public static Collection<Object> intersection(Collection<?> a, Collection<?> b)
    {
        ArrayList<Object> list = new ArrayList<>();
        Map<?, Integer> mapa = getCardinalityMap(a);
        Map<?, Integer> mapb = getCardinalityMap(b);
        Set<Object> elts = new HashSet<>(a);
        elts.addAll(b);
        Iterator<Object> it = elts.iterator();
        while (it.hasNext())
        {
            Object obj = it.next();
            int i = 0;
            for (int m = Math.min(getFreq(obj, mapa), getFreq(obj, mapb)); i < m; ++i)
            {
                list.add(obj);
            }
        }
        return list;
    }
    
    


    public static Object find(Collection<?> collection, Predicate predicate)
    {
        if ((collection != null) && (predicate != null))
        {
            for (Object item : collection)
            {
                if (predicate.evaluate(item))
                {
                    return item;
                }
            }
        }
        return null;
    }



    public static Collection<?> retainAll(Collection<?> collection, Collection<?> retain)
    {
        return ListUtils.retainAll(collection, retain);
    }



    public static Collection<?> removeAll(Collection<?> collection, Collection<?> remove)
    {
        return ListUtils.retainAll(collection, remove);
    }
    
    
    
    private static <T> Map<T, Integer> getCardinalityMap(Collection<T> coll)
    {
        Map<T, Integer> count = new HashMap<>();
        for (T obj : coll)
        {
            Integer c = count.get(obj);
            if (c == null)
            {
                count.put(obj, 1);
            }
            else
            {
                count.put(obj, c+1);
            }
        }
        return count;       
    }
    
    
    
    private static final int getFreq(Object obj, Map<?,?> freqMap)
    {
        Integer count = (Integer) freqMap.get(obj);
        if (count != null)
        {
            return count.intValue();
        }
        return 0;
    }
}
