package com.anyonehelps.common.util.barcode;

import java.awt.image.BufferedImage;

public interface IBarcode {
    public BufferedImage createBarcode(String codes) throws Exception;
}
