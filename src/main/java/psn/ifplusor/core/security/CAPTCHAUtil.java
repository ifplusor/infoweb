/**
 * Created by james on 9/16/16.
 */

package psn.ifplusor.core.security;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.util.Random;

public class CAPTCHAUtil {

    //设置字母的大小,大小
    private static Font mFont = new Font("Times New Roman", Font.PLAIN, 17);

    private static Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if(fc>255) fc=255;
        if(bc>255) bc=255;
        int r=fc+random.nextInt(bc-fc);
        int g=fc+random.nextInt(bc-fc);
        int b=fc+random.nextInt(bc-fc);
        return new Color(r,g,b);
    }

    public static String generateCAPTCHACode() {
        String sRand="";
        Random random = new Random();
        for (int i=0;i<6;i++) {
            int itmp = random.nextInt(26) + 65;
            char ctmp = (char)itmp;
            sRand += String.valueOf(ctmp);
        }
        return sRand;
    }

    public static RenderedImage generateCAPTCHAImage(String code) {

        int width=100, height=18;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();
        g.setColor(getRandColor(200,250));
        g.fillRect(1, 1, width-1, height-1);
        g.setColor(new Color(102,102,102));
        g.drawRect(0, 0, width-1, height-1);
        g.setFont(mFont);
        g.setColor(getRandColor(160,200));

        Random random = new Random();

        //画随机线
        for (int i=0;i<155;i++) {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            int xl = random.nextInt(6) + 1;
            int yl = random.nextInt(12) + 1;
            g.drawLine(x,y,x + xl,y + yl);
        }

        //从另一方向画随机线
        for (int i = 0;i < 70;i++) {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            int xl = random.nextInt(12) + 1;
            int yl = random.nextInt(6) + 1;
            g.drawLine(x,y,x - xl,y - yl);
        }

        //生成随机数,并将随机数字转换为字母
        for (int i = 0; i < code.length(); i++) {
            char c = code.charAt(i);
            g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
            g.drawString(String.valueOf(c),15*i+10,16);
        }

        g.dispose();

        return image;
    }
}
