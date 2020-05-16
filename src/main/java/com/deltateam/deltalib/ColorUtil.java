package com.deltateam.deltalib;

public class ColorUtil {
	int value;
	public ColorUtil(int r, int g, int b) { this(r, g, b, 255); }
	public ColorUtil(int r, int g, int b, int a) {
		value = ((a & 0xFF) << 24) |
				((r & 0xFF) << 16) |
				((g & 0xFF) << 8)  |
				((b & 0xFF));
	}
	public ColorUtil darker() {
		double FACTOR=0.7;
		return new ColorUtil(Math.max((int)(getRed()*FACTOR), 0),
				Math.max((int)(getGreen()*FACTOR), 0),
				Math.max((int)(getBlue() *FACTOR), 0),
				getAlpha());
	}
	public ColorUtil darker(double FACTOR) {
		return new ColorUtil(Math.max((int)(getRed()  *FACTOR), 0),
				Math.max((int)(getGreen()*FACTOR), 0),
				Math.max((int)(getBlue() *FACTOR), 0),
				getAlpha());
	}
	public ColorUtil(int rgb) { value = 0xff000000 | rgb; }
	public int getRGB() { return value; }
	public int getRed() { return (getRGB() >> 16) & 0xFF; }
	public int getGreen() { return (getRGB() >> 8) & 0xFF; }
	public int getBlue() { return (getRGB() >> 0) & 0xFF; }
	public int getAlpha() { return (getRGB() >> 24) & 0xff; }
	public boolean equals(Object obj) { return obj instanceof ColorUtil && ((ColorUtil)obj).getRGB() == this.getRGB(); }
	public String toString() { return getClass().getName() + "[r=" + getRed() + ",g=" + getGreen() + ",b=" + getBlue() + "]"; }
}
