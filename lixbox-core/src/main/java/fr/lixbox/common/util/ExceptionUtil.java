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

import java.util.Calendar;

import javax.ejb.EJBTransactionRolledbackException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.lixbox.common.exceptions.BusinessException;
import fr.lixbox.common.exceptions.CriticalBusinessException;
import fr.lixbox.common.exceptions.ProcessusException;
import fr.lixbox.common.model.ConteneurEvenement;
import fr.lixbox.common.model.Contexte;
import fr.lixbox.common.model.enumeration.NiveauEvenement;
import fr.lixbox.common.resource.LixboxResources;

/**
 * Cette classe gere le flot d'exception et renvoie l'exception
 * adequate.
 * 
 * @author ludovic.terral
 */
public class ExceptionUtil extends ExceptionUtils
{
    // ----------- Attribut -----------
    private static final Log LOG = LogFactory.getLog(ExceptionUtil.class);
    
    
    
    // ----------- Methode -----------
    /**
     * Cette methode traite les exceptions que peuvent rencontrer
     * les methodes du code Metier et lance soit une business soit une critical
     * soit une processus Exception
     * 
     * @param e
     * @param racineClasse
     * @param throwException
     * 
     * @throws BusinessException
     */
    public static void traiterException(final Exception e, final String racineClasse, boolean throwException)
        throws BusinessException
    {
        Exception result = null;
        
        //Traitement des exceptions de l'application
        if (e instanceof javax.validation.ConstraintViolationException)
        {
            LOG.error(getRootCauseMessage(e));
            ConteneurEvenement conteneurEvenement = new ConteneurEvenement();
            conteneurEvenement.addAll(((javax.validation.ConstraintViolationException)e).getConstraintViolations());
            result = new BusinessException(LixboxResources.getString("MSG.ERROR.EXCEPUTI_03", racineClasse),conteneurEvenement);
        }
        if (e instanceof org.hibernate.exception.ConstraintViolationException)
        {
            LOG.error(getRootCauseMessage(e));
            Contexte contexte = new Contexte();
            contexte.put("constraintViolationExceptionName", ((org.hibernate.exception.ConstraintViolationException)e).getConstraintName());
            ConteneurEvenement conteneurEvenement = new ConteneurEvenement();
            conteneurEvenement.add(NiveauEvenement.ERROR, ((org.hibernate.exception.ConstraintViolationException)e).getConstraintName(), 
            		Calendar.getInstance(), contexte);
            result = new BusinessException(LixboxResources.getString("MSG.ERROR.EXCEPUTI_03", racineClasse),conteneurEvenement);
        }
        if (e instanceof BusinessException)
        {
            if (!StringUtils.contains(getRootCauseMessage(e), "_01") &&
                    !StringUtils.contains(getRootCauseMessage(e), "_02"))
            {
                LOG.info(getRootCauseMessage(e));
            }
            result = e;
        }
        if (e instanceof CriticalBusinessException)
        {
            LOG.error(getRootCauseMessage(e));
            result = e;
        }
        if (e instanceof ProcessusException)
        {
            LOG.fatal(getRootCauseMessage(e));
            result = e;
        }
                
        
        //Traitement des exceptions aleatoires
        if (e instanceof EntityNotFoundException ||
            e instanceof NoResultException)            
        {
            result = new BusinessException(LixboxResources.getString("MSG.ERROR.EXCEPUTI_01", racineClasse));
        }
        
        if (e instanceof EJBTransactionRolledbackException && ((EJBTransactionRolledbackException) e).getCausedByException() instanceof EntityNotFoundException)            
        {
            result = new BusinessException(LixboxResources.getString("MSG.ERROR.EXCEPUTI_01", racineClasse));
        }
        
        
        //Toute exception inconnue est transformee en ProcessusException        
        if (null == result)
        {
            LOG.fatal(getRootCauseMessage(e));
            result = new ProcessusException(e);
        }         
        // throw une exception typee   
        if (throwException)
        {
            if (result instanceof BusinessException)
            {
                throw (BusinessException) result;
            }
            if (result instanceof CriticalBusinessException)
            {
                throw (CriticalBusinessException) result;
            }
            if (result instanceof ProcessusException)
            {
                throw (ProcessusException) result;
            }
        }
    }
}
