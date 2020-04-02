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
package fr.lixbox.common.thread;

import java.io.Serializable;
import java.lang.reflect.Method;

import fr.lixbox.common.exceptions.BusinessException;

/**
 * Cette classe est un thread generique capable d'executer
 * n'importe quelle methode publique d'une instance d'objet
 * passe en parametre lors de l'instanciation.
 *  
 * @author ludovic.terral
 */
public class GenericThread<T> extends Thread implements Serializable, Runnable
{
    // ----------- Attribut -----------
    private static final long serialVersionUID = 120080923085200L; 
    private transient T result=null;
    private transient Object instance=null;
    private Class<?>[] typeParams=null;
    private transient Object[] params=null;
    private String nomMethode=null;
    private boolean isLaunched=false;
   
    
    
    // ----------- Methode -----------  
    public GenericThread(final Object instance, final String nomMethode, 
            final Class<?>[] typeParams, final Object[] params)
    {
        this.instance = instance;
        this.typeParams = typeParams.clone();
        this.params = params.clone();
        this.nomMethode = nomMethode;
    }
    

    
    @SuppressWarnings("unchecked")
    public void run()
    {
        try
        {
        	isLaunched = true;
            final Method method = instance.getClass().getMethod(nomMethode, typeParams);
            if (null != method)
            {
                result = (T) method.invoke(instance, params);
            }
        }
        catch(Exception e)
        {
            if (BusinessException.class.isInstance(e.getCause()))
            {
                final BusinessException be = (BusinessException) e.getCause();
                result = (T) be.getConteneur();
            }
        }
    }
    
    
    
    public T getResultatThread()
    {
        return result;
    } 
    
    
    
    public boolean isLaunched()
    {
    	return isLaunched;
    }
}
