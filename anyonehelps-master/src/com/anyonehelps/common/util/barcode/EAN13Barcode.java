package com.anyonehelps.common.util.barcode;

import java.awt.image.BufferedImage;

import org.jbarcode.JBarcode;
import org.jbarcode.encode.EAN13Encoder;
import org.jbarcode.paint.EAN13TextPainter;
import org.jbarcode.paint.WidthCodedPainter;

public class EAN13Barcode implements IBarcode {

    public BufferedImage createBarcode(String codes) throws Exception {
        try {
            JBarcode localJBarcode = new JBarcode(EAN13Encoder.getInstance(), WidthCodedPainter.getInstance(), EAN13TextPainter.getInstance());
            BufferedImage localBufferedImage = localJBarcode.createBarcode(codes);
            return localBufferedImage;
        } catch (Exception e) {
            throw e;
        }
    }

}
