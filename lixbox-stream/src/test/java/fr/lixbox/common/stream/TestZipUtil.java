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
package fr.lixbox.common.stream;



import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Test;

import fr.lixbox.common.stream.util.StreamStringUtil;
import fr.lixbox.common.stream.util.ZipUtil;
import fr.lixbox.common.util.StringUtil;

/**
 * Cette classe permet de valider l'utilitaire 
 * Zip Util
 * 
 * @author ludovic.terral
 */
public class TestZipUtil 
{
    // ----------- Attribut -----------
        
    
    
    
    // ----------- Methode -----------
    @After
    public void after() throws IOException
    {
        try
        {
            FileUtils.forceDelete(new File("./zip.zip"));
        }
        catch(Exception e)
        {
            //pas present?
        }
        try
        {
            FileUtils.forceDelete(new File("./temp"));
        }
        catch(Exception e)
        {
            //pas present?
        }
    }

    

    /**
     * Ce test verifie la methode afficherDetail
     * du ZipUtil.
     */
    @Test
    public void testZipUtil_afficherDetail()
    {
        System.out.println("--------------------------------");
        System.out.println("Debut testZipUtil_afficherDetail");
        System.out.println("--------------------------------\n");
        final long debut = Calendar.getInstance().getTimeInMillis();
        
        
        //Preparation du test
        final ZipUtil zipUtil = new ZipUtil("./zip.zip");
        
        
        //Execution du test 
        final String result = zipUtil.afficherDetail();
        
        

        //Verification des resultats
        if (!StringUtil.isEmpty(result))
        {
            System.out.println("==== Resultat testZipUtil_afficherDetail: Succes");
        }
        else
        {
            System.out.println("==== Resultat testZipUtil_afficherDetail: Echec");
            assertTrue("contenu nul", null != result);
        } 

        
        final long duree = Calendar.getInstance().getTimeInMillis() - debut;
        System.out.println("duree:" + duree + " ms");  
        System.out.println("------------------------------");
        System.out.println("Fin testZipUtil_afficherDetail");
        System.out.println("------------------------------\n");    
    }

    

    /**
     * Ce test verifie la methode ajouterUnOuDesFichiers
     * du ZipUtil.
     * @throws IOException 
     */
    @Test
    public void testZipUtil_ajouterUnOuDesFichiers() throws IOException
    {
        System.out.println("-----------------------------------------");
        System.out.println("Debut testZipUtil_ajouterUnOuDesFichiers");
        System.out.println("-----------------------------------------\n");
        final long debut = Calendar.getInstance().getTimeInMillis();
        
        
        //Preparation du test
        final ZipUtil zipUtil = new ZipUtil("./zip.zip");
        
        
        //Execution du test 
        final boolean result = zipUtil.ajouterUnOuDesFichiers(new File(ZipUtil.class.getResource("/fr/lixbox/common/stream/README.txt").getPath().replaceAll("file:", "")));
        
        

        //Verification des resultats
        if (result)
        {
            System.out.println("==== Resultat testZipUtil_ajouterUnOuDesFichiers: Succes");
            FileUtils.forceDelete(new File("./zip.zip"));
        }
        else
        {
            System.out.println("==== Resultat testZipUtil_ajouterUnOuDesFichiers: Echec");
            assertTrue("Echec de l'ajout", result);
        } 

        
        final long duree = Calendar.getInstance().getTimeInMillis() - debut;
        System.out.println("duree:" + duree + " ms");  
        System.out.println("--------------------------------------");
        System.out.println("Fin testZipUtil_ajouterUnOuDesFichiers");
        System.out.println("--------------------------------------\n");   
    }
    

    
    /**
     * Ce test verifie la methode extraireLesFichiers
     * du ZipUtil.
     * @throws URISyntaxException 
     */
    @Test
    public void testZipUtil_extraireLesFichiers() throws URISyntaxException
    {
        System.out.println("-----------------------------------------");
        System.out.println("Debut testZipUtil_extraireLesFichiers");
        System.out.println("-----------------------------------------\n");
        final long debut = Calendar.getInstance().getTimeInMillis();
        
        
        //Preparation du test
        final ZipUtil zipUtil = new ZipUtil(ZipUtil.class.getResource("/birt.war.zip").getPath().replaceAll("file:",""));
        System.out.println(new File("./temp").mkdir());
        
        
        //Execution du test        
        final boolean result = zipUtil.extraireLesFichiers(new File("./temp"));
        

        //Verification des resultats
        if (result)
        {
            System.out.println("==== Resultat testZipUtil_extraireLesFichiers: Succes");
        }
        else
        {
            System.out.println("==== Resultat testZipUtil_extraireLesFichiers: Echec");
            assertTrue("Echec de l'extraction", result);
        } 

        
        final long duree = Calendar.getInstance().getTimeInMillis() - debut;
        System.out.println("duree:" + duree + " ms");  
        System.out.println("--------------------------------------");
        System.out.println("Fin testZipUtil_extraireLesFichiers");
        System.out.println("--------------------------------------\n");
    }


    /**
     * Ce test verifie la methode listerLesFichiers
     * du ZipUtil.
     */
    @Test
    public void testZipUtil_listerLesFichiers()
    {
        System.out.println("--------------------------------");
        System.out.println("Debut testZipUtil_listerLesFichiers");
        System.out.println("--------------------------------\n");
        final long debut = Calendar.getInstance().getTimeInMillis();
        
        
        //Preparation du test
        final ZipUtil zipUtil = new ZipUtil(ZipUtil.class.getResource("/birt.war.zip").getPath().replaceAll("file:",""));
        
        
        //Execution du test 
        final String result = zipUtil.listerLesFichiers().get(0);
        
        

        //Verification des resultats
        if (!StringUtil.isEmpty(result))
        {
            System.out.println("==== Resultat testZipUtil_listerLesFichiers: Succes");
        }
        else
        {
            System.out.println("==== Resultat testZipUtil_listerLesFichiers: Echec");
            assertTrue("contenu nul", null != result);
        } 

        
        final long duree = Calendar.getInstance().getTimeInMillis() - debut;
        System.out.println("duree:" + duree + " ms");  
        System.out.println("------------------------------");
        System.out.println("Fin testZipUtil_afficherDetail");
        System.out.println("------------------------------\n");    
    }
    
    
    
    /**
     * Ce test verifie la methode ajouterUnFichierTexte
     * du ZipUtil.
     * @throws IOException 
     */
    @Test
    public void testZipUtil_ajouterUnFichierTexte() throws IOException
    {
        System.out.println("-----------------------------------------");
        System.out.println("Debut testZipUtil_ajouterUnFichierTexte");
        System.out.println("-----------------------------------------\n");
        final long debut = Calendar.getInstance().getTimeInMillis();
        
        
        //Preparation du test
        final ZipUtil zipUtil = new ZipUtil("./zip.zip");
        
        
        //Execution du test 
        String dataTest = StreamStringUtil.read(TestZipUtil.class.getResourceAsStream("/fr/lixbox/common/stream/README.txt"));
        final boolean result = zipUtil.ajouterFichierTexte("test/test.txt",dataTest, StandardCharsets.ISO_8859_1);
                

        //Verification des resultats
        if (result)
        {
            System.out.println("==== Resultat testZipUtil_ajouterUnFichierTexte: Succes");
            FileUtils.forceDelete(new File("./zip.zip"));
        }
        else
        {
            System.out.println("==== Resultat testZipUtil_ajouterUnFichierTexte: Echec");
            assertTrue("Echec de l'ajout", result);
            FileUtils.forceDelete(new File("./zip.zip"));
        } 

        
        final long duree = Calendar.getInstance().getTimeInMillis() - debut;
        System.out.println("duree:" + duree + " ms");  
        System.out.println("--------------------------------------");
        System.out.println("Fin testZipUtil_ajouterUnFichierTexte");
        System.out.println("--------------------------------------\n");   
    }

        
    
    /**
     * Ce test verifie la methode ajouterUnFichier
     * du ZipUtil.
     * @throws IOException 
     */
    @Test
    public void testZipUtil_ajouterUnFichier() throws IOException
    {
        System.out.println("-----------------------------------------");
        System.out.println("Debut testZipUtil_ajouterUnFichier");
        System.out.println("-----------------------------------------\n");
        final long debut = Calendar.getInstance().getTimeInMillis();
        
        
        //Preparation du test
        final ZipUtil zipUtil = new ZipUtil("./zip.zip");
        
        
        //Execution du test 
        byte[] dataTest = new byte[]{'1','2','3','4'};        
        final boolean result = zipUtil.ajouterFichier("test/testByte.txt",dataTest);
                

        //Verification des resultats
        if (result)
        {
            System.out.println("==== Resultat testZipUtil_ajouterUnFichierTexte: Succes");
            FileUtils.forceDelete(new File("./zip.zip"));
        }
        else
        {
            System.out.println("==== Resultat testZipUtil_ajouterUnFichierTexte: Echec");
            assertTrue("Echec de l'ajout", result);
            FileUtils.forceDelete(new File("./zip.zip"));
        } 

        
        final long duree = Calendar.getInstance().getTimeInMillis() - debut;
        System.out.println("duree:" + duree + " ms");  
        System.out.println("--------------------------------------");
        System.out.println("Fin testZipUtil_ajouterUnFichierTexte");
        System.out.println("--------------------------------------\n");   
    }
}
