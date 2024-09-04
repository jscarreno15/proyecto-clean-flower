package com.clean.flower.util;

import org.modelmapper.AbstractConverter;

import java.util.Date;

public class ConvertidorFecha extends AbstractConverter<Date,String> {

    @Override
    protected String convert(Date fecha){return Util.formatoFecha(fecha);}
}
