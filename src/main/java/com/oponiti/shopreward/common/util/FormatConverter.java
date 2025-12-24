package com.oponiti.shopreward.common.util;

import com.oponiti.shopreward.common.exception.illegalargument.invalidvalue.InvalidIdException;
import com.oponiti.shopreward.common.exception.illegalargument.invalidvalue.ParsingBooleanException;
import com.oponiti.shopreward.common.exception.illegalargument.numberformat.*;

import static org.apache.commons.lang3.BooleanUtils.FALSE;
import static org.apache.commons.lang3.BooleanUtils.TRUE;

public class FormatConverter {
    public static Long parseId(String id) {
        try {
            return parseToLong(id);
        } catch (ParsingLongException e) {
            throw new InvalidIdException(id);
        }
    }

    public static Long parseToLong(String number) {
        try {
            return Long.parseLong(number);
        } catch (NumberFormatException nfe) {
            throw new ParsingLongException(number);
        }
    }

    public static Integer parseToInteger(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException nfe) {
            throw new ParsingIntegerException(number);
        }
    }

    public static Short parseToShort(String number) throws ParsingShortException {
        try {
            return Short.parseShort(number);
        } catch (NumberFormatException nfe) {
            throw new ParsingShortException(number);
        }
    }

    public static Byte parseToByte(String number) throws ParsingByteException {
        try {
            return Byte.parseByte(number);
        } catch (NumberFormatException nfe) {
            throw new ParsingByteException(number);
        }
    }

    public static Double parseToDouble(String primeNumber) {
        try {
            return Double.parseDouble(primeNumber);
        } catch (NumberFormatException nfe) {
            throw new ParsingDoubleException(primeNumber);
        }
    }

    public static Float parseToFloat(String primeNumber) {
        try {
            return Float.parseFloat(primeNumber);
        } catch (NumberFormatException nfe) {
            throw new ParsingFloatException(primeNumber);
        }
    }

    public static boolean parseToBoolean(String value) {
        if (!value.equals(TRUE) && !value.equals(FALSE)) {
            throw new ParsingBooleanException(value);
        }

        return Boolean.parseBoolean(value);
    }
}
