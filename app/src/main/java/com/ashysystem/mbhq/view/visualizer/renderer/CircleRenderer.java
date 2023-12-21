/**
 * Copyright 2011, Felix Palmer
 *
 * Licensed under the MIT license:
 * http://creativecommons.org/licenses/MIT/
 */
package com.ashysystem.mbhq.view.visualizer.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.ashysystem.mbhq.view.visualizer.AudioData;
import com.ashysystem.mbhq.view.visualizer.FFTData;


public class CircleRenderer extends Renderer
{
  private Paint mPaint;
  private boolean mCycleColor;


  public CircleRenderer(Paint paint)
  {
    this(paint, false);
  }


  public CircleRenderer(Paint paint, boolean cycleColor)
  {
    super();
    mPaint = paint;
    mCycleColor = cycleColor;
  }

  @Override
  public void onRender(Canvas canvas, AudioData data, Rect rect)
  {
    if(mCycleColor)
    {
      cycleColor();
    }

    for (int i = 0; i < data.bytes.length - 1; i++) {
      float[] cartPoint = {
          (float)i / (data.bytes.length - 1),
          rect.height() / 2 + ((byte) (data.bytes[i] + 128)) * (rect.height() / 2) / 128
      };

      float[] polarPoint = toPolar(cartPoint, rect);
      mPoints[i * 4] = polarPoint[0];
      mPoints[i * 4 + 1] = polarPoint[1];

      float[] cartPoint2 = {
          (float)(i + 1) / (data.bytes.length - 1),
          rect.height() / 2 + ((byte) (data.bytes[i + 1] + 128)) * (rect.height() / 2) / 128
      };

      float[] polarPoint2 = toPolar(cartPoint2, rect);
      mPoints[i * 4 + 2] = polarPoint2[0];
      mPoints[i * 4 + 3] = polarPoint2[1];
    }

    canvas.drawLines(mPoints, mPaint);

    // Controls the pulsing rate
    modulation += 0.04;
  }

  @Override
  public void onRender(Canvas canvas, FFTData data, Rect rect)
  {
    // Do nothing, we only display audio data
  }

  float modulation = 0;
  float aggresive = 0.33f;
  private float[] toPolar(float[] cartesian, Rect rect)
  {
    double cX = rect.width()/2;
    double cY = rect.height()/2;
    double angle = (cartesian[0]) * 2 * Math.PI;
    double radius = ((rect.width()/2) * (1 - aggresive) + aggresive * cartesian[1]/2) * (1.2 + Math.sin(modulation))/2.2;
    float[] out =  {
        (float)(cX + radius * Math.sin(angle)),
        (float)(cY + radius * Math.cos(angle))
    };
    return out;
  }

  private float colorCounter = 0;
  private void cycleColor()
  {
    int r = (int) Math.floor(128*(Math.sin(colorCounter) + 1));
    //int r = Color.parseColor("#01D0B9");
    int g = (int) Math.floor(128*(Math.sin(colorCounter + 2) + 1));
    //int g = Color.parseColor("#01D0B9");
    int b = (int) Math.floor(128*(Math.sin(colorCounter + 4) + 1));
    //int b = Color.parseColor("#01D0B9");
    mPaint.setColor(Color.argb(128, r, g, b));
    colorCounter += 0.03;
  }
}
