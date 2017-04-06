package com.anyonehelps.common.util.barcode;

import java.awt.image.BufferedImage;

import org.jbarcode.JBarcode;
import org.jbarcode.encode.EAN8Encoder;
import org.jbarcode.paint.EAN8TextPainter;
import org.jbarcode.paint.WidthCodedPainter;

public class ITF25Barcode implements IBarcode {

    public BufferedImage createBarcode(String codes) throws Exception {
        try {
            JBarcode localJBarcode = new JBarcode(EAN8Encoder.getInstance(), WidthCodedPainter.getInstance(), EAN8TextPainter.getInstance());
            BufferedImage localBufferedImage = localJBarcode.createBarcode(codes);
            return localBufferedImage;
        } catch (Exception e) {
            throw e;
        }
    }

}
