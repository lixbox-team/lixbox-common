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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Cette classe est utilitaire de lecture du fichier de build-info.properties
 * 
 * @author ludovic.terral
 */
public class CodeVersionUtil
{
    // ----------- Attribut(s) -----------    
    private static final Log LOG = LogFactory.getLog(CodeVersionUtil.class);



    // ----------- Methode(s) -----------
    private CodeVersionUtil()
    {
    }
    
    
    
    public static String getVersion(Class<?> clasz)
    {
        var result = "unknow";
        try(
            InputStream is = clasz.getResourceAsStream("/META-INF/MANIFEST.MF");
        )
        {
            var prop = new Properties();
            prop.load(is);
            result = prop.getProperty("Implementation-Version");
        }
        catch (IOException e)
        {
            LOG.error(e);
        }
        return result;
    }
}