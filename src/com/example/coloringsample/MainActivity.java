package com.example.coloringsample;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private ImageView backPictureView;
    private MyCanvasView myCanvasView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        

        setContentView(R.layout.activity_main);

        backPictureView = (ImageView) findViewById(R.id.backPictureView);
        myCanvasView = (MyCanvasView) findViewById(R.id.myCanvasView);
        myCanvasView
                .setPaletteView((PaletteView) findViewById(R.id.paletteView));
        myCanvasView.setBrushView((BrushView) findViewById(R.id.brushView));
    }


    public void onClickClearButton(View v) {
       
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.confirm_clear_title);
        dialog.setMessage(R.string.confirm_clear_msg);
        dialog.setPositiveButton(R.string.btn_ok,
        new DialogInterface.OnClickListener() {
                    public void
                            onClick(DialogInterface dialog, int whichButton) {
                        myCanvasView.clear();
                    }
                });
        dialog.setNegativeButton(R.string.btn_cancel, null);
        dialog.show();
    }

    public void onClickUndoButton(View v) {
        myCanvasView.undo();
    }

    public void onClickSaveButton(View v) {
        Bitmap back = ((BitmapDrawable) backPictureView.getDrawable())
                .getBitmap();
        Bitmap bitmap = composeBitmap(myCanvasView.getMBitmap(), back);
        outputPicture(bitmap);
    }

    private Bitmap composeBitmap(Bitmap myCanvas, Bitmap backPicture) {
        Bitmap newBitmap = Bitmap.createBitmap(backPicture.getWidth(),
                backPicture.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawBitmap(backPicture, 0, 0, (Paint) null);
        canvas.drawBitmap(myCanvas, 0, 0, (Paint) null);
        return newBitmap;
    }


    private void outputPicture(Bitmap myCanvas) {
        
        File path = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
       SimpleDateFormat format = new SimpleDateFormat(
                "yyyyMMddHHmmssSSS'.png'");
        File file = new File(path, format.format(new Date(System
                .currentTimeMillis())));
        try {
            if (!path.exists()) {
                path.mkdirs();
            }
            FileOutputStream out = new FileOutputStream(file);
            myCanvas.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.close();
            String mimeType = "image/png";
            MediaScannerConnection.scanFile(this, new String[] { file.getPath() }, new String[] { mimeType }, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, getString(R.string.message_saved),
                Toast.LENGTH_LONG).show();
    }
}