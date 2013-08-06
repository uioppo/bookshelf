package com.google.code.kaptcha.util;

import com.google.code.kaptcha.BackgroundProducer;
import com.google.code.kaptcha.GimpyEngine;
import com.google.code.kaptcha.NoiseProducer;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultBackground;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.impl.DefaultNoise;
import com.google.code.kaptcha.impl.WaterRipple;
import com.google.code.kaptcha.text.TextProducer;
import com.google.code.kaptcha.text.WordRenderer;
import com.google.code.kaptcha.text.impl.DefaultTextCreator;
import com.google.code.kaptcha.text.impl.DefaultWordRenderer;
import java.awt.Color;
import java.awt.Font;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * @author randyjie
 *
 */
public class Config
{
  private Properties properties;
  private ConfigHelper helper;

 
  private static java.io.File file = null;
  private static java.io.File file1 = null;
  public Config(Properties properties)
  {
	  try {
		file = new java.io.File( Config.class.getClassLoader().getResource("./font/actionj.ttf").toURI().getPath());
		file1 = new java.io.File( Config.class.getClassLoader().getResource("./font/Hotplate.TTF").toURI().getPath());
	  } catch (URISyntaxException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    this.properties = properties;
    this.helper = new ConfigHelper();
  }

  public boolean isBorderDrawn()
  {
    String paramName = "kaptcha.border";
    String paramValue = this.properties.getProperty(paramName);
    return this.helper.getBoolean(paramName, paramValue, true);
  }

  public Color getBorderColor()
  {
    String paramName = "kaptcha.border.color";
    String paramValue = this.properties.getProperty(paramName);
    return this.helper.getColor(paramName, paramValue, Color.BLACK);
  }

  public int getBorderThickness()
  {
    String paramName = "kaptcha.border.thickness";
    String paramValue = this.properties.getProperty(paramName);
    return this.helper.getPositiveInt(paramName, paramValue, 1);
  }

  public Producer getProducerImpl()
  {
    String paramName = "kaptcha.producer.impl";
    String paramValue = this.properties.getProperty(paramName);
    Producer producer = (Producer)this.helper.getClassInstance(paramName, paramValue, new DefaultKaptcha(), this);
    return producer;
  }

  public TextProducer getTextProducerImpl()
  {
    String paramName = "kaptcha.textproducer.impl";
    String paramValue = this.properties.getProperty(paramName);
    TextProducer textProducer = (TextProducer)this.helper.getClassInstance(paramName, paramValue, new DefaultTextCreator(), this);

    return textProducer;
  }

  public char[] getTextProducerCharString()
  {
    String paramName = "kaptcha.textproducer.char.string";
    String paramValue = this.properties.getProperty(paramName);
    return this.helper.getChars(paramName, paramValue, "abcde34678gqfukhjvynmnpwx".toCharArray());
  }

  public int getTextProducerCharLength()
  {
    String paramName = "kaptcha.textproducer.char.length";
    String paramValue = this.properties.getProperty(paramName);
    return this.helper.getPositiveInt(paramName, paramValue, 5);
  }

  public Font[] getTextProducerFonts(int fontSize)
  {
    String paramName = "kaptcha.textproducer.font.names";
    String paramValue = this.properties.getProperty(paramName);
    
    if (file==null || !file.exists() || file1==null || !file1.exists()) {
    	 return this.helper.getFonts(paramName, paramValue, fontSize, new Font[] { new Font("Arial", 1, fontSize), new Font("Courier", 1, fontSize) });
    }
    try {
        java.io.FileInputStream fi = new java.io.FileInputStream(file);
        java.io.BufferedInputStream fb = new java.io.BufferedInputStream(fi);
        Font nf = Font.createFont(Font.TRUETYPE_FONT, fb);
        nf= nf.deriveFont(1,fontSize);
        fb.close();
        fi.close();
	      java.io.FileInputStream fi1 = new java.io.FileInputStream(file1);
	      java.io.BufferedInputStream fb1 = new java.io.BufferedInputStream(fi1);
	      Font nf1 = Font.createFont(Font.TRUETYPE_FONT, fb1);
	      nf1= nf1.deriveFont(1,fontSize);
        fb1.close();
        fi1.close();
        return this.helper.getFonts(paramName, paramValue, fontSize, new Font[] {nf,nf1});
      }
      catch (Exception e) {
    	  return this.helper.getFonts(paramName, paramValue, fontSize, new Font[] { new Font("Arial", 1, fontSize), new Font("Courier", 1, fontSize) });
      }
  }

  public int getTextProducerFontSize()
  {
    String paramName = "kaptcha.textproducer.font.size";
    String paramValue = this.properties.getProperty(paramName);
    return this.helper.getPositiveInt(paramName, paramValue, 40);
  }

  public Color getTextProducerFontColor()
  {
    String paramName = "kaptcha.textproducer.font.color";
    String paramValue = this.properties.getProperty(paramName);
    return this.helper.getColor(paramName, paramValue, Color.BLACK);
  }

  public int getTextProducerCharSpace()
  {
    String paramName = "kaptcha.textproducer.char.space";
    String paramValue = this.properties.getProperty(paramName);
    return this.helper.getPositiveInt(paramName, paramValue, 2);
  }

  public NoiseProducer getNoiseImpl()
  {
    String paramName = "kaptcha.noise.impl";
    String paramValue = this.properties.getProperty(paramName);
    NoiseProducer noiseProducer = (NoiseProducer)this.helper.getClassInstance(paramName, paramValue, new DefaultNoise(), this);

    return noiseProducer;
  }

  public Color getNoiseColor()
  {
    String paramName = "kaptcha.noise.color";
    String paramValue = this.properties.getProperty(paramName);
    return this.helper.getColor(paramName, paramValue, Color.BLACK);
  }

  public GimpyEngine getObscurificatorImpl()
  {
    String paramName = "kaptcha.obscurificator.impl";
    String paramValue = this.properties.getProperty(paramName);
    GimpyEngine gimpyEngine = (GimpyEngine)this.helper.getClassInstance(paramName, paramValue, new WaterRipple(), this);
    return gimpyEngine;
  }

  public WordRenderer getWordRendererImpl()
  {
    String paramName = "kaptcha.word.impl";
    String paramValue = this.properties.getProperty(paramName);
    WordRenderer wordRenderer = (WordRenderer)this.helper.getClassInstance(paramName, paramValue, new DefaultWordRenderer(), this);

    return wordRenderer;
  }

  public BackgroundProducer getBackgroundImpl()
  {
    String paramName = "kaptcha.background.impl";
    String paramValue = this.properties.getProperty(paramName);
    BackgroundProducer backgroundProducer = (BackgroundProducer)this.helper.getClassInstance(paramName, paramValue, new DefaultBackground(), this);

    return backgroundProducer;
  }

  public Color getBackgroundColorFrom()
  {
    String paramName = "kaptcha.background.clear.from";
    String paramValue = this.properties.getProperty(paramName);
    return this.helper.getColor(paramName, paramValue, Color.LIGHT_GRAY);
  }

  public Color getBackgroundColorTo()
  {
    String paramName = "kaptcha.background.clear.to";
    String paramValue = this.properties.getProperty(paramName);
    return this.helper.getColor(paramName, paramValue, Color.WHITE);
  }

  public int getWidth()
  {
    String paramName = "kaptcha.image.width";
    String paramValue = this.properties.getProperty(paramName);
    return this.helper.getPositiveInt(paramName, paramValue, 200);
  }

  public int getHeight()
  {
    String paramName = "kaptcha.image.height";
    String paramValue = this.properties.getProperty(paramName);
    return this.helper.getPositiveInt(paramName, paramValue, 50);
  }

  public String getSessionKey()
  {
    return this.properties.getProperty("kaptcha.session.key", "KAPTCHA_SESSION_KEY");
  }

  public String getSessionDate()
  {
    return this.properties.getProperty("kaptcha.session.date", "KAPTCHA_SESSION_DATE");
  }

  public Properties getProperties()
  {
    return this.properties;
  }
}