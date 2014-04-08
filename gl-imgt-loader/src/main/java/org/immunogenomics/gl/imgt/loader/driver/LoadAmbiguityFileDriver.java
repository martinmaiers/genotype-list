/*

    gl-imgt-loader  IMGT/HLA database loader for the gl project.
    Copyright (c) 2012-2014 National Marrow Donor Program (NMDP)

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 3 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.fsf.org/licensing/licenses/lgpl.html
    > http://www.opensource.org/licenses/lgpl-license.php

 */
package org.immunogenomics.gl.imgt.loader.driver;

import java.io.File;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
//import org.apache.log4j.xml.DOMConfigurator;
import org.immunogenomics.gl.imgt.loader.processor.AlleleSetDataProcessor;
import org.immunogenomics.gl.imgt.xml.model.hla.ambig.AmbiguityData;
import org.immunogenomics.gl.imgt.xml.parser.XmlUnmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Adrienne N. Walts (awalts) <awalts@nmdp.org> Operational
 *         Bioinformatics, National Marrow Donor Program
 *
 */
public class LoadAmbiguityFileDriver {

	private static final Logger LOGGER = LoggerFactory.getLogger("mainEventLogger."
			+ LoadAmbiguityFileDriver.class);

//	static {
//		DOMConfigurator.configure("src/main/resources/log4j.xml");
//	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String hlaDatabasePath =
				System.getProperty(LoaderConstants.HLA_AMBIG_DATABASE_PATH_SYS_PROP);
		File xml = null;
		boolean isValidPath = false;

		if (hlaDatabasePath != null && !hlaDatabasePath.equals("")) {
			if ( hlaDatabasePath.startsWith("~" + File.separator)) {
				hlaDatabasePath = StringUtils.replaceOnce(hlaDatabasePath, "~", System.getProperty("user.home"));
			}

			xml = new File(hlaDatabasePath);

			if (xml.isFile() && xml.exists()) {
				isValidPath = true;
			}
			else if (xml.isDirectory()) {
				if (hlaDatabasePath.endsWith(File.separator)) {
					hlaDatabasePath = hlaDatabasePath.substring(0, hlaDatabasePath.length() - 1);
				}
				hlaDatabasePath = hlaDatabasePath + File.separator + LoaderConstants.HLA_AMBIG_DATABASE_FILENAME;
				xml = new File(hlaDatabasePath);

				if (xml.exists()) {
					isValidPath = true;
				}
			}
		}
		else {
			hlaDatabasePath =
					System.getProperty("user.dir") + File.separator + LoaderConstants.HLA_AMBIG_DATABASE_FILENAME;
			xml = new File(hlaDatabasePath);

			if (xml.exists()) {
				isValidPath = true;
			}
		}

		if (isValidPath) {
			Date start = null;
			AmbiguityData ambigData = null;
			XmlUnmarshaller x = new XmlUnmarshaller();
			AlleleSetDataProcessor alleleSetProcessor = new AlleleSetDataProcessor();

			LOGGER.info("LoadAmbiguityFileDriver.main:: Started loading the hla ambig database from the XML file: "
					+ xml.getPath());

			try {
				start = new Date(System.currentTimeMillis());

				// unmarshal the xml
				ambigData = x.unmarshalAmbiguousAlleles(xml);

				// insert the alleles
				alleleSetProcessor.insertAmbiguousAlleleSet(ambigData);
			} catch (Exception e) {
				LOGGER.error("LoadAmbiguityFileDriver.main:: Exception: " + e);
			}

			LOGGER.info("LoadAmbiguityFileDriver.main:: Finished loading the hla ambig database.");
			LOGGER.info("Start time was: " + start);
			LOGGER.info("end was: " + new Date(System.currentTimeMillis()));
		}
		else {
			LOGGER.info("LoadHLADriver.main:: HLA ambig database XML file '" + hlaDatabasePath + "' not found");
			System.out.println("HLA ambig database XML file '" + hlaDatabasePath + "' not found");
		}

	} // end main

}
