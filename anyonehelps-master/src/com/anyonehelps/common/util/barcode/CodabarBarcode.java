package com.anyonehelps.common.util.barcode;

import java.awt.image.BufferedImage;

import org.jbarcode.JBarcode;
import org.jbarcode.encode.CodabarEncoder;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.WideRatioCodedPainter;

public class CodabarBarcode implements IBarcode {

    public BufferedImage createBarcode(String codes) throws Exception {
        try {
            JBarcode localJBarcode = new JBarcode(CodabarEncoder.getInstance(), WideRatioCodedPainter.getInstance(), BaseLineTextPainter.getInstance());
            BufferedImage localBufferedImage = localJBarcode.createBarcode(codes);
            return localBufferedImage;
        } catch (Exception e) {
            throw e;
        }
    }

}
