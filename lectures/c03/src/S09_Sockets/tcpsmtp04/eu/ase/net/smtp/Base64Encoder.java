package eu.ase.net.smtp;

// System imports

import java.lang.String;
import java.text.ParseException;

// Local imports
// (None)


/*******************************************************************************
* Base-64 encoding methods.
*
* <p>
* Base-64 encoding (a.k.a. radix-64 encoding) is a form of encoding binary data
* in 6-bit units.  (6 bits provides 64 distinct binary values, hence the moniker
* base-<i>64</i> or radix-<i>64</i>.)  Groups of three 8-bit octets (bytes),
* totalling 24 bits, can be converted into four 6-bit units.  Each 6-bit unit
* can in turn be represented by one of 64 unique printable ASCII (8-bit)
* characters.
*
* <p>
* Converting straight binary data (as a sequence of 8-bit octets) into a
* base-64 representation is thus simply the the process of packing groups of
* three 8-bit octets into four 6-bit units, and then substituting each 6-bit
* unit with its corresponding ASCII character code.
*
* <p>
* Converting base-64 encoded data (as a sequence of 8-bit characters) back into
* straight binary data is the reverse operation, substituting each base-64
* ASCII character code with its corresponding 6-bit unit, then unpacking groups
* of four 6-bit units into three 8-bit octets.
*
* <p>
* A 65th special ASCII character is used to indicate trailing padding in
* base-64 encoded data.
*
* <p>
* This class can be used as a simple replacement for the
* {@link sun.misc.BASE64Encoder} class.
*
* <!-- --------------------------------------------------------------------- -->
* @version	$Revision: 1.2 $ $Date: 2005/04/10 20:05:52 $
* @since	2003-02-26
* @author
*	David R. Tribble
*	(<a href="mailto:david@tribble.com">david@tribble.com</a>).
*	<br>
*
*	Copyright 2003-2005 by David R. Tribble, all rights reserved.
*	<br>
*	Permission is granted to freely use and distribute this source
*	code provided that the original copyright and authorship notices
*	remain intact.
*
* @see	Base64Decoder
* @see	sun.misc.BASE64Encoder
*/

public class Base64Encoder
{
// Identification

    /** Revision information. */
    static final String		REV = "@(#)tribble/util/Base64Encoder.java $Revision: 1.2 $ $Date: 2005/04/10 20:05:52 $\n";

    /** Class revision number. */
    public static final int	SERIES =	102;


// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
// Public static methods

    /***************************************************************************
    * Encode binary data as base-64 characters.
    *
    * <!-- ----------------------------------------------------------------- -->
    * @param	data (const)
    * Binary data, as an array of bytes (8-bit octets).
    *
    * @return
    * A printable ASCII string containing the contents of <tt>data</tt> encoded
    * as base-64 characters.  This string will contain
    * <tt>data.length*4/3+<i>p</i></tt> characters, where <tt><i>p</i></tt> is
    * 0, 1, or 2, so as to make the total output length a multiple of 4.
    *
    * @see	#encodeAsString(byte[], int, int) encodeAsString()
    * @see	Base64Decoder#decodeString(String) Base64Decoder.decodeString()
    *
    * @since	1.1, 2003-02-26
    */

    public static String encodeAsString(/*const*/ byte[] data)
    {
        // Encode the binary data as base-64 characters
        return (encodeAsString(data, 0, data.length));
    }


    /***************************************************************************
    * Encode binary data as base-64 characters.
    *
    * <!-- ----------------------------------------------------------------- -->
    * @param	data (const)
    * Binary data, as an array of bytes (8-bit octets).
    *
    * @param	off
    * Index of the first byte (octet) within <tt>data</tt> to encode.
    *
    * @param	len
    * Number of bytes (octets) within <tt>data</tt> to encode.
    *
    * @return
    * A printable ASCII string containing the contents of <tt>data</tt> encoded
    * as base-64 characters.  This string will contain
    * <tt>len*4/3+<i>p</i></tt> characters, where <tt><i>p</i></tt> is
    * 0, 1, or 2, so as to make the total output length a multiple of 4.
    *
    * @see	#encodeAsBytes(byte[], int, int) encodeAsBytes()
    * @see	Base64Decoder#decodeString(String, byte[], int)
    *		    Base64Decoder.decodeString()
    *
    * @since	1.1, 2003-02-26
    */

    public static String encodeAsString(/*const*/ byte[] data, int off, int len)
    {
        byte[]	b;

        // Encode 8-bit bytes as base-64 character bytes
        b = encodeAsBytes(data, off, len);

        // Return the encoded character bytes as a string
        return (new String(b));
    }


    /***************************************************************************
    * Encode binary data as base-64 character octets.
    *
    * <!-- ----------------------------------------------------------------- -->
    * @param	data (const)
    * Binary data, as an array of bytes (8-bit octets).
    *
    * @return
    * An array of bytes containing printable ASCII characters representating the
    * contents of <tt>data</tt> encoded as base-64 characters.  This array will
    * contain <tt>data.length*4/3+<i>p</i></tt> characters, where
    * <tt><i>p</i></tt> is 0, 1, or 2, so as to make the total output length a
    * multiple of 4.
    *
    * @see	#encodeAsBytes(byte[], int, int) encodeAsBytes()
    * @see	Base64Decoder#decodeBytes(byte[]) Base64Decoder.decodeBytes()
    *
    * @since	1.1, 2005-04-01
    */

    public static byte[] encodeAsBytes(/*const*/ byte[] data)
    {
        // Encode the binary data as base-64 characters
        return (encodeAsBytes(data, 0, data.length));
    }


    /***************************************************************************
    * Encode binary data as base-64 characters.
    *
    * <!-- ----------------------------------------------------------------- -->
    * @param	data (const)
    * Binary data, as an array of bytes (8-bit octets).
    *
    * @param	off
    * Index of the first byte (octet) within <tt>data</tt> to encode.
    *
    * @param	len
    * Number of bytes (octets) within <tt>data</tt> to encode.
    *
    * @return
    * An array of bytes containing printable ASCII characters containing the
    * contents of <tt>data</tt> encoded as base-64 characters.  This array will
    * contain <tt>data.length*4/3+<i>p</i></tt> characters, where
    * <tt><i>p</i></tt> is 0, 1, or 2, so as to make the total output length a
    * multiple of 4.
    *
    * @see	#encodeAsBytes(byte[], int, int, byte[], int) encodeAsBytes()
    * @see	Base64Decoder#decodeBytes(byte[], int, int)
    *		    Base64Decoder.decodeBytes()
    *
    * @since	1.1, 2005-04-01
    */

    public static byte[] encodeAsBytes(/*const*/ byte[] data, int off, int len)
    {
        byte[]	enc;
        int	olen;

        // Allocate the output buffer
        olen = (data.length+3-1)/3*4;
        enc = new byte[olen];

        // Encode the binary data as base-64 characters
        encodeAsBytes(data, off, len, enc, 0);
        return (enc);
    }


    /***************************************************************************
    * Encode binary data as base-64 characters.
    *
    * <p>
    * Base-64 (a.k.a. radix-64) encoding converts 8-bit bytes (octets) into
    * characters by encoding 6 bits of data (sextets) in each output character;
    * thus 3 8-bit input bytes can be converted into 4 6-bit output characters
    * (for a total of 24 data bits).  Any input bytes left over (two or less)
    * are converted and padded.
    *
    * <pre>
    *    +---------------+---------------+---------------+
    *    |     octet     |     octet     |     octet     |
    *    |7 6 5 4 3 2 1 0|7 6 5 4 3 2 1 0|7 6 5 4 3 2 1 0|  input octets
    *    :- - - - - -:- -:- - - -:- - - -:- -:- - - - - -:
    *    |5 4 3 2 1 0|5 4 3 2 1 0|5 4 3 2 1 0|5 4 3 2 1 0|  output sextets
    *    |   sextet  |   sextet  |   sextet  |   sextet  |
    *    +-----------+-----------+-----------+-----------+
    *
    *    +---------------+---------------+ - - - - - - - +
    *    |     octet     |     octet     |    no data    :
    *    |7 6 5 4 3 2 1 0|7 6 5 4 3 2 1 0|               :  input octets
    *    :- - - - - -:- -:- - - -:- - - -:- -:- - - - - -:
    *    |5 4 3 2 1 0|5 4 3 2 1 0|5 4 3 2 z z|    '='    |  output sextets
    *    |   sextet  |   sextet  |   sextet  |  padding  |
    *    +-----------+-----------+-----------+-----------+
    *
    *    +---------------+ - - - - - - - + - - - - - - - +
    *    |     octet     |    no data    :    no data    :
    *    |7 6 5 4 3 2 1 0|               :               :  input octets
    *    :- - - - - -:- -:- - - -:- - - -:- -:- - - - - -:
    *    |5 4 3 2 1 0|5 4 z z z z|    '='    |    '='    |  output sextets
    *    |   sextet  |   sextet  |  padding  |  padding  |
    *    +-----------+-----------+-----------+-----------+ </pre>
    *
    * <p>
    * For example, the following table list some octet sequences and their
    * resulting sextet encodings:
    * <pre>
    *    11,11,11           => 04,11,04,11
    *    11,22,33           => 04,12,08,33
    *    FF,FF,FF           => 3F,3F,3F,3F
    *    11,11              => 04,11,04, =
    *    FF,FF              => 3F,3F,3C, =
    *    11                 => 04,10, =, =
    *    FF                 => 3F,30, =, =
    *    FF,00,FF,00,FF,00  => 3F,30,03,3F,00,0F,3C,00 </pre>
    *
    * <!-- ----------------------------------------------------------------- -->
    * @param	data (const)
    * Binary data, as an array of bytes (8-bit octets).
    *
    * @param	off
    * Index of the first byte (octet) within <tt>data</tt> to encode.
    *
    * @param	len
    * Number of bytes (octets) within <tt>data</tt> to encode.
    *
    * @param	enc
    * An array of bytes into which will be written printable ASCII characters
    * representating the contents of <tt>data</tt> encoded as base-64
    * characters.
    *
    * @param	encOff
    * Index of the first byte (octet) within <tt>enc</tt> to write to.
    *
    * @return
    * The number of bytes (8-bit octets) actually written into <tt>enc</tt>.
    * This will be exactly <tt>len*4/3+<i>p</i></tt> bytes, where
    * <tt><i>p</i></tt> is 0, 1, or 2, so as to make the total output length a
    * multiple of 4.
    *
    * @see	#encodeAsBytes(byte[], int, int, byte[], int) encodeAsBytes()
    * @see	Base64Decoder#decodeBytes(byte[], int, int)
    *		    Base64Decoder.decodeBytes()
    *
    * @since	1.1, 2005-04-01
    */

    public static int encodeAsBytes(/*const*/ byte[] data, int off, int len,
        byte[] enc, int encOff)
    {
        int	d;
        int	i;
        int	o;

        // Convert the input bytes in chunks, 24 bits (3 octets) at a time
        len += off;
        i = off;
        o = encOff;

        while (i < len-2)
        {
            // Convert a single 24-bit chunk (3 octets into 4 sextets)
            d = ((data[i+0] & 0xFF) << 16) |
                ((data[i+1] & 0xFF) << 8) |
                ((data[i+2] & 0xFF));
            i += 3;

            enc[o++] = toBase64((d >> 18) & 0x3F);
            enc[o++] = toBase64((d >> 12) & 0x3F);
            enc[o++] = toBase64((d >> 6)  & 0x3F);
            enc[o++] = toBase64((d)       & 0x3F);
        }

        // Convert leftover input bytes, if any
        if (i < len-1)
        {
            // Two input bytes left, requires one padding char
            d = ((data[i+0] & 0xFF) << 16) |
                ((data[i+1] & 0xFF) << 8);

            enc[o++] = toBase64((d >> 18) & 0x3F);
            enc[o++] = toBase64((d >> 12) & 0x3F);
            enc[o++] = toBase64((d >> 6)  & 0x3F);
            enc[o++] = '=';
        }
        else if (i < len)
        {
            // One input byte left, requires two padding chars
            d = ((data[i+0] & 0xFF) << 16);

            enc[o++] = toBase64((d >> 18) & 0x3F);
            enc[o++] = toBase64((d >> 12) & 0x3F);
            enc[o++] = '=';
            enc[o++] = '=';
        }

        // Done
        return (o-encOff);
    }


    /***************************************************************************
    * Convert a 6-bit binary value into its corresponding base-64 printable
    * ASCII character code.
    *
    * <!-- ----------------------------------------------------------------- -->
    * @param	bits
    * A 6-bit binary value in the range [0,63]; or the value 64, indicating the
    * special trailing padding character code; or the character <tt>'?'</tt>,
    * indicating an invalid 6-bit code.
    *
    * @return
    * A printable ASCII character code.
    *
    * @see	Base64Decoder#fromBase64 Base64Decoder.fromBase64()
    *
    * @since	1.1, 2003-02-26
    */

    public static byte toBase64(int bits)
    {
        // This averages 2.85 comparisons per call
        if (bits >= 52)
        {
            if (bits < 62)
                return ((byte) (bits-52+'0'));
            if (bits == 62)
                return ((byte) '+');
            if (bits == 63)
                return ((byte) '/');
            if (bits == 64)
                return ((byte) '=');
            return ((byte) '?');
        }
        if (bits >= 26)
            return ((byte) (bits-26+'a'));
        if (bits >= 0)
            return ((byte) (bits-0+'A'));
        return ((byte) '?');
    }


// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
// Variables

    // (None)


// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
// Public constructors

    /***************************************************************************
    * Default constructor.
    *
    * <p>
    * This constructor is provided for compatibility with the
    * {@link sun.misc.BASE64Encoder} class, which allows this class to be used
    * as a simple replacement for it.
    * 
    * @since	1.2, 2005-04-10
    */

    public Base64Encoder()
    {
        // Do nothing
    }


// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
// Public methods

    /***************************************************************************
    * Encode binary data as base-64 characters.
    *
    * <p>
    * This method produces the same result as the <tt>encode()</tt> method
    * of the {@link sun.misc.BASE64Encoder} class.
    *
    * <!-- ----------------------------------------------------------------- -->
    * @param	data (const)
    * Binary data, as an array of bytes (8-bit octets).
    *
    * @return
    * A printable ASCII string containing the contents of <tt>data</tt> encoded
    * as base-64 characters.  This string will contain
    * <tt>data.length*4/3+<i>p</i></tt> characters, where <tt><i>p</i></tt> is
    * 0, 1, or 2, so as to make the total output length a multiple of 4.
    *
    * @see	#encodeAsString(byte[]) encodeAsString()
    * @see	Base64Decoder#decodeString(String) Base64Decoder.decodeString()
    *
    * @since	1.2, 2005-04-10
    */

    public String encode(/*const*/ byte[] data)
    {
        // Encode the binary data as base-64 characters
        return (encodeAsString(data, 0, data.length));
    }
}

// End Base64Encoder.java

