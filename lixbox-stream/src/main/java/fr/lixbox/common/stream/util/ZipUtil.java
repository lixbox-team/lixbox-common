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
package fr.lixbox.common.stream.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.lixbox.common.util.StringUtil;

/**
 * Cette classe fournit un utilitaire servant a creer, modifier une archive ZIP.
 * 
 * @author ludovic.terral
 */
public class ZipUtil
{
    private static final String PATH_SEPARATOR = "/";
    // ----------- Attribut -----------
    protected static final Log LOG = LogFactory.getLog(ZipUtil.class);    
    protected static final int BUFFER = 2048;
    private static final String CHECKSUM = "checksum: ";
    private static final String EXTRACTION_PATH = "/extraction";

    private File fichier = null;   
    private FileOutputStream dest;
    private CheckedOutputStream checksum;
    private BufferedOutputStream buff;
    private ZipOutputStream out;



    // ----------- Methode -----------  
    public ZipUtil(String cheminArchiveZip)
    {
        fichier = new File(cheminArchiveZip);
    }



    public ZipUtil(File archiveZip)
    {
        fichier = archiveZip;
    }



    /**
     * Cette methode affiche les informations de 
     * l'archive   
     */
    public String afficherDetail()
    {
        StringBuilder sbf = new StringBuilder("Archive ");
                
        try(ZipFile archive = new ZipFile(fichier);)
        {   
            String method = null;            
            sbf = new StringBuilder("Archive " + archive.getName() + System.getProperty("line.separator"));
            final Enumeration<?> entries = archive.entries();
            final DateFormat df = new SimpleDateFormat("yyyy/mm/dd_hh:mm:ss");
            
            
            // parcours des entrees
            while (entries.hasMoreElements())
            {
                ZipEntry e = ((ZipEntry) entries.nextElement());
                Date d = new Date(e.getTime());
                if (e.getMethod() == ZipEntry.DEFLATED)
                {
                    method = "DEFLATED";
                }
                sbf.append(e.getName() + ", ");
                sbf.append(e.getComment() + ", ");
                sbf.append(e.getCompressedSize() + " octets, ");
                sbf.append("CRC:" + e.getCrc() + ", ");
                sbf.append(String.valueOf(e.getExtra()) + ", "); // $codepro.audit.disable improperConversionOfArrayToString
                sbf.append(method + ", ");
                sbf.append(e.getSize() + " octets, ");
                sbf.append(df.format(d) + ", ");
                sbf.append(e.hashCode() + ", ");
                if (!e.isDirectory())
                {
                    sbf.append(("ratio: " + (((e.getSize() - e.getCompressedSize()) * 100) / e.getSize())) + "%");
                }
            }                      
        }
        catch (IOException e)
        {
            LOG.fatal(e);
        }
        return sbf.toString();
    }



    /**
     * Cette methode ajoute un ou plusieurs fichers dans
     * l'archive   
     * 
     * @param fichiersOuRepertoire
     * 
     * @return true si integration ok
     *         false si echec
     */
    public boolean ajouterUnOuDesFichiers(File fichiersOuRepertoire)
    {
        boolean result;

        try
        {   
            result = preparerArchive();
            
            //ajout des nouveaux fichiers
            try
            {
                ajouterFichier(out, fichiersOuRepertoire, "");
            }
            catch (Exception e)
            {
                LOG.fatal(e);
                result=false;
            }
            
            
            out.close();
            buff.close();
            checksum.close();
            dest.close();
            LOG.debug(CHECKSUM + checksum.getChecksum().getValue());
            result = true;
        }
        catch (Exception e)
        {
            LOG.fatal(e);
            result=false;
        }
        return result;
    }

    
    
    /**
     * Cette methode ajoute un fichier de type texte dans 
     * l'archive ZIP
     * 
     * @param nomFichier
     * @param data
     * @param charset
     * 
     * @return true si l'integration est ok
     *         false dans les autres cas
     */
    public boolean ajouterFichierTexte(String nomFichier, String data, Charset charset)
    {
        boolean result;
        try
        {          
            result = preparerArchive();
            
            //ajouter le fichier texte
            try
            {                
                ZipEntry entry = new ZipEntry(nomFichier);
                out.putNextEntry(entry);
                StreamStringUtil.write(data, out, charset);
            }
            catch (Exception e)
            {
                LOG.fatal(e);
                result=false;
            }
            
            out.close();
            buff.close();
            checksum.close();
            dest.close();
            LOG.debug(CHECKSUM + checksum.getChecksum().getValue());
            result = true;
        }
        catch (Exception e)
        {
            LOG.fatal(e);
            result=false;
        }
        return result;  
    }
    
    
    
    /**
     * Cette methode ajoute un fichier dans l'archive ZIP
     * 
     * @param nomFichier
     * @param data
     * 
     * @return true si l'integration est ok
     *         false dans les autres cas
     */
    public boolean ajouterFichier(String nomFichier, byte[] data)
    {
        boolean result;
        try
        {  
            result = preparerArchive();
            
            //ajouter le fichier texte
            try
            {                
                ZipEntry entry = new ZipEntry(nomFichier);
                out.putNextEntry(entry);
                out.write(data);
                out.closeEntry();
            }
            catch (Exception e)
            {
                LOG.fatal(e);
                result=false;
            }
            
            out.close();
            buff.close();
            checksum.close();
            dest.close();
            LOG.debug(CHECKSUM + checksum.getChecksum().getValue());
            result = true;
        }
        catch (Exception e)
        {
            LOG.fatal(e);
            result=false;
        }
        return result;  
    }
    
    

    /**
     * Cette methode extrait un ficher de l'archive
     * 
     * @param destination
     * 
     * @return true si l'extraction est ok
     *         flase si echec
     */
    public boolean extraireLesFichiers(File destination)
    {
        boolean result = true;
        
        try
        (                
            FileInputStream fis = new FileInputStream(fichier);         
            BufferedInputStream buffi= new BufferedInputStream(fis);
            ZipInputStream zis= new ZipInputStream(buffi);        
        )
        {
            //creation de la destination
            if (!destination.exists())
            {
                result &= destination.mkdirs();
            }
            
            
            //extraction
            ZipEntry entry = null;            
            
            // parcours des entrees de l'archive
            while (null != (entry = zis.getNextEntry()))
            {
                LOG.debug("Extracting: " + entry);
                int count=0;
                byte[] data = new byte[BUFFER];
                
                //creation de l'arborescence si necessaire
                String chemin = StringUtils.left(entry.getName(), entry.getName().lastIndexOf('/'));
                if (!StringUtil.isEmpty(chemin) && !new File(destination.getAbsolutePath() + PATH_SEPARATOR + chemin).exists())
                {
                    result &= new File(destination.getAbsolutePath() + PATH_SEPARATOR + chemin).mkdirs();
                }
                
                //ecriture des fichiers
                if (!entry.getName().endsWith(PATH_SEPARATOR))
                {
                    try (
                        FileOutputStream fos = new FileOutputStream(destination.getAbsolutePath() + PATH_SEPARATOR + entry.getName());
                        BufferedOutputStream desti = new BufferedOutputStream(fos, BUFFER);
                    )
                    {                    
                        while ((count = zis.read(data, 0, BUFFER)) != -1)
                        {
                            desti.write(data, 0, count);
                        }
                    }
                }
            }
        }
        catch (IOException e)
        {            
            LOG.fatal(e);
            result=false;
        }
        return result;
    }
    
    
    
    /**
     * Cette methode renvoie la liste des fichiers contenus
     * dans l'archive
     * 
     * @return la liste des fichiers de l'archive
     */
    public List<String> listerLesFichiers()
    {
        final List<String> result = new ArrayList<>();
        try (            
                final FileInputStream fis = new FileInputStream(fichier);            
                final BufferedInputStream buffi = new BufferedInputStream(fis);
                final ZipInputStream zis = new ZipInputStream(buffi);)
        {            
            //extraction
            ZipEntry entry = null;
            
            
            // parcours des entrees de l'archive
            while (null != (entry = zis.getNextEntry()))
            {
                result.add(entry.getName());                
            }
        }
        catch (IOException e)
        {            
            LOG.fatal(e);
        }
        return result;
    }
    
        
    
    /**
     * Cette methode prepare une nouvelle archive. Elle recupere les fichiers existants
     * 
     * @return true si ok et false dans les autres cas 
     * @throws IOException 
     */
    private boolean preparerArchive() throws IOException
    {   
        //initialisation du repertoire
        String chemin = StringUtils.left(fichier.getAbsolutePath(), fichier.getAbsolutePath().lastIndexOf('\\'));
        File repTemp = new File(chemin);
        if (!repTemp.exists())
        {
            LOG.trace(repTemp.mkdirs());
        }
        boolean result = true;        
        
        //Extraction de l'ancienne archive si necessaire
        final boolean archiveExistante = fichier.exists();            
        if (archiveExistante)
        {            
            result &= new File(chemin + "/save").mkdirs();
            FileUtils.copyFileToDirectory(fichier, new File(chemin + "/save/")); 
            final ZipUtil zipUtil = new ZipUtil(chemin + "/save/" + fichier.getName());
            result &= zipUtil.extraireLesFichiers(new File(chemin + EXTRACTION_PATH));
        }
        
        //Preparation de la nouvelle archive
        dest = new FileOutputStream(fichier);
        checksum = new CheckedOutputStream(dest, new Adler32());
        buff = new BufferedOutputStream(checksum);    
        out = new ZipOutputStream(buff);
        out.setMethod(ZipOutputStream.DEFLATED);
        out.setLevel(Deflater.BEST_COMPRESSION);
                
        //Reimport des anciens fichiers
        if (archiveExistante)
        {                   
            try
            {
                ajouterFichier(out, new File(chemin + EXTRACTION_PATH), "");
                FileUtils.forceDelete(new File(chemin + EXTRACTION_PATH));
                FileUtils.forceDelete(new File(chemin + "/save"));
            }
            catch (Exception e)
            {
                LOG.fatal(e);
                result=false;
            }
        }        
        return result;
    }
    
    
    
    /**
     * Cette methode est utilise pour ajouter recursivement
     * les fichiers d'un repertoire.
     * 
     * @param zipOutput
     * @param fichiersOuRepertoire
     * @param racine
     */
    private void ajouterFichier(ZipOutputStream zipOutput, File fichiersOuRepertoire, String racine)
    {   
        if (fichiersOuRepertoire==null)
        {
            return;
        }
        final byte[] data = new byte[BUFFER]; // $codepro.audit.disable unusedAssignment
        if (fichiersOuRepertoire.isDirectory())
        {
            if (fichiersOuRepertoire.listFiles()!=null)
            {                
                for (File tmp : Arrays.asList(fichiersOuRepertoire.listFiles()))
                {
                    if ("extraction".equals(fichiersOuRepertoire.getName()))
                    {
                        ajouterFichier(zipOutput, tmp, racine + PATH_SEPARATOR);    
                    }
                    else
                    {
                        ajouterFichier(zipOutput, tmp, racine + fichiersOuRepertoire.getName() + PATH_SEPARATOR);   
                    }
                    
                }
            }
        }
        else
        {
            try(                
                    FileInputStream fi = new FileInputStream(fichiersOuRepertoire);
                    BufferedInputStream buffi = new BufferedInputStream(fi, BUFFER);
            )
            {
                if (racine.startsWith(PATH_SEPARATOR))
                {
                    racine = racine.substring(1,racine.length());
                }
                ZipEntry entry = new ZipEntry(racine + fichiersOuRepertoire.getName());
                
                // ajout de cette entree dans le flux d'ecriture de l'archive ZIP
                zipOutput.putNextEntry(entry);
                int count=0;
                while ((count = buffi.read(data, 0, BUFFER)) != -1)
                {
                    zipOutput.write(data, 0, count);
                }
                zipOutput.closeEntry();
            }
            catch (IOException e)
            {
                LOG.error(e);
            }
        }
    }        
}
