package com.anyonehelps.common.util.weixin;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;


public class QRCodeUtil {
	public static final int WHITE = 0xFFFFFFFF;
	public static final int BLACK = 0xFF000000;
	@SuppressWarnings("unchecked")
	public static void encodeQRCode(HttpServletResponse response, String content){
		if(StringUtils.isBlank(content)){
			return ;
		}
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		@SuppressWarnings("rawtypes")
		Map hintsMap = new HashMap();
		hintsMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		BitMatrix bitMatrix = null;
		try{
			bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 300, 300, hintsMap);
			BufferedImage bufferedImage = toBufferedImage(bitMatrix);
			try{
				ImageIO.write(bufferedImage, "png", response.getOutputStream());
			}catch(IOException ioe){
				ioe.printStackTrace();
			}
		}catch(WriterException we){
			we.printStackTrace();
		}
	}
	private static BufferedImage toBufferedImage(BitMatrix bitMatrix){
		int width = bitMatrix.getWidth();
		int height = bitMatrix.getHeight();
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for(int x=0; x<width; x++){
			for(int y=0; y<height; y++){
				bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return bufferedImage;
	}
}
