package com.anyonehelps.common.util.barcode;

import java.awt.image.BufferedImage;

import org.jbarcode.JBarcode;
import org.jbarcode.encode.Code128Encoder;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.WidthCodedPainter;

public class Code128Barcode implements IBarcode {

    public BufferedImage createBarcode(String codes) throws Exception {
        try {
            JBarcode localJBarcode = new JBarcode(Code128Encoder.getInstance(), WidthCodedPainter.getInstance(), BaseLineTextPainter.getInstance());
            localJBarcode.setCheckDigit(false);
            localJBarcode.setShowCheckDigit(false);
            localJBarcode.setShowText(false);
            BufferedImage localBufferedImage = localJBarcode.createBarcode(codes);
            return localBufferedImage;
        } catch (Exception e) {
            throw e;
        }
    }

}
