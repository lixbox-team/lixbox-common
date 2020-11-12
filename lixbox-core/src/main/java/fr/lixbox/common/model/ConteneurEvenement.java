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
package fr.lixbox.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.validation.ConstraintViolation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.lixbox.common.model.enumeration.NiveauEvenement;
import fr.lixbox.common.util.CollectionUtil;
import fr.lixbox.common.util.StringUtil;

/**
 * Cette classe est un conteneur d'erreurs liees aux evenements rencontres par
 * le systeme.
 * 
 * @author ludovic.terral
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConteneurEvenement implements Serializable
{
    // ----------- Attribut -----------
    protected static final Log LOG = LogFactory.getLog(ConteneurEvenement.class);        
    private static final long serialVersionUID = 5592240322757077471L;
    private static final String PARENT_CTX = "parent";
    private static final String ELM_CTX = "element";
    
    private Map<String, Evenement> evenements = new HashMap<>(32, 0.75f);

    

    // ----------- Methode -----------
    public static ConteneurEvenement valueOf(String json)
    {
        ConteneurEvenement result = null;
        TypeReference<ConteneurEvenement> typeRef = new TypeReference<ConteneurEvenement>() {};
        if (StringUtil.isNotEmpty(json)) {
            ObjectMapper mapper = new ObjectMapper();

            try {
                result = mapper.readValue(json, typeRef);
            } catch (Exception var5) {
                LOG.error(var5, var5);
            }
        }

        return result;
    }
    
    
    
    @JsonIgnore
    public long getSize()
    {
        long size = 0;
        if (evenements != null)
        {
            size = evenements.values().size();
        }
        return size;
    }

    

    public boolean isEmpty()
    {
        return getSize() == 0;
    }



    /**
     * Cette methode fusionne deux conteneurs d'evenements
     * 
     * @param conteneur
     */
    public void addAll(final ConteneurEvenement conteneur)
    {
        if (null != conteneur)
        {
            this.add(conteneur.getEvenements());
            this.setMapEvenements(new TreeMap<String, Evenement>(evenements));
        }
    }



    public void addAll(Set<ConstraintViolation<?>> constraintViolations)
    {
        if (!CollectionUtil.isEmpty(constraintViolations))
        {
            for (ConstraintViolation<?> violation : constraintViolations)
            {
                Evenement event = new Evenement(violation, new Contexte());
                this.evenements.put(event.toString(), event);
            }
        }
    }



    /**
     * Cette methode extrait une sous-liste contenant des evenements du type
     * fournis en parametre.
     * 
     * @param niveau de l'evenement
     * 
     * @return une sous-liste contenant les evenements correspondant au niveau
     */
    public List<Evenement> getEvenementByNiveauEvenement(final NiveauEvenement niveau)
    {
        final List<Evenement> result = new ArrayList<>();
        for (final Evenement element : evenements.values())
        {
            if (element.getNiveau().equals(niveau))
            {
                result.add(element);
            }
        }
        return result;
    }



    /**
     * Cette methode extrait une sous-liste contenant des evenements de type
     * erreur.
     * 
     * @return une sous-liste contenant les evenements correspondant au niveau
     */
    @JsonIgnore
    public List<Evenement> getEvenementTypeErreur()
    {
        return this.getEvenementByNiveauEvenement(NiveauEvenement.ERROR);
    }



    /**
     * Cette methode extrait une sous-liste contenant des evenements de type
     * warning.
     * 
     * @return une sous-liste contenant les evenements correspondant au niveau
     */
    @JsonIgnore
    public List<Evenement> getEvenementTypeWarning()
    {
        return this.getEvenementByNiveauEvenement(NiveauEvenement.WARN);
    }



    /**
     * Cette methode extrait une sous-liste contenant des evenements de type
     * info.
     * 
     * @return une sous-liste contenant les evenments correspondant au niveau
     */
    @JsonIgnore
    public List<Evenement> getEvenementTypeInfo()
    {
        return this.getEvenementByNiveauEvenement(NiveauEvenement.INFO);
    }



    /**
     * Cette methode recupere l'ensemble des evenements
     * 
     * @return une liste contenant les evenements
     */
    @JsonIgnore
    public List<Evenement> getEvenements()
    {
        return CollectionUtil.convertAnyListToArrayList(evenements.values());
    }



    /**
     * Cette methode recupere l'ensemble des evenements
     * 
     * @return une Map<String, Evenement> contenant les evenements
     */
    public Map<String, Evenement> getMapEvenements()
    {
        return evenements;
    }



    public void setMapEvenements(Map<String, Evenement> event)
    {
        this.evenements = event;
    }



    /**
     * Cette methode recupere le niveau d'evenements le plus eleves
     * 
     * @return le niveau le plus eleves
     */
    @JsonIgnore
    public NiveauEvenement getNiveauMax()
    {
        NiveauEvenement result = NiveauEvenement.INFO;
        for (Evenement event : evenements.values())
        {
            result = result.ordinal() > event.getNiveau().ordinal() ? result : event.getNiveau();
        }
        return result;
    }



    /**
     * Cette methode recupere l'ensemble des evenements
     * 
     * @return une liste contenant les evenements
     */
    @JsonIgnore
    public List<String> getStringEvenements()
    {
        final List<String> liste = new ArrayList<>();
        for (final String string : evenements.keySet())
        {
            liste.add(string);
        }
        return liste;
    }



    /**
     * Cette methode ajoute un evenement dans la liste
     * 
     * @param event
     */
    public void add(final Evenement event)
    {
        if (event != null)
        {
            evenements.put(event.toString(), event);
        }
    }



    /**
     * Cette methode ajoute des evenements dans la liste
     * 
     * @param listeEvent
     */
    public void add(final List<Evenement> listeEvent)
    {
        if (!CollectionUtil.isEmpty(listeEvent))
        {
            for (final Evenement event : listeEvent)
            {
                evenements.put(event.toString(), event);
            }
        }
    }



    /**
     * Cette methode ajoute un evenement dans la liste
     * 
     * @param niveau
     * @param libelle
     * @param contexte
     */
    public void add(final NiveauEvenement niveau, final String libelle, final Contexte contexte)
    {
        final Contexte contexte1 = new Contexte(contexte);
        if (!StringUtil.isEmpty(contexte1.get(PARENT_CTX)))
        {
            contexte1.put(PARENT_CTX, contexte1.get(PARENT_CTX)
                    .substring(0, contexte1.get(PARENT_CTX).length() - 1));
            contexte1.put(ELM_CTX, "");
        }
        evenements.put(new Evenement(niveau, libelle, contexte1).toString(),
                new Evenement(niveau, libelle, contexte1));
    }



    /**
     * Cette methode ajoute un evenement dans la liste
     * 
     * @param niveau
     * @param libelle
     * @param dateEvent
     * @param contexte
     */
    public void add(final NiveauEvenement niveau, final String libelle, final Calendar dateEvent,
            final Contexte contexte)
    {
        final Contexte contexte1 = new Contexte(contexte);
        if (!StringUtil.isEmpty(contexte1.get(PARENT_CTX)))
        {
            contexte1.put(PARENT_CTX, contexte1.get(PARENT_CTX)
                    .substring(0, contexte1.get(PARENT_CTX).length() - 1));
            contexte1.put(ELM_CTX, "");
        }
        evenements.put(new Evenement(niveau, libelle, dateEvent, contexte1).toString(),
                new Evenement(niveau, libelle, dateEvent, contexte1));
    }



    /**
     * Cette methode ajoute un evenement dans la liste
     * 
     * @param niveau
     * @param libelle
     * @param referenceName
     *            nom de la reference contextuelle
     * @param referenceValue
     *            valeur de la reference contextuelle
     */
    public void add(final NiveauEvenement niveau, final String libelle, final String referenceName,
            final String referenceValue)
    {
        final Contexte contexte = new Contexte();
        contexte.put(referenceName, referenceValue);
        evenements.put(new Evenement(niveau, libelle, contexte).toString(),
                new Evenement(niveau, libelle, contexte));
    }



    /**
     * Cette methode ajoute un evenement dans la liste
     * 
     * @param niveau
     * @param libelle
     * @param dateEvent
     * @param referenceName
     *            nom de la reference contextuelle
     * @param referenceValue
     *            valeur de la reference contextuelle
     */
    public void add(final NiveauEvenement niveau, final String libelle, final Calendar dateEvent,
            final String referenceName, final String referenceValue)
    {
        final Contexte contexte = new Contexte();
        contexte.put(referenceName, referenceValue);
        evenements.put(new Evenement(niveau, libelle, dateEvent, contexte).toString(),
                new Evenement(niveau, libelle, dateEvent, contexte));
    }



    /**
     * Cette methode ajoute un evenement dans la liste
     * 
     * @param niveau
     * @param libelle
     * @param dateEvent
     * @param contexte
     *            contexte de l'evenement
     * @param elementValue
     *            nom de l'element en erreur
     */
    public void add(final NiveauEvenement niveau, final String libelle, final Calendar dateEvent,
            final Contexte contexte, final String elementValue)
    {
        final Contexte contexte1 = new Contexte(contexte);
        contexte1.put(ELM_CTX, elementValue);
        evenements.put(new Evenement(niveau, libelle, dateEvent, contexte1).toString(),
                new Evenement(niveau, libelle, dateEvent, contexte1));
    }



    /**
     * Cette methode ajoute un evenement dans la liste
     * 
     * @param contextElmtValue
     * 
     * @return un evenement dont le contexte contient l'element recherche
     */
    @JsonIgnore
    public Evenement getEvenementByContextElement(final String contextElmtValue)
    {
        Evenement result = null;
        for (final Evenement event : evenements.values())
        {
            if (event.getContexte().getContenu().containsValue(contextElmtValue))
            {
                result = event;
            }
        }
        return result;
    }



    /**
     * Cette methode recupere des evenements en fct d'un nom et d'une valeur
     * 
     * @param contextElmtName
     * @param contextElmtValue
     * 
     * @return une liste d'evenements
     */
    @JsonIgnore
    public List<Evenement> getEvenementsByContextElement(final String contextElmtName,
            final String contextElmtValue)
    {
        final List<Evenement> result = new ArrayList<>();
        for (final Evenement event : evenements.values())
        {
            if ((null != contextElmtValue) && (null != event.getContexte()) && contextElmtValue
                    .equals(event.getContexte().get(contextElmtName)))
            {
                result.add(event);
            }
        }
        return result;
    }



    /**
     * Cette methode recupere des evenements en fct d'un nom et d'une valeur
     * 
     * @param contextElmtName
     * @param contextElmtValue
     * @param criticite
     * 
     * @return une liste d'evenements
     */
    @JsonIgnore
    public List<Evenement> getEvenementsByContextElement(final String contextElmtName,
            final String contextElmtValue, final NiveauEvenement criticite)
    {
        final List<Evenement> result = new ArrayList<>();
        for (final Evenement event : evenements.values())
        {
            if ((null != contextElmtValue) && (null != event.getContexte())
                    && contextElmtValue
                            .equals(event.getContexte().get(contextElmtName))
                    && criticite.equals(event.getNiveau()))
            {
                result.add(event);
            }
        }
        return result;
    }



    @Override
    public String toString()
    {
        String result = "Content error";
        ObjectMapper mapper = new ObjectMapper();
        try {
            result = mapper.writeValueAsString(this);
        } 
        catch (JsonProcessingException e) 
        {
            LOG.error(e,e);
        }
        return result;
    }
}