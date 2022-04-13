/*
 * ====================================================================
 *
 * Copyright (C) 2007 - 2011 GeoSolutions S.A.S.
 * http://www.geo-solutions.it
 *
 * GPLv3 + Classpath exception
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.
 *
 * ====================================================================
 *
 * This software consists of voluntary contributions made by developers
 * of GeoSolutions.  For more information on GeoSolutions, please see
 * <http://www.geo-solutions.it/>.
 *
 */
package it.geosolutions.geostore.core.model.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.io.WKTWriter;

/**
 * The Class GeometryAdapter.
 * 
 * @param <G> the generic type
 * 
 * @author Emanuele Tajariol (etj at geo-solutions.it)
 */
public class GeometryAdapter<G extends Geometry> extends XmlAdapter<String, G> {

    /*
     * (non-Javadoc) @see javax.xml.bind.annotation.adapters.XmlAdapter#unmarshal(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public G unmarshal(String val) throws ParseException {
        WKTReader wktReader = new WKTReader();

        Geometry the_geom = wktReader.read(val);
        if (the_geom.getSRID() == 0) {
            the_geom.setSRID(4326);
        }

        try {
            return (G) the_geom;
        } catch (ClassCastException e) {
            throw new ParseException("WKT val is a " + the_geom.getClass().getName());
        }
    }

    /*
     * (non-Javadoc) @see javax.xml.bind.annotation.adapters.XmlAdapter#marshal(java.lang.Object)
     */
    @Override
    public String marshal(G the_geom) throws ParseException {
        if (the_geom != null) {
            WKTWriter wktWriter = new WKTWriter();
            if (the_geom.getSRID() == 0) {
                the_geom.setSRID(4326);
            }

            return wktWriter.write(the_geom);
        } else {
            throw new ParseException("Geometry obj is null.");
        }
    }
}
