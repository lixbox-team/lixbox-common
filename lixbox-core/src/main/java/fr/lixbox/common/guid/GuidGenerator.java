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
package fr.lixbox.common.guid;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Calendar;

import fr.lixbox.common.exceptions.ProcessusException;
import fr.lixbox.common.resource.LixboxResources;
import fr.lixbox.common.util.DateUtil;

/**
 * Cette classe est un generateur de cles techniques. 
 *  
 * @author ludovic.terral
 */
public class GuidGenerator implements Serializable
{   
    // ----------- Attribut -----------    
    private static final long serialVersionUID = 31407064505408450L;
    private String hexServerIP = null;
    private final SecureRandom seeder = new SecureRandom();


    
    // ----------- Methode -----------  
    /**
     * Constructeur par defaut (prive, cf. factory pattern)
     */
    private GuidGenerator()
    {
        java.net.InetAddress localInetAddress = null;
        try
        {
            localInetAddress = java.net.InetAddress.getLocalHost();
        }
        catch (java.net.UnknownHostException uhe)
        {
            throw new ProcessusException(LixboxResources.getString("ERROR.HOST.INACCESSIBLE"), uhe);
        }
        final byte[] serverIP = localInetAddress.getAddress();
        this.hexServerIP = hexFormat(getInt(serverIP), 8);
    }

    
    
    /**
     * Cette methode retourne une cle technique unique
     * pour l'objet fourni.
     * 
     * @param object : objet origine 
     * 
     * @return un oid
     * 
     * @throws ProcessusException
     */
    public static String getGUID(final Object object)
    {
        return new GuidGenerator().generateGUID(object);
    }

    
    
    /**
     * Cette methode retourne une cle technique 
     * unique de type:
     * <prefixe>-ddMMYYYY-hhmm-<random>
     * 
     * @param prefixe : lettre prefixe en fonction de l'objet
     * 
     * @return un oid
     * 
     * @throws ProcessusException
     */
    public static String getNumeroRef(final String prefixe)
    {
        return new GuidGenerator().generateReference(prefixe);
    }
    
    
    
    /**
     * Cette methode cree une cle technique unique
     * pour l'objet fourni.
     * 
     * @param object : objet origine 
     * 
     * @return un oid
     * 
     * @throws ProcessusException
     */
    private String generateGUID(final Object object)
    {
        final var tmpBuffer = new StringBuilder(16);
        final String hashcode = hexFormat(System.identityHashCode(object), 8);
        tmpBuffer.append(this.hexServerIP);
        tmpBuffer.append(hashcode);
           
        final var node = this.seeder.nextInt();

        final var guid = new StringBuilder(32);
        guid.append(tmpBuffer.toString());
        guid.append(hexFormat(node, 8));

        return guid.toString().toUpperCase();
    }

    
    
    /**
     * Cette methode cree une cle technique
     * unique de type:
     * <prefixe>-ddMMYYYY-HHmm-<random>
     * 
     * @param prefixe : lettre prefixe en fonction de l'objet
     * 
     * @return un oid
     * 
     * @throws ProcessusException
     */
    private String generateReference(final String prefixe) 
    {
        final var nRef = new StringBuilder(32);
        nRef.append(prefixe);
        nRef.append('-');
        nRef.append(DateUtil.format(Calendar.getInstance(), "ddMMyyyy"));
        nRef.append('-');
        nRef.append(DateUtil.format(Calendar.getInstance(), "HHmm"));
        nRef.append('-');
        nRef.append(getRandomCode());

        return nRef.toString();
    }
    
    
    
    /**
     * Cette methode de calcul renvoie une 
     * chaine contenant une sequence de caracteres
     * aleatoires. 
     * 
     * @return une chaine aleatoire
     */
    private String getRandomCode()
    {
        final var number = this.seeder.nextInt(1679616);
        var texte = Integer.toString(number, 36);
        final int delta = 4 - texte.length();
        var sb = new StringBuilder();
        for (var i = 0; i < delta; i++) 
        {
            sb.append('0');
            sb.append(texte);
            texte = sb.toString();
        }
        return texte;
    }
    
    
    
    /**
     * Cette methode de calcul renvoie une 
     * valeur numerique a partir d'un sequence de
     * bytes. 
     * 
     * @param bytes
     * 
     * @return une valeur numerique
     */
    private static int getInt(final byte[] bytes)
    {
        var index1 = 0;
        var index2 = 24;
        var index3 = 0;
        
        while (index2 >= 0)
        {
            final int index4 = bytes[index3] & 0xff;
            index1 += (index4 << index2);
            index2 -= 8;
            index3++;
        }
        return index1;
    }

    
    
    /**
     * Cette methode convertit un nombre
     * en une valeur hexadecimale et de rendre cette
     * valeur avec un pad definis. 
     * 
     * @param valeur
     * @param pad
     * 
     * @return un hexa
     */
    private static String hexFormat(final int valeur, final int pad)
    {
        final var result = Integer.toHexString(valeur);

        return padHex(result, pad) + result;
    }


    
    
    /**
     * Cette methode convertit une chaine image d'un nombre
     * en une valeur hexadecimale et de rendre cette
     * valeur avec un pad definis. 
     * 
     * @param valeur
     * @param pad
     * 
     * @return un hexa
     */
    private static String padHex(final String valeur, final int pad)
    {
        final var tmpBuffer = new StringBuilder(32);
        final int lengthValeur = valeur.length();
        if (lengthValeur < pad)
        {
            for (var j = 0; j < (pad - lengthValeur); j++)
            {
                tmpBuffer.append('0');
            }
        }
        return tmpBuffer.toString();
    }
}
